package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.examinerservice.domain.Question;
import pro.sky.java.course2.examinerservice.exception.BadAmountException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    public void testGetQuestionsWithZeroAmount() {
        when(questionService.getAll()).thenReturn(List.of(new Question("Q1", "A1")));

        Collection<Question> result = examinerService.getQuestions(0);

        assertThat(result).isEmpty();
    }

    @Test
    public void testGetQuestionsThrowsExceptionWhenAmountIsTooBig() {
        when(questionService.getAll()).thenReturn(List.of(new Question("Q1", "A1")));

        assertThrows(BadAmountException.class, () -> {
            examinerService.getQuestions(5);
        });
    }

    @Test
    public void testGetQuestionsReturnsCorrectAmountOfUniqueQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");

        when(questionService.getAll()).thenReturn(List.of(q1, q2, q3));
        when(questionService.getRandomQuestion()).thenReturn(q1, q2, q1, q3);

        Collection<Question> result = examinerService.getQuestions(3);

        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(q1, q2, q3);
    }
}
