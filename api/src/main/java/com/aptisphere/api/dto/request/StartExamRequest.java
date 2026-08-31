package com.aptisphere.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartExamRequest {
    @NotNull(message = "Exam ID is required")
    private Long examId;
}