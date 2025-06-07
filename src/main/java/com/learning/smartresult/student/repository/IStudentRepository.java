package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStudentRepository extends JpaRepository<Student, Long> {

    // You can define custom query methods here if needed
    // For example:
    // List<Student> findByCourseId(Long courseId);
    // Optional<Student> findByEmail(String email);
    // List<Student> findByStatus(String status);
    Optional<Student> findByRollNumber(Long rollNumber);
}
