package com.aptisphere.api.repository;

import com.aptisphere.api.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // Fetches only exams that the admin has marked as active for candidates to take
    List<Exam> findByIsActiveTrue();
}