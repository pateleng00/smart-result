package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {

    // You can define custom query methods here if needed
    // For example:
    // Optional<Subject> findBySubjectCode(String subjectCode);
    // List<Subject> findByDepartmentId(Long departmentId);
    // List<Subject> findByCredits(int credits);
    // List<Subject> findByNameContaining(String name);
    // List<Subject> findBySemester(int semester);
    // List<Subject> findByDepartmentIdAndSemester(Long departmentId, int semester);
    List<Subject> findByCourseId(Long courseId);

}
