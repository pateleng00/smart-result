package com.learning.smartresult.student.model.request;

import lombok.Data;

@Data
public class StudentResultRequest {

    private String studentName;     // Optional: filter by student name (partial match)
    private Long rollNumber;        // Optional: filter by roll number (exact match)
    private Integer semester;       // Optional: filter by semester
    private String courseName;      // Optional: filter by course name (exact match or partial)
}
