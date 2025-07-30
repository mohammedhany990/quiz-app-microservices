package com.qa.question_service.controller;


import com.qa.question_service.dto.QuestionDto;
import com.qa.question_service.dto.Response;
import com.qa.question_service.model.Question;
import com.qa.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            questionService.addQuestion(question);
            return new ResponseEntity<>("Question added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question question) {
        try {
            questionService.updateQuestion(id, question);
            return new ResponseEntity<>("Question updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        try {
            questionService.deleteQuestion(id);
            return new ResponseEntity<>("Question deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getAllByCategory(@PathVariable String category) {
        try {
            List<Question> questions = questionService.findAllByCategory(category);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String category,@RequestParam Integer numQ)
    {
        return new ResponseEntity<>(questionService.getQuestionsForQuiz(category, numQ), HttpStatus.OK);
    }

    @PostMapping("get-questions")
        public ResponseEntity<List<QuestionDto>> getQuestions(@RequestBody List<Integer> questionIds)
    {
        return new ResponseEntity<>(questionService.getQuestionsFromIds(questionIds), HttpStatus.OK);
    }

    @PostMapping("get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return new ResponseEntity<>(questionService.getScore(responses), HttpStatus.OK);
    }


}
