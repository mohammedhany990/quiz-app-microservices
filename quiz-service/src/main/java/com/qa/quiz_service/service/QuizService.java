package com.qa.quiz_service.service;


import com.qa.quiz_service.dto.QuestionDto;
import com.qa.quiz_service.dto.QuizDto;
import com.qa.quiz_service.dto.Response;
import com.qa.quiz_service.feign.QuestionClient;
import com.qa.quiz_service.model.Quiz;
import com.qa.quiz_service.repository.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepo quizRepo;
    private final QuestionClient questionClient;

    public String createQuiz(QuizDto quizDto) {

        List<Integer> questionIds = questionClient
                .generateQuestions(quizDto.getCategory(), quizDto.getNumberOfQuestions())
                .getBody();

        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .questionIds(questionIds)
                .build();

        quizRepo.save(quiz);

        return "Quiz created successfully.";
    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestionsById(Integer id) {
        Quiz quiz = quizRepo.findById(id).get();

        List<Integer> questionIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionDto>> questions = questionClient.getQuestions(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> sum = questionClient.getScore(responses);
        return sum;
    }
}
