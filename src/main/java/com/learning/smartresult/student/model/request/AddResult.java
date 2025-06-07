package com.learning.smartresult.student.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class AddResult {
        private Long studentId;
        private Long rollNumber;
        private Long subjectId;
        private Long examSessionId;
        private BigDecimal marksObtained;
        private BigDecimal maxMarks;
        private String grade;
        private String status;
        private LocalDate resultDate;

}
