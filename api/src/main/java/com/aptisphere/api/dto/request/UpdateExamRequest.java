package com.aptisphere.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateExamRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotNull(message = "Total marks are required")
    @Min(value = 1, message = "Total marks must be greater than 0")
    private Integer totalMarks;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}