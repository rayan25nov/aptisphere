package com.aptisphere.api.controller;

import com.aptisphere.api.dto.request.CreateExamRequest;
import com.aptisphere.api.dto.request.UpdateExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.entity.Exam;
import com.aptisphere.api.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    // Publicly accessible to any authenticated user (Candidates seeing available
    // exams)
    @GetMapping("/active")
    public ResponseEntity<List<Exam>> getActiveExams() {
        return ResponseEntity.ok(examService.getActiveExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    // ==========================================
    // ADMIN ONLY ENDPOINTS
    // ==========================================

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Exam> createExam(@Valid @RequestBody CreateExamRequest request) {
        return new ResponseEntity<>(examService.createExam(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @Valid @RequestBody UpdateExamRequest request) {
        return ResponseEntity.ok(examService.updateExam(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExam(@PathVariable Long id) {
        return ResponseEntity.ok(examService.deleteExam(id));
    }
}