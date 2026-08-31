package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.response.AdminDashboardResponse;
import com.aptisphere.api.repository.ExamAttemptRepository;
import com.aptisphere.api.repository.ExamRepository;
import com.aptisphere.api.repository.QuestionRepository;
import com.aptisphere.api.repository.UserRepository;
import com.aptisphere.api.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamAttemptRepository examAttemptRepository;

    @Override
    public AdminDashboardResponse getAdminDashboardStats() {
        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalExams(examRepository.count())
                .activeExams(examRepository.findByIsActiveTrue().size())
                .totalQuestions(questionRepository.count())
                .totalAttempts(examAttemptRepository.count())
                .build();
    }
}