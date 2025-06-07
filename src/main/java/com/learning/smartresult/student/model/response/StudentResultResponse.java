package com.learning.smartresult.student.model.response;


import lombok.Data;
import java.util.List;

@Data
public class StudentResultResponse {
    private String studentName;
    private Long rollNumber;
    private String departmentName;
    private String courseName;
    private List<ResultDetails> results;



}

