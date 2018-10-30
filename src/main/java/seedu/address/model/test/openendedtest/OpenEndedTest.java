package seedu.address.model.test.openendedtest;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.topic.Topic;
import seedu.address.ui.test.TriviaTestPage;
import seedu.address.ui.test.TriviaTestResultPage;
import seedu.address.ui.test.openendedtest.OpenEndedTestPage;
import seedu.address.ui.test.openendedtest.OpenEndedTestResultPage;

/**
 * Represents a trivia test that is started by the user.
 * For a {@code OpenEndedTest} to start, there must be at least 1 card related to the topic
 * that is specified in the test.
 */
public class OpenEndedTest extends TriviaTest {
    public static final String MESSAGE_TEST_CONSTRAINS = "OpenEndedTest needs at least 1 card with the"
            + " corresponding topic to proceed.";

    public final TestType testType = TestType.OPEN_ENDED_TEST;
    private List<Attempt> attempts;

    private ArrayList<Card> shuffledCards;
    private ArrayList<Card> referenceCards;

    public OpenEndedTest(Topic tag, ReadOnlyTriviaBundle triviaBundle) {
        super(tag, triviaBundle);

        shuffledCards= shuffleCards(cards);

        attempts = new ArrayList<>();

        checkArgument(isValidTest(), MESSAGE_TEST_CONSTRAINS);
    }

    /**
     * Retrieve a randomized list of cards
     * @param cards The cards to shuffle from.
     * @return an list of cards
     */
    private ArrayList<Card> shuffleCards(List<Card> cards) {
        ArrayList<Card> shuffledCards = new ArrayList<Card>(cards);
        Collections.shuffle(shuffledCards);
        referenceCards = new ArrayList<Card>(shuffledCards);
        return shuffledCards;
    }

    public Card getNextCard() {
        if (shuffledCards.size() == 0) {
            return null;
        }
        Card nextCard = shuffledCards.get(0);
        shuffledCards.remove(0);
        return nextCard;
    }

    public ArrayList<Card> getReferenceCards() {
        return this.referenceCards;
    }

    private boolean isEndOfTest() {
        return shuffledCards.isEmpty();
    }

    @Override
    public void startTest() { startTimer(); }

    @Override
    public void stopTest() {
        timer.stop();
    }


    @Override
    public TestType getTestType() {
        return testType;
    }

    @Override
    public List<Attempt> getAttempts() {
        return attempts;
    }


    private boolean isValidTest() {
        return shuffledCards.size() >= 1;
    }

    /**
     * Starts the timer of the test.
     */
    private void startTimer() {
        DecimalFormat timerFormat = new DecimalFormat("#.#");
        timer = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
            duration.setValue(Double.parseDouble(timerFormat.format(duration.getValue() + 0.1)));
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    @Override
    public Supplier<? extends TriviaTestPage> getTestingPage() { return () -> new OpenEndedTestPage(null); }

    @Override
    public Supplier<? extends TriviaTestResultPage> getResultPage() {
        return () -> new OpenEndedTestResultPage(null);
    }

}
