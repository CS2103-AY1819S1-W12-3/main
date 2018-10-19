package seedu.address.storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.topic.Topic;

/**
 * JAXB-friendly version of the TriviaResult.
 */
public class XmlAdaptedTriviaResult {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Trivia Test's %s field is missing!";


    @XmlElement(required = true)
    private String testType;
    @XmlElement(required = true)
    private String topic;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String duration;

    @XmlElement(required = true)
    private List<XmlAdaptedAttempt> attempt;

    /**
     * Constructs an XmlAdaptedTriviaResult.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTriviaResult() {}

    /**
     * Constructs an {@code XmlAdaptedTriviaResult} with the given card details.
     */
    public XmlAdaptedTriviaResult(String testType, String topic, String date,
                                  String duration, List<XmlAdaptedAttempt> attempt) {
        this.testType = testType;
        this.topic = topic;
        this.date = date;
        this.duration = duration;
        this.attempt = new ArrayList<>(attempt);
    }

    /**
     * Converts a given Card into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCard
     */
    public XmlAdaptedTriviaResult(TriviaResult source) {
        testType = source.getTestType().toString();
        topic = source.getTopic().topicName;
        date = DateUtil.format(source.getTestDate());
        duration = String.valueOf(source.getDuration());
        attempt = source.getAttempts().stream()
                .map(XmlAdaptedAttempt::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted card object into the model's TriviaResult object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card
     */
    public TriviaResult toModelType() throws IllegalValueException {
        final List<Attempt> attempts = new ArrayList<>();
        for (XmlAdaptedAttempt attempt : attempt) {
            attempts.add(attempt.toModelType());
        }

        /** Parse testType */
        if (testType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TestType.class.getSimpleName()));
        }
        if (!TestType.consistOf(testType)) {
            throw new IllegalValueException(TestType.MESSAGE_TEST_TYPE_CONSTRAINTS);
        }
        final TestType testTypeEnum = TestType.valueOf(testType);

        /** Parse topic */
        if (topic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Topic.class.getSimpleName()));
        }
        if (!Topic.isValidTopicName(topic)) {
            throw new IllegalValueException(Topic.MESSAGE_TOPIC_CONSTRAINTS);
        }
        final Topic modelTopic = new Topic(topic);

        /** Parse date */
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelDate;
        try {
            modelDate = DateUtil.parse(date);
        } catch (ParseException e) {
            throw new IllegalValueException(DateUtil.MESSAGE_DATE_CONSTRAINTS);
        }

        /** Parse duration */
        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Duration"));
        }
        final double modelDuration;
        try {
            modelDuration = Double.parseDouble(duration);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(NumberFormatException.class.getSimpleName());
        }

        return new TriviaResult(testTypeEnum, modelTopic, modelDate, modelDuration, attempts);
    }
}