package com.aptisphere.api.service;

import com.aptisphere.api.dto.request.CreateQuestionRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    ApiResponse addQuestion(Long examId, CreateQuestionRequest request);

    List<QuestionResponse> getQuestionsByExam(Long examId);

    ApiResponse deleteQuestion(Long questionId);
}