package seedu.address.ui.test.openendedtest;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OpenEndedTestShowAnswerEvent;
import seedu.address.commons.events.ui.OpenEndedTestShowNextQuestionEvent;
import seedu.address.model.card.Card;
import seedu.address.model.test.openendedtest.OpenEndedTest;
import seedu.address.ui.test.TriviaTestPage;

/**
 * The page that shows up when the user is in a open ended test
 */
public class OpenEndedTestPage extends TriviaTestPage {
    private static final String FXML = "test/openendedtest/OpenEndedTestPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private OpenEndedTest openEndedTest;
    private Card card;
    private String question;
    private String answer;
    private String userAnswer;

    @FXML
    private Label questionLabel;
    @FXML
    private Label userAnswerLabel;
    @FXML
    private Label answerLabel;

    public OpenEndedTestPage(OpenEndedTest openEndedTest) {
        super(FXML);
        this.openEndedTest = openEndedTest;
        this.card = openEndedTest.getCurrCard();
        this.question = card.getQuestion().value;
        this.answer = "";
        this.userAnswer = "";
        questionLabel.setText(question);
        userAnswerLabel.setText(userAnswer);
        answerLabel.setText(answer);

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleOpenEndedTestShowNextQuestion(OpenEndedTestShowNextQuestionEvent event) {
        this.card = event.card;
        this.question = this.card.getQuestion().value;
        this.answer = "";
        this.userAnswer = "";
        questionLabel.setText(question);
        userAnswerLabel.setText(userAnswer);
        answerLabel.setText(answer);
    }

    @Subscribe
    private void handleOpenEndedTestShowAnswer(OpenEndedTestShowAnswerEvent event) {
        this.userAnswer = event.userAnswer;
        this.answer = this.card.getAnswer().toString();
        userAnswerLabel.setText(this.userAnswer);
        answerLabel.setText(this.answer);
    }
}
