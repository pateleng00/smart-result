package com.learning.smartresult.student.model.request;

import lombok.Data;

@Data
public class AddStudent {
    private String name;
    private Long rollNumber;
    private String email;
    private String phoneNumber;
    private String address;
    private Long course;
    private String department;

    // Additional fields can be added as needed

}
