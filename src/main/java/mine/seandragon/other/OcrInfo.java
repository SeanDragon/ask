package mine.seandragon.other;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanDragon
 * <p>
 * Create By 2018-01-12 14:49
 */
public class OcrInfo implements java.io.Serializable {
    private String question;
    private List<String> answers;

    public OcrInfo() {
        this.answers = new ArrayList<>(4);
    }

    public OcrInfo(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public OcrInfo setQuestion(String question) {
        this.question = question;
        return this;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public OcrInfo setAnswers(List<String> answers) {
        this.answers = answers;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("question", question)
                .add("answers", answers)
                .toString();
    }
}
