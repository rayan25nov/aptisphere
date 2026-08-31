package com.aptisphere.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamResultResponse {
    private Long attemptId;
    private String examTitle;
    private Integer score;
    private Integer totalMarks;
    private String status;
}