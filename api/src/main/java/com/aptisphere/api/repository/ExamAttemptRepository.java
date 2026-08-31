package com.aptisphere.api.repository;

import com.aptisphere.api.entity.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    // Gets a candidate's full history of taken exams
    List<ExamAttempt> findByUserId(Long userId);

    // Gets all attempts across all users for a specific exam (useful for Admin
    // analytics)
    List<ExamAttempt> findByExamId(Long examId);

    // Checks if a specific user has an ongoing attempt for a specific exam
    Optional<ExamAttempt> findByUserIdAndExamIdAndStatus(Long userId, Long examId, String status);
}