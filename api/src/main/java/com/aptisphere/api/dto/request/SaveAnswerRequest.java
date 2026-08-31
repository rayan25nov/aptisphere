package com.aptisphere.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveAnswerRequest {
    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Option ID is required")
    private Long optionId;
}