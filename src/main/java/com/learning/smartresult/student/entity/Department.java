package com.learning.smartresult.student.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "department")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;

    @Column(name = "department_code", nullable = false, unique = true)
    private String departmentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "head_of_department", nullable = false)
    private String headOfDepartment; // Name of the head of the department

    @OneToMany(mappedBy = "department")
    private List<Course> courses;

}
