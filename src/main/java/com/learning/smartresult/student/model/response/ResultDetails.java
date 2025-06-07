package com.learning.smartresult.student.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.BitSet;

@Data
public class ResultDetails {
    private String subjectName;
    private String examName;
    private Integer semester;
    private BigDecimal marksObtained;
    private BigDecimal maxMarks;
    private String grade;
    private LocalDateTime resultDate;
}
