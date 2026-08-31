package com.aptisphere.api.service;

import com.aptisphere.api.dto.request.CreateExamRequest;
import com.aptisphere.api.dto.request.UpdateExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.entity.Exam;

import java.util.List;

public interface ExamService {
    Exam createExam(CreateExamRequest request);

    Exam updateExam(Long id, UpdateExamRequest request);

    List<Exam> getAllExams();

    List<Exam> getActiveExams();

    Exam getExamById(Long id);

    ApiResponse deleteExam(Long id);
}