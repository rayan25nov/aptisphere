package com.aptisphere.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequest {
    @NotBlank(message = "Question content is required")
    private String content;

    @NotBlank(message = "Section is required")
    private String section;

    @NotNull(message = "Marks are required")
    private Integer marks;

    @NotEmpty(message = "At least one option is required")
    @Valid
    private List<OptionRequest> options;

    @Data
    public static class OptionRequest {
        @NotBlank(message = "Option content is required")
        private String content;

        @NotNull(message = "Must specify if option is correct")
        private Boolean isCorrect;
    }
}