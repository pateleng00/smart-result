package com.learning.smartresult.student.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentDetailsResponse {

    private String name;
    private Long rollNumber;
    private String email;
    private String phoneNumber;
    private String address;
    private String course;
    private LocalDateTime enrollmentDate;
    private List<String> subjectsEnrolled;
    private String department;

    // Additional fields can be added as needed
}
