package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.request.SaveAnswerRequest;
import com.aptisphere.api.dto.request.StartExamRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.ExamResultResponse;
import com.aptisphere.api.entity.*;
import com.aptisphere.api.exception.BadRequestException;
import com.aptisphere.api.exception.ResourceNotFoundException;
import com.aptisphere.api.repository.*;
import com.aptisphere.api.service.ExamAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamAttemptServiceImpl implements ExamAttemptService {

    private final ExamAttemptRepository attemptRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Override
    @Transactional
    public ExamResultResponse startExam(StartExamRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", request.getExamId()));

        // Check if candidate already has an ongoing attempt for this specific exam
        Optional<ExamAttempt> existingAttempt = attemptRepository.findByUserIdAndExamIdAndStatus(user.getId(),
                exam.getId(), "IN_PROGRESS");
        if (existingAttempt.isPresent()) {
            throw new BadRequestException("You already have an ongoing attempt for this exam.");
        }

        LocalDateTime now = LocalDateTime.now();
        ExamAttempt attempt = ExamAttempt.builder()
                .user(user)
                .exam(exam)
                .startTime(now)
                .expectedEndTime(now.plusMinutes(exam.getDurationMinutes()))
                .status("IN_PROGRESS")
                .score(0)
                .build();

        attempt = attemptRepository.save(attempt);
        return mapToResultResponse(attempt);
    }

    @Override
    @Transactional
    public ApiResponse saveAnswer(Long attemptId, SaveAnswerRequest request, String userEmail) {
        ExamAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("ExamAttempt", "id", attemptId));

        // 1. Verify Ownership
        if (!attempt.getUser().getEmail().equals(userEmail)) {
            throw new BadRequestException("Unauthorized access to this attempt.");
        }

        // 2. Check if already submitted
        if ("COMPLETED".equals(attempt.getStatus())) {
            throw new BadRequestException("This exam has already been submitted.");
        }

        // 3. Strict Server-Side Deadline Check
        if (LocalDateTime.now().isAfter(attempt.getExpectedEndTime())) {
            forceSubmitExam(attempt);
            throw new BadRequestException("Time is up! Your exam has been automatically submitted.");
        }

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", request.getQuestionId()));

        Option option = optionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", request.getOptionId()));

        // 4. Update the existing answer if they changed their mind, or create a new one
        Optional<AttemptAnswer> existingAnswer = attempt.getAnswers().stream()
                .filter(a -> a.getQuestion().getId().equals(question.getId()))
                .findFirst();

        if (existingAnswer.isPresent()) {
            existingAnswer.get().setSelectedOption(option);
        } else {
            AttemptAnswer newAnswer = AttemptAnswer.builder()
                    .examAttempt(attempt)
                    .question(question)
                    .selectedOption(option)
                    .build();
            attempt.getAnswers().add(newAnswer);
        }

        attemptRepository.save(attempt);
        return new ApiResponse(true, "Answer saved successfully.");
    }

    @Override
    @Transactional
    public ExamResultResponse submitExam(Long attemptId, String userEmail) {
        ExamAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("ExamAttempt", "id", attemptId));

        if (!attempt.getUser().getEmail().equals(userEmail)) {
            throw new BadRequestException("Unauthorized access to this attempt.");
        }

        if ("COMPLETED".equals(attempt.getStatus())) {
            return mapToResultResponse(attempt); // Already graded
        }

        return forceSubmitExam(attempt);
    }

    @Override
    public List<ExamResultResponse> getUserAttemptHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        return attemptRepository.findByUserId(user.getId()).stream()
                .map(this::mapToResultResponse)
                .collect(Collectors.toList());
    }

    // Shared internal method to calculate the final grade
    private ExamResultResponse forceSubmitExam(ExamAttempt attempt) {
        int calculatedScore = 0;

        for (AttemptAnswer answer : attempt.getAnswers()) {
            if (answer.getSelectedOption().isCorrect()) {
                calculatedScore += answer.getQuestion().getMarks();
            }
        }

        attempt.setScore(calculatedScore);
        attempt.setStatus("COMPLETED");
        attempt.setEndTime(LocalDateTime.now());

        ExamAttempt savedAttempt = attemptRepository.save(attempt);
        return mapToResultResponse(savedAttempt);
    }

    private ExamResultResponse mapToResultResponse(ExamAttempt attempt) {
        return ExamResultResponse.builder()
                .attemptId(attempt.getId())
                .examTitle(attempt.getExam().getTitle())
                .score(attempt.getScore())
                .totalMarks(attempt.getExam().getTotalMarks())
                .status(attempt.getStatus())
                .build();
    }
}