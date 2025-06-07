package com.learning.smartresult.student.service;


import com.learning.smartresult.student.model.request.AddResult;
import com.learning.smartresult.student.model.request.AddStudent;
import com.learning.smartresult.student.model.request.StudentResultRequest;
import com.learning.smartresult.student.model.response.StudentDetailsResponse;
import com.learning.smartresult.student.model.response.StudentResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {
    // Define methods for student operations here
     Page<StudentDetailsResponse> getAllStudents(Pageable pageable);

    void addStudent(AddStudent addStudent);

    List<StudentResultResponse> getStudentResults(StudentResultRequest su);

    void addStudentResult(AddResult addResultRequest);
}
