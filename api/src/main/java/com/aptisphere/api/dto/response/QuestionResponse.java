package com.aptisphere.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String content;
    private String section;
    private Integer marks;
    private List<OptionResponse> options;

    @Data
    @Builder
    public static class OptionResponse {
        private Long id;
        private String content;
        private boolean isCorrect;
        // Note: For candidate mock tests, you would map this to false/null in the
        // service
        // to prevent cheating, but keep it for admin views.
    }
}