package numd.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch when the Start button is pressed
    public void onClickStart(View view) {
        running = true;
    }

    // Stop the stopwatch when the Stop button is pressed
    public void onClickStop(View view) {
        running = false;
    }

    // Reset the stopwatch when the Reset button is pressed
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // Sets the number of seconds displayed on the timer
    private void runTimer() {
        // Gets the TextView XML element and assigns it the label 'timeView'
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();

        // Use a handler to post code
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                // Sets the text in the TextView 'timeView'
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                // Post the code again with a one second delay
                handler.postDelayed(this, 1000);
            }
        });
    }
}
