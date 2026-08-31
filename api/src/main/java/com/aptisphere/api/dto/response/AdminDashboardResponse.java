package com.aptisphere.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDashboardResponse {
    private long totalUsers;
    private long totalExams;
    private long activeExams;
    private long totalQuestions;
    private long totalAttempts;
}