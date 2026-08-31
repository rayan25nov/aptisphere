package com.aptisphere.api.controller;

import com.aptisphere.api.dto.request.CreateQuestionRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.QuestionResponse;
import com.aptisphere.api.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // Candidates need to fetch questions to take the exam
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(questionService.getQuestionsByExam(examId));
    }

    // ==========================================
    // ADMIN ONLY ENDPOINTS
    // ==========================================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse> addQuestion(
            @PathVariable Long examId,
            @Valid @RequestBody CreateQuestionRequest request) {
        ApiResponse response = questionService.addQuestion(examId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.deleteQuestion(id));
    }
}