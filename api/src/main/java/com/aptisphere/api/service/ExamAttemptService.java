package com.aptisphere.api.service;

import com.aptisphere.api.dto.request.SaveAnswerRequest;
import com.aptisphere.api.dto.request.StartExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.ExamResultResponse;

import java.util.List;

public interface ExamAttemptService {
    ExamResultResponse startExam(StartExamRequest request, String userEmail);

    ApiResponse saveAnswer(Long attemptId, SaveAnswerRequest request, String userEmail);

    ExamResultResponse submitExam(Long attemptId, String userEmail);

    List<ExamResultResponse> getUserAttemptHistory(String userEmail);
}