package com.hfad.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    // Call onSendMessage() when the button is clicked
    public void onSendMessage(View view) {
        // Creates an EditText View object called 'messageView' assigned to the 'message' TextView
        // in content_receive_message.xml
        EditText messageView = (EditText) findViewById(R.id.message);

        // Creates a String object called 'messageText' from the messageView EditText object
        // by taking its TextView text and converting it to a String value
        String messageText = messageView.getText().toString();

        // Create a new Intent object called 'intent', from this class (CreateMessageActivity.class)
        // and to the ReceiveMessageActivity class. (Think of it as an envelope)
        //Intent intent = new Intent(this, ReceiveMessageActivity.class);

        // Takes the String object messageText and places it into the EXTRA_MESSAGE String variable
        // in the ReceiveMessageActivity class
        //intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, messageText);

        // Creates a new Intent object like above, but implicitly directed to use a send action
        Intent intent = new Intent(Intent.ACTION_SEND);

        // Takes the String object messageText, as above, and sends it implicitly with the above
        // intent as the 'messageText' field
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);

        // Starts the intent declared above, which starts ReceiveMessageActivity
        // This is passed to Android with an instruction to start the activity
        // along with the messageText to be placed into the EXTRA_MESSAGE field
        //startActivity(intent);

        // Optionally, we create a chooser below, such that the user will always be prompted
        // the option of choosing where to send the intent.
        // -----
        String chooserTitle = getString(R.string.chooser);

        // Creates a new explicit intent from the original implicit intent. This intent is a
        // chooser with specified chooser title containing the information from the original
        // intent
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);

        // Similar as above
        startActivity(chosenIntent);
    }
}
