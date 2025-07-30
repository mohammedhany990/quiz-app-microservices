package com.qa.quiz_service.controller;

import com.qa.quiz_service.dto.QuestionDto;
import com.qa.quiz_service.dto.QuizDto;
import com.qa.quiz_service.dto.Response;
import com.qa.quiz_service.service.QuizService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("create")
    public String createQuiz(@RequestBody QuizDto quizDto)
    {
        return quizService.createQuiz(quizDto);
    }

    @GetMapping("{id}")
    public List<QuestionDto> getQuizQuestions(@PathVariable Integer id)
    {
        return quizService.getQuizQuestionsById(id);
    }

    @PostMapping("submit/{id}")
    public Integer submit(@PathVariable Integer id, @RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id, responses);
    }


}
