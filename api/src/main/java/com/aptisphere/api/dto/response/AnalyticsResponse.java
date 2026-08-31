package com.aptisphere.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AnalyticsResponse {
    // Maps exam titles to their average scores (e.g., {"Java Basics": 75.5,
    // "Aptitude": 82.0})
    private Map<String, Double> averageScoresByExam;

    // Maps sections to accuracy percentages (e.g., {"Quantitative": 65.0, "Verbal":
    // 80.0})
    private Map<String, Double> accuracyBySection;

    private double overallPassRate;
}