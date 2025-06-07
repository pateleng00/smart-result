package com.learning.smartresult.student.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "course")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Column(name = "description")
    private String description;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department; // Assuming Department is another entity class representing the department details


    @ManyToMany
    @JoinTable(
            name = "course_subject_mapping",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Subject> subjects; // Assuming Subject is another entity class representing the subject details

    @ManyToMany(mappedBy = "courses")
    private List<Faculty> faculties;


}
