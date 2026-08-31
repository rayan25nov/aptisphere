package com.aptisphere.api.controller;

import com.aptisphere.api.dto.request.SaveAnswerRequest;
import com.aptisphere.api.dto.request.StartExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.ExamResultResponse;
import com.aptisphere.api.service.ExamAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class ExamAttemptController {

    private final ExamAttemptService attemptService;

    // Triggered when a candidate clicks "Start Exam"
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/start")
    public ResponseEntity<ExamResultResponse> startExam(
            @Valid @RequestBody StartExamRequest request,
            Principal principal) {
        return ResponseEntity.ok(attemptService.startExam(request, principal.getName()));
    }

    // Triggered iteratively as the candidate selects options in the UI
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/{attemptId}/answers")
    public ResponseEntity<ApiResponse> saveAnswer(
            @PathVariable Long attemptId,
            @Valid @RequestBody SaveAnswerRequest request,
            Principal principal) {
        return ResponseEntity.ok(attemptService.saveAnswer(attemptId, request, principal.getName()));
    }

    // Triggered by the "Final Submit" button, OR automatically if Angular's timer
    // hits 0
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ExamResultResponse> submitExam(
            @PathVariable Long attemptId,
            Principal principal) {
        return ResponseEntity.ok(attemptService.submitExam(attemptId, principal.getName()));
    }

    // Fetch the candidate's historical results
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/history")
    public ResponseEntity<List<ExamResultResponse>> getMyHistory(Principal principal) {
        return ResponseEntity.ok(attemptService.getUserAttemptHistory(principal.getName()));
    }
}