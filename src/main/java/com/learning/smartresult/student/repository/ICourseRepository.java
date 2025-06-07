package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    // You can define custom query methods here if needed
    // For example:
    // Optional<Course> findByCourseCode(String courseCode);
    // List<Course> findByDepartmentId(Long departmentId);
    // List<Course> findByCredits(int credits);
}
