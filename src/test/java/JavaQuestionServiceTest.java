package pro.sky.java.course2.examinerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.java.course2.examinerservice.domain.Question;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaQuestionServiceTest {

    private JavaQuestionService questionService;

    @BeforeEach
    public void setUp() {
        questionService = new JavaQuestionService();
    }

    @Test
    public void testAddQuestionWithStrings() {
        Question result = questionService.add("Question 1", "Answer 1");

        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo("Question 1");
        assertThat(result.getAnswer()).isEqualTo("Answer 1");
        assertThat(questionService.getAll()).contains(result);
    }

    @Test
    public void testAddQuestionObject() {
        Question question = new Question("Question 2", "Answer 2");
        Question result = questionService.add(question);

        assertThat(result).isEqualTo(question);
        assertThat(questionService.getAll()).contains(question);
    }

    @Test
    public void testAddDuplicatesDoesNotIncreaseSize() {
        Question question1 = new Question("Unique", "Answer");
        Question question2 = new Question("Unique", "Answer");

        questionService.add(question1);
        questionService.add(question2);

        assertThat(questionService.getAll()).hasSize(1);
    }

    @Test
    public void testRemoveQuestion() {
        Question question = new Question("Question 3", "Answer 3");
        questionService.add(question);

        Question result = questionService.remove(question);

        assertThat(result).isEqualTo(question);
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    public void testRemoveFromEmptyCollection() {
        Question question = new Question("Missing", "Missing");
        Question result = questionService.remove(question);
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    public void testGetAllReturnsImmutableCollection() {
        questionService.add("Question 4", "Answer 4");
        Collection<Question> allQuestions = questionService.getAll();

        assertThrows(UnsupportedOperationException.class, () -> {
            allQuestions.add(new Question("Q", "A"));
        });
    }

    @Test
    public void testGetRandomQuestionThrowsExceptionWhenEmpty() {
        assertThrows(IllegalStateException.class, () -> {
            questionService.getRandomQuestion();
        });
    }

    @Test
    public void testGetRandomQuestionReturnsValidQuestion() {
        Question q1 = questionService.add("Q1", "A1");
        Question q2 = questionService.add("Q2", "A2");

        Question randomQuestion = questionService.getRandomQuestion();

        assertThat(randomQuestion).isNotNull();
        assertThat(questionService.getAll()).contains(randomQuestion);
    }
}
