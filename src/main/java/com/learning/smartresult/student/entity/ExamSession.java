package com.learning.smartresult.student.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "exam_session")
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // e.g., "Semester 1 Final"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "academic_year", length = 20)
    private String academicYear; // e.g., "2024-2025"

    private Integer semester; // e.g., 1 or 2

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 20)
    private String status; // e.g., "Scheduled", "Completed"

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
      this.createdAt = LocalDate.now();
    }

  @ManyToOne
  @JoinColumn(name = "exam_session_id")
  private Result result;
}