package numd.coffeequiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    // Add boolean variable mAnswerIsTrue, whose value is retrieved from the intent
    // sent by QuizActivity
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mPressedShowAnswer;
    // Add TAG constant.
    private static final String TAG = "CheatActivity";
    // Add key for CheatActivity
    private static final String EXTRA_ANSWER_IS_TRUE = "numd.coffeequiz.answer_is_true";
    // Add result key for whether the answer was shown.
    private static final String EXTRA_ANSWER_SHOWN = "numd.coffeequiz.answer_shown";
    // Add a result key for whether user cheated.
    private static final String KEY_CHEATED = "cheated";

    // Static method which allows us to create a properly configured Intent with the proper extras.
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    // Extra method to help decode the result intent.
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Gets the value of mAnswerIsTrue from the Intent.
        // Note that CheatActivity.getIntent() will always return the intent which
        // started CheatActivity, in this case, QuizActivity.
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // Enable Cheating.
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    // Gets the value of what the answer to the question is
                    // and returns the appropriate value of T or F.
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }

                // Confirm that the user cheated.
                mPressedShowAnswer = true; // Challenge #1
                setAnswerShownResult(mPressedShowAnswer);
            }
        });

        // Check if a savedInstanceState exists, if so get the value of mPressedShowAnswer.
        if (savedInstanceState != null) {
            mPressedShowAnswer = savedInstanceState.getBoolean(KEY_CHEATED);
            setAnswerShownResult(mPressedShowAnswer);
        }
    }

    // Override onSaveInstanceState(...) to include the mPressedShowAnswer boolean with the bundle.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() called");
        outState.putBoolean(KEY_CHEATED, mPressedShowAnswer);
    }

    // Helper method to confirm user cheated.
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();

        // intent puts the boolean result taken from the method's input as an extra.
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
        // the onActivityResult method in QuizActivity will take the request code, result code
        // and the intent data.
    }
}
