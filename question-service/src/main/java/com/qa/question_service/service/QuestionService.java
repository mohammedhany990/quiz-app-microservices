package com.qa.question_service.service;

import com.qa.question_service.dto.QuestionDto;
import com.qa.question_service.dto.Response;
import com.qa.question_service.model.Question;
import com.qa.question_service.repository.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepo questionRepo;

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public void addQuestion(Question question) {
        questionRepo.save(question);
    }

    public void deleteQuestion(Integer id) {
        questionRepo.deleteById(id);
    }

    public void updateQuestion(Integer id, Question updatedQuestion) {
        Optional<Question> existingQuestion = questionRepo.findById(id);
        existingQuestion.ifPresent(q -> {
            Question question = Question.builder()
                    .id(q.getId())
                    .questionTitle(updatedQuestion.getQuestionTitle())
                    .option1(updatedQuestion.getOption1())
                    .option2(updatedQuestion.getOption2())
                    .option3(updatedQuestion.getOption3())
                    .option4(updatedQuestion.getOption4())
                    .rightAnswer(updatedQuestion.getRightAnswer())
                    .difficultylevel(updatedQuestion.getDifficultylevel())
                    .category(updatedQuestion.getCategory())
                    .build();
            questionRepo.save(question);
        });
    }

    public List<Question> findAllByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public List<Integer> getQuestionsForQuiz(String category, Integer numQ) {
        return questionRepo.findRandomQuestionsByCategory(category, numQ);
    }


    public List<QuestionDto> getQuestionsFromIds(List<Integer> questionIds) {

        List<Question> questions = questionRepo.findAllById(questionIds);

        return questions
                .stream()
                .map(x -> QuestionDto.builder()
                        .id(x.getId())
                        .questionTitle(x.getQuestionTitle())
                        .option1(x.getOption1())
                        .option2(x.getOption2())
                        .option3(x.getOption3())
                        .option4(x.getOption4())
                        .build())
                .toList();
    }

    public Integer getScore(List<Response> responses) {

        int sum = 0;
        for (Response response : responses) {
            Question question = questionRepo.findById(response.getId()).get();

            if (response.getResponse().equals(question.getRightAnswer())) {
                sum++;
            }
        }
        return sum;
    }
}
