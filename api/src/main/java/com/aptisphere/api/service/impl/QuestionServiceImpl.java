package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.request.CreateQuestionRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.QuestionResponse;
import com.aptisphere.api.entity.Exam;
import com.aptisphere.api.entity.Option;
import com.aptisphere.api.entity.Question;
import com.aptisphere.api.exception.ResourceNotFoundException;
import com.aptisphere.api.repository.ExamRepository;
import com.aptisphere.api.repository.QuestionRepository;
import com.aptisphere.api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Override
    public ApiResponse addQuestion(Long examId, CreateQuestionRequest request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));

        Question question = Question.builder()
                .content(request.getContent())
                .section(request.getSection())
                .marks(request.getMarks())
                .exam(exam)
                .build();

        List<Option> options = request.getOptions().stream().map(optReq -> {
            Option option = new Option();
            option.setContent(optReq.getContent());
            option.setCorrect(optReq.getIsCorrect());
            option.setQuestion(question);
            return option;
        }).collect(Collectors.toList());

        question.setOptions(options);
        questionRepository.save(question);

        return new ApiResponse(true, "Question added successfully to the exam");
    }

    @Override
    public List<QuestionResponse> getQuestionsByExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam", "id", examId);
        }

        List<Question> questions = questionRepository.findByExamId(examId);

        return questions.stream().map(q -> QuestionResponse.builder()
                .id(q.getId())
                .content(q.getContent())
                .section(q.getSection())
                .marks(q.getMarks())
                .options(q.getOptions().stream().map(o -> QuestionResponse.OptionResponse.builder()
                        .id(o.getId())
                        .content(o.getContent())
                        .isCorrect(o.isCorrect())
                        .build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public ApiResponse deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        questionRepository.delete(question);
        return new ApiResponse(true, "Question deleted successfully");
    }
}