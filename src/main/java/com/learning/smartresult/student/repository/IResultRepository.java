package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

}
