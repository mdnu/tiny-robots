package numd.coffeequiz;

/**
 * CONTROLLER layer
 *
 * Created by m on 08/01/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_sumatra, true),
            new Question(R.string.question_americano, true),
            new Question(R.string.question_macchiato, false),
            new Question(R.string.question_french, true),
            new Question(R.string.question_flat, true),
    };
    // Holds the value which CheatActivity passes
    private boolean[] mIsCheater = new boolean[mQuestionBank.length];
    // Add TAG constant.
    private static final String TAG = "QuizActivity";
    // Add key 'index' for saving the instance state's index.
    private static final String KEY_INDEX = "index";
    // Add key 'cheat' for saving the instance state's 'cheat' boolean.
    private static final String KEY_CHEAT = "cheated"; // Challenge #2
    // Add request code for CheatActivity
    private static final int REQUEST_CODE_CHEAT = 0;

    // Start logging methods.
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    // End logging methods.

    // Helper method which updates questions in question text view.
    private void updateQuestion() {
        // Logging exceptions in method #1 (Optional, for debugging purposes).
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());

        // Get reference ID to question from the Question array.
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // Sets current question in question_text_view to the question associated with
        // the above reference.
        mQuestionTextView.setText(question);
    }

    // Helper method for checking answer result.
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if (mIsCheater[mCurrentIndex]) {
            // Sets if user cheated.
            messageResId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        // Insert a toast (use code completion to use makeText method)
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    // Called when an instance of the activity subclass is created. Gets the UI for the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Call Log.d to log a message.
        Log.d(TAG, "onCreate(Bundle) called");

        // The below method is called to 'inflate' the layout and place it
        // on the screen. When a layout is inflated, each widget in it
        // is instantiated as defined by its XML attributes.
        setContentView(R.layout.activity_quiz);

        // To access a resource (like activity_quiz.xml above) in code, you use its
        // resource ID. In this case, it's 'R.layout.activity_quiz'.

        // Get references to inflated widgets using their resource IDs.
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment the count by 1 mod the length of the question bank.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                // Gets the next question.
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);

        // Assign a listener for a button press event for 'true_button' above.
        // The listener implemented is OnClickListener() as the argument for the method
        // setOnClickListener. It is an anonymous inner class. Everything within the outermost
        // parentheses is passed into setOnClickListener(OnClickListener). Within the parentheses,
        // you create a new nameless class and pass its entire implementation.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // onClick is OnClickListener's only method.
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // Similarly as above.
        mFalseButton= (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // decrement the count by 1 mod the length of the question bank.
                // Notice how the 'decrement' is always non-negative
                mCurrentIndex = (mCurrentIndex + (mQuestionBank.length - 1)) % mQuestionBank.length;

                // Gets the next question.
                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // increment the count by 1 mod the length of the question bank.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater[mCurrentIndex] = false; // Challenge #3

                // Gets the next question.
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                // Start CheatActivity:
                // Create an intent to tell the ActivityManager to start CheatActivity.
                // ActivityManager checks the manifest for a class named 'CheatActivity'
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                // The 'extra' here is the boolean value above. We send this to CheatActivity.
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);

                //startActivity(i);
                // Starts activity like above, but also requests for verification from CheatActivity
                // for whether or not the user cheated.
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        // Check for savedInstanceState. If exists, assign to mCurrentIndex.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBooleanArray(KEY_CHEAT); // Challenge #3
        }

        // Gets the first question.
        updateQuestion();
    }

    // Method which decodes the result intent and extras from CheatActivity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }

    // Saves instance state.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_CHEAT, mIsCheater); // Challenge #2, #3
    }

    // Ignore for now.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    // Ignore for now.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
