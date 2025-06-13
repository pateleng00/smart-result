package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

}
