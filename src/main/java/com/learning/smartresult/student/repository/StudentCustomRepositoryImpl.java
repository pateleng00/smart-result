package com.learning.smartresult.student.repository;


import com.learning.smartresult.student.entity.*;
import com.learning.smartresult.student.model.request.StudentResultRequest;
import com.learning.smartresult.student.model.response.ResultDetails;
import com.learning.smartresult.student.model.response.StudentDetailsResponse;
import com.learning.smartresult.student.model.response.StudentResultResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class StudentCustomRepositoryImpl implements IStudentCustomRepository{
    private final EntityManager entityManager;


    @Override
    public Page<StudentDetailsResponse> getAllStudentDetails(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Student> studentRoot = cq.from(Student.class);
        Join<Student, Course> courseJoin = studentRoot.join("course");
        Join<Course, Department> departmentJoin = courseJoin.join("department");

        cq.where(cb.equal(studentRoot.get("status"), "Active"));

        cq.multiselect(
                studentRoot.get("id").alias("id"),
                studentRoot.get("studentName").alias("studentName"),
                studentRoot.get("rollNumber").alias("rollNumber"),
                studentRoot.get("email").alias("email"),
                studentRoot.get("phoneNumber").alias("phoneNumber"),
                studentRoot.get("address").alias("address"),
                courseJoin.get("courseName").alias("courseName"),
                studentRoot.get("enrollmentDate").alias("enrollmentDate"),
                departmentJoin.get("departmentName").alias("departmentName")
        );

        TypedQuery<Tuple> query = entityManager.createQuery(cq);

        // Pagination:
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Tuple> tuples = query.getResultList();

        // FIXED COUNT QUERY (matches main query joins)
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Student> countRoot = countQuery.from(Student.class);
        Join<Student, Course> countCourseJoin = countRoot.join("course");
        Join<Course, Department> countDeptJoin = countCourseJoin.join("department");
        countQuery.select(cb.count(countRoot))
                .where(cb.equal(countRoot.get("status"), "Active"));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        List<StudentDetailsResponse> content = tuples.stream().map(tuple -> {
            StudentDetailsResponse res = new StudentDetailsResponse();
            res.setName(tuple.get("studentName", String.class));
            res.setRollNumber(tuple.get("rollNumber", Long.class));
            res.setEmail(tuple.get("email", String.class));
            res.setPhoneNumber(tuple.get("phoneNumber", String.class));
            res.setAddress(tuple.get("address", String.class));
            res.setCourse(tuple.get("courseName", String.class));
            res.setEnrollmentDate(tuple.get("enrollmentDate", LocalDateTime.class));
            res.setDepartment(tuple.get("departmentName", String.class));
            res.setSubjectsEnrolled(getSubjectsForStudent(tuple.get("id", Long.class)));
            return res;
        }).toList();

        return new PageImpl<>(content, pageable, total);
    }



    private List<String> getSubjectsForStudent(Long studentId) {
        return entityManager.createQuery(
                        "SELECT s.subjectName FROM Subject s " +
                                "JOIN s.studentList student " +
                                "WHERE student.id = :studentId", String.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }


    public List<StudentResultResponse> getStudentResults(StudentResultRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);

        Root<Result> resultRoot = query.from(Result.class);
        Join<Result, Student> studentJoin = resultRoot.join("student");
        Join<Student, Course> courseJoin = studentJoin.join("course");
        Join<Result, Subject> subjectJoin = resultRoot.join("subject");
        Join<Course, Department> departmentJoin = courseJoin.join("department", JoinType.LEFT);
        Join<Result, ExamSession> examJoin = resultRoot.join("examSession", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(studentJoin.get("status"), "Active"));

        if (request.getStudentName() != null && !request.getStudentName().isEmpty()) {
            predicates.add(cb.like(cb.lower(studentJoin.get("studentName")), "%" + request.getStudentName().toLowerCase() + "%"));
        }

        if (request.getRollNumber() != null) {
            predicates.add(cb.equal(studentJoin.get("rollNumber"), request.getRollNumber()));
        }

        if (request.getSemester() != null) {
            predicates.add(cb.equal(subjectJoin.get("semester"), request.getSemester()));
        }

        if (request.getCourseName() != null && !request.getCourseName().isEmpty()) {
            predicates.add(cb.equal(cb.lower(courseJoin.get("courseName")), request.getCourseName().toLowerCase()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.multiselect(
                studentJoin.get("studentName").alias("studentName"),
                studentJoin.get("rollNumber").alias("rollNumber"),
                subjectJoin.get("subjectName").alias("subjectName"),
                subjectJoin.get("semester").alias("semester"),
                courseJoin.get("courseName").alias("courseName"),
                resultRoot.get("marksObtained").alias("marksObtained"),
                resultRoot.get("maxMarks").alias("maxMarks"),
                resultRoot.get("grade").alias("grade"),
                resultRoot.get("resultDate").alias("resultDate"),
                examJoin.get("name").alias("examName"),
                departmentJoin.get("departmentName").alias("departmentName")
        );
        List<Tuple> tuples = entityManager.createQuery(query).getResultList();

        Map<Long, StudentResultResponse> resultMap = new LinkedHashMap<>();

        for (Tuple tuple : tuples) {
            Long rollNumber = tuple.get("rollNumber", Long.class);

            // If student entry not present, create one
            StudentResultResponse studentResult = resultMap.computeIfAbsent(rollNumber, rn -> {
                StudentResultResponse sr = new StudentResultResponse();
                sr.setStudentName(tuple.get("studentName", String.class));
                sr.setRollNumber(rn);
                sr.setDepartmentName(tuple.get("departmentName", String.class));
                sr.setCourseName(tuple.get("courseName", String.class));
                sr.setResults(new ArrayList<>());
                return sr;
            });

            // Add subject result to that student
            ResultDetails result = new ResultDetails();
            result.setSubjectName(tuple.get("subjectName", String.class));
            result.setExamName(tuple.get("examName", String.class));
            result.setSemester(tuple.get("semester", Integer.class));
            result.setMarksObtained(tuple.get("marksObtained", BigDecimal.class));
            result.setMaxMarks(tuple.get("maxMarks", BigDecimal.class));
            result.setGrade(tuple.get("grade", String.class));
            result.setResultDate(tuple.get("resultDate", LocalDateTime.class));

            studentResult.getResults().add(result);
        }

        return new ArrayList<>(resultMap.values());
    }


}
