package com.learning.smartresult.student.repository;

import com.learning.smartresult.student.model.request.StudentResultRequest;
import com.learning.smartresult.student.model.response.StudentDetailsResponse;
import com.learning.smartresult.student.model.response.StudentResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IStudentCustomRepository {
    Page<StudentDetailsResponse> getAllStudentDetails(Pageable pageable);

    List<StudentResultResponse> getStudentResults(StudentResultRequest studentResultRequest);

}
