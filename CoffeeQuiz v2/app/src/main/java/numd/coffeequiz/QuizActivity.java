package numd.coffeequiz;
/**
 * CONTROLLER layer
 *
 * Created by m on 08/01/2016.
 */
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_sumatra, true),
            new Question(R.string.question_americano, true),
            new Question(R.string.question_macchiato, false),
            new Question(R.string.question_french, true),
            new Question(R.string.question_flat, true),
    };

    private int mCurrentIndex = 0;

    // Helper method #1, updates questions in question text view.
    private void updateQuestion() {
        // Get reference ID to question from the Question array.
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // Sets current question in question_text_view to the question associated with
        // the above reference.
        mQuestionTextView.setText(question);
    }

    // Helper method #2
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        // Insert a toast (use code completion to use makeText method)
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    // Method #1 - onCreate(Bundle)
    // Called when an instance of the activity subclass is created.
    // Gets the UI for the activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Below code will inflate the VIEW layer widgets
         */
        // The below method is called to 'inflate' the layout and place it
        // on the screen. When a layout is inflated, each widget in it
        // is instantiated as defined by its XML attributes.
        setContentView(R.layout.activity_quiz);
        // To access a resource (like activity_quiz.xml above) in code, you use its
        // resource ID. In this case, it's 'R.layout.activity_quiz'.

        // Get references to inflated widgets using their resource IDs.
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        /**
         * Challenge #1 of 3 - Add a Listener to the TextView
         */
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment the count by 1 mod the length of the question bank.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                // Gets the next question.
                updateQuestion();
            }
        });
        /** Complete.
         */
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

        /**
         * Challenge #2 of 3 - Add a Previous Button
         */
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
        /** Complete.
         */

        /**
         * Challenge #3 of 3 - From Button to ImageButton
         *  i.e. implement icon-only buttons.
         */
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // increment the count by 1 mod the length of the question bank.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                // Gets the next question.
                updateQuestion();
            }
        });
        /** Complete.
         */

        // Gets the first question.
        updateQuestion();
    }

    // Method #2
    // Ignore for now.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    // Method #3
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
