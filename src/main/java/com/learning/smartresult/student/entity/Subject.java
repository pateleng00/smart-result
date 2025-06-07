package com.learning.smartresult.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subject")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_code", nullable = false, unique = true)
    private String subjectCode;

    @Column(name = "description")
    private String description;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "semester", nullable = false)
    private Integer semester; // e.g., 1 for first semester, 2 for second semester, etc.

    @Column(name = "is_elective", nullable = false)
    private Boolean isElective; // Indicates if the subject is elective or core

    @Column(name = "course_id", nullable = false)
    private Long courseId; // Assuming this links to a Course entity

    @ManyToMany(mappedBy = "subjects")
    private List<Course> courses; // Assuming Course is another entity class representing the course details

    @ManyToMany(mappedBy = "subjects")
    private List<Student> studentList; // Assuming Student is another entity class representing the students enrolled in the subject

    @ManyToMany(mappedBy = "subjects")
    private List<Faculty> faculties;








}
