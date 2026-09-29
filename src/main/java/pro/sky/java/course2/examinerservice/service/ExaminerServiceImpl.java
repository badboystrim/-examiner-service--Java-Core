package pro.sky.java.course2.examinerservice.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exception.BadAmountException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > questionService.getAll().size()) {
            throw new BadAmountException("Запрошено слишком много вопросов!");
        }

        Set<Question> examQuestions = new HashSet<>();
        while (examQuestions.size() < amount) {
            examQuestions.add(questionService.getRandomQuestion());
        }
        return examQuestions;
    }
}
