package com.learning.smartresult.student.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "result")
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "marks_obtained")
    private BigDecimal marksObtained;

    @Column(name = "max_marks")
    private BigDecimal maxMarks;

    private String grade;
    private String status;

    @Column(name = "result_date")
    private LocalDateTime resultDate;

    @Getter
    @ManyToOne
    private ExamSession examSession;

}
