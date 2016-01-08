package numd.coffeequiz;

/**
 * MODEL layer
 *
 * Created by m on 08/01/2016.
 */
public class Question {

    // member variables
    private int mTextResId;
    private boolean mAnswerTrue;

    // constructor
    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    // To automatically create the getter and setter, right-click here in Android Studio
    // and select 'Generate...', and generate a getter and a setter.

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
