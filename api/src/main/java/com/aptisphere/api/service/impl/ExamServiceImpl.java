package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.request.CreateExamRequest;
import com.aptisphere.api.dto.request.UpdateExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.entity.Exam;
import com.aptisphere.api.exception.ResourceNotFoundException;
import com.aptisphere.api.repository.ExamRepository;
import com.aptisphere.api.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public Exam createExam(CreateExamRequest request) {
        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .durationMinutes(request.getDurationMinutes())
                .totalMarks(request.getTotalMarks())
                .isActive(true)
                .build();
        return examRepository.save(exam);
    }

    @Override
    public Exam updateExam(Long id, UpdateExamRequest request) {
        Exam exam = getExamById(id);
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setActive(request.getIsActive());
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public List<Exam> getActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    @Override
    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));
    }

    @Override
    public ApiResponse deleteExam(Long id) {
        Exam exam = getExamById(id);
        examRepository.delete(exam);
        return new ApiResponse(true, "Exam deleted successfully");
    }
}