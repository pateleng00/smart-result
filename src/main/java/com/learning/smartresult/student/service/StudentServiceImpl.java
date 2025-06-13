package com.learning.smartresult.student.service;

import com.learning.smartresult.student.entity.*;
import com.learning.smartresult.student.model.request.AddResult;
import com.learning.smartresult.student.model.request.AddStudent;
import com.learning.smartresult.student.model.request.StudentResultRequest;
import com.learning.smartresult.student.model.response.StudentDetailsResponse;
import com.learning.smartresult.student.model.response.StudentResultResponse;
import com.learning.smartresult.student.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService{
    private final IStudentCustomRepository studentCustomRepository;
    private final ISubjectRepository subjectRepository;
    private final ICourseRepository courseRepository;
    private final IStudentRepository studentRepository;
    private final IResultRepository resultRepository;
    private final IExamSessionRepository examSessionRepository;


    @Override
    public Page<StudentDetailsResponse> getAllStudents(Pageable pageable) {
            Page<StudentDetailsResponse> studentDetailsResponses = studentCustomRepository.getAllStudentDetails(pageable);
            if (studentDetailsResponses.isEmpty()) {
                throw new RuntimeException("No students found");
            }

        return studentDetailsResponses;
    }


    @Override
    public void addStudent(AddStudent student) {
        Optional<Student> getStudent = studentRepository.findByRollNumber(student.getRollNumber());
        if (getStudent.isPresent()) {
            throw new RuntimeException("Student with roll number " + student.getRollNumber() + " already exists.");
        }
        Optional<Course> course = courseRepository.findById(student.getCourse());
        List<Subject> subjectList = subjectRepository.findByCourseId(student.getCourse());
        LocalDateTime enrollmentDate = LocalDateTime.now();
        Student newStudent = new Student();
        newStudent.setStudentName(student.getName());
        newStudent.setRollNumber(student.getRollNumber());
        newStudent.setEmail(student.getEmail());
        newStudent.setPhoneNumber(student.getPhoneNumber());
        newStudent.setAddress(student.getAddress());
        newStudent.setEnrollmentDate(enrollmentDate);
        newStudent.setCourse(course.orElseThrow(() -> new RuntimeException("Course not found with ID: " + student.getCourse())));
        newStudent.setSubjects(subjectList);
        newStudent.setStatus("Active"); // Set the status to "Active" by default
        // Save the student entity to the database
        studentRepository.save(newStudent);
        
    }

    @Override
    public List<StudentResultResponse> getStudentResults(StudentResultRequest su) {
        return studentCustomRepository.getStudentResults(su);
    }

    @Override
    public void addStudentResult(AddResult addResultRequest) {
        Optional<Student> student = studentRepository.findById(addResultRequest.getStudentId());
        Optional<Subject> subject = subjectRepository.findById(addResultRequest.getSubjectId());
        if (student.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + addResultRequest.getStudentId());
        }
        if (subject.isEmpty()) {
            throw new RuntimeException("Subject not found with ID: " + addResultRequest.getSubjectId());
        }

        Optional<Result> existingResults = resultRepository.findByStudentIdAndSubjectId(
                addResultRequest.getStudentId(), addResultRequest.getSubjectId());

        if (!existingResults.isEmpty()) {
            throw new RuntimeException("Result for this student and subject already exists.");
        }

        ExamSession examSession = examSessionRepository.findById(addResultRequest.getExamSessionId())
                .orElse(null); // Optional, can be null if not provided
        // Create a new Result entity
        Result result = new Result();
        result.setStudent(student.get());
        result.setSubject(subject.get());
        result.setExamSession(examSession); // Optional, can be null if not provided
        result.setMarksObtained(addResultRequest.getMarksObtained());
        result.setMaxMarks(addResultRequest.getMaxMarks());
        result.setGrade(addResultRequest.getGrade());
        result.setStatus(addResultRequest.getStatus());
        result.setResultDate(LocalDateTime.now());

        // Save the result entity to the database
        resultRepository.save(result);

    }

}
