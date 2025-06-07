package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {

    // You can define custom query methods here if needed
    // For example:
    // Optional<Department> findByDepartmentCode(String departmentCode);
    // List<Department> findByHeadOfDepartment(String headOfDepartment);
    // List<Department> findByCoursesId(Long courseId);
}
