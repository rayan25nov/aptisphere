package com.aptisphere.api.repository;

import com.aptisphere.api.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Fetches all questions belonging to a specific exam
    List<Question> findByExamId(Long examId);
}