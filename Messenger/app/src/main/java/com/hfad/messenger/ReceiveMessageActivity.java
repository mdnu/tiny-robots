package com.hfad.messenger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class ReceiveMessageActivity extends Activity {

    // Name of the extra value we're passing in the intent
    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Specifies that it uses the activity_receive_message XML layout, which itself includes
        // the content_receive_message XML layout which we use. This is what is displayed on the
        // device
        setContentView(R.layout.activity_receive_message);

        // Gets the intent
        Intent intent = getIntent();

        // Creates a String object called 'messageText' by calling the intent above and retrieving
        // the String object labelled EXTRA_MESSAGE which takes its value from the "message"
        // TextView XML element
        String messageText = intent.getStringExtra(EXTRA_MESSAGE);

        // Creates a new TextView object called 'messageView' by finding the "message" view
        // and referring to it
        TextView messageView = (TextView) findViewById(R.id.message);

        // Calls the messageView object which is the XML View element 'message' and edits its
        // text to be the value of 'messageText' sent by the CreateMessageActivity class
        messageView.setText(messageText);
    }
}
