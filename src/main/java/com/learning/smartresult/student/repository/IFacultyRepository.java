package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacultyRepository extends JpaRepository<Faculty, Long> {

    // You can define custom query methods here if needed
    // For example:
    // List<Faculty> findByDepartmentId(Long departmentId);
    // Optional<Faculty> findByEmail(String email);
}
