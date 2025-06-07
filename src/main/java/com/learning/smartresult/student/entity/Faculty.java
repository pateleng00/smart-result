package com.learning.smartresult.student.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "faculty")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "specialization")
    private String specialization; // Area of expertise or specialization of the faculty member

    @Column(name = "joining_date")
    private LocalDateTime joiningDate; // Date when the faculty member joined the institution

    @Column(name = "status")
    private String status; // e.g., Active, Inactive, Retired

    @Column(name = "address")
    private String address; // Address of the faculty member

    @Column(name = "is_HOD", nullable = false)
    private Boolean isHOD; // Indicates if the faculty member is a Head of Department (HOD)

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department; // Assuming Department is another entity class representing the department details

    @ManyToMany
    @JoinTable(
            name = "faculty_subject_mapping",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @ManyToMany
    @JoinTable(
            name = "faculty_course_mapping",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;




}
