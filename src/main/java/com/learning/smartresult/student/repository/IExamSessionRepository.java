package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamSessionRepository extends JpaRepository<ExamSession, Long> {

    // Additional methods specific to ExamSession can be defined here if needed
}
