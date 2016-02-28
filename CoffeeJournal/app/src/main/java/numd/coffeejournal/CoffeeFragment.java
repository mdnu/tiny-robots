package numd.coffeejournal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * controller layer (II of V)
 * Created by m on 16/01/2016.
 */

public class CoffeeFragment extends Fragment {

    // Allows user interaction with Coffee objects.
    // i.e. The Fragment with "Project Title" and "Coffee Details".
    // Basically, the CoffeeFragment displays details of a Coffee object and interacts
    // with the View layer to update/change the properties of the Coffee object.
    // These are the Fragments that CoffeePagerActivity displays.

    // We have the following methods:
    // (Creates Fragments). A static newInstance method
    //  to create a Fragment instance using a given Coffee UUID,
    //  setting the arguments of the Fragment, and then returning the configured Fragment.

    // (Creates and displays the Coffee View). A public 'onCreate' overriding method.
    //  This method extracts the UUID of the Coffee from 'ARG_COFFEE_ID', gets the Coffee
    //  from the CoffeeBar (using its get method) which returns the CoffeeBar.
    //  method is public so that any activity hosting CoffeeFragment can call it.

    // (Updating method). An 'onPause' overriding method
    //  which, when called, pushes any updates made to the
    //  current viewed Coffee object to the CoffeeLab.

    // (Creates View). An 'onCreateView' overriding method
    //  which creates, configures and returns the View.
    //  We'll explain below.

    // (Updates Buttons). An 'onActivityResult' overriding method.
    //  which retrieves the Intent's extra, sets the date on the Coffee object,
    //  and refreshes the text of the date button. We'll explain below.

    // Two helper methods, updateDate and updateTime
    //  which updates the dates and time on the date and time buttons.

    // (Generates report). A private 'getCoffeeReport' method.
    //  which generates a report for the Coffee object. We'll explain below.

    // (Top Menu Inflater). An 'onCreateOptionsMenu' overriding method
    //  which inflates the secondary menu. i.e. the top menu, which contains the delete button.

    // (Entry Deletion Functionality). An 'onOptionsItemSelected' overriding method
    //  which allows for entry deletion. i.e. it's related to the above method. We'll explain below.

    private static final String ARG_COFFEE_ID = "whatever";
    private static final String DIALOG_DATE = "whatever";
    private static final String DIALOG_TIME = "whatever";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = -200;
    private static final int REQUEST_CONTACT = 1;

    private Coffee mCoffee;
    private EditText mTitleField;
    private CheckBox mCompleteCheckBox;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mFriendButton;
    private Button mReportButton;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");

    // CoffeeFragment's "newInstance" method
    // A static method to create fragments using a given Coffee object UUID.
    // How: Create a new Bundle of arguments 'args'. Then...
    public static CoffeeFragment newInstance(UUID coffeeId) {
        Bundle args = new Bundle();
        // Use input UUID and Coffee ID "ARG_COFFEE_ID" into the args bundle.
        args.putSerializable(ARG_COFFEE_ID, coffeeId);

        // Create a new (empty) CoffeeFragment.
        CoffeeFragment fragment = new CoffeeFragment();
        // Set the CoffeeFragment to display the desired Coffee using the args Bundle.
        fragment.setArguments(args);
        // Return the configured CoffeeFragment.
        return fragment;
    }

    // CoffeeFragment's "onCreate" overriding method.
    // Creates and displays the Coffee View.
    // How: Creates a Fragment using the savedInstanceState, and...
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Extracts the UUID of the desired Coffee object from 'ARG_COFFEE_ID', and...
        UUID coffeeId = (UUID)getArguments().getSerializable(ARG_COFFEE_ID);
        // uses this UUID to get the Coffee object from the CoffeeBar.
        mCoffee = CoffeeBar.get(getActivity()).getCoffee(coffeeId);
        // Also, enables the options menu.
        setHasOptionsMenu(true);
    }

    // CoffeeFragment's "onPause" overriding method.
    // When we call this method, it pushes any updates made
    // to the current viewed Coffee object to the Coffee lab.
    // How: We pause the Fragment, then...
    @Override
    public void onPause() {
        super.onPause();
        // Access the CoffeeBar, use its 'get' method, passing to it
        // our current Activity Context, and then updating the Coffee object
        // obtained from the Context with the new mCoffee Coffee object.
        CoffeeBar.get(getActivity()).updateCoffee(mCoffee);
    }

    // CoffeeFragment's "onCreateView" overriding method.
    // Creates, configures and returns(inflates) the View object.
    // How:
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Explicitly inflates, passing in layout resource ID.
        // 2nd parameter is view's parent. 3rd parameter asks whether to add the inflated view
        // to the view's parent. Here we say 'false' since we're adding the view to the
        // activity code.
        View v = inflater.inflate(R.layout.fragment_coffee, container, false);

        // Wire up the EditText widget: get a reference and add a listener.
        mTitleField = (EditText) v.findViewById(R.id.coffee_title);
        mTitleField.setText(mCoffee.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally blank.
            }

            // sub-method to change Title text.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCoffee.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Intentionally blank.
            }
        });

        // Get reference to button XML object and set button text.
        // Show the DialogFragment
        mDateButton = (Button)v.findViewById(R.id.coffee_date);
        updateDate();

        // Wire up the date button to the DatePicker fragment.
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();

                // Get the date:
                // Call DatePickerFragment.newInstance(Date), where we use the date
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCoffee.getDate());
                // stored in the current mCoffee object.
                // Set the date extracted from the DatePicker as the button date.
                dialog.setTargetFragment(CoffeeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // Do the same for the time fragment.
        mTimeButton = (Button) v.findViewById(R.id.coffee_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCoffee.getDate());
                dialog.setTargetFragment(CoffeeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        // Wire up CheckBox XML object. ie. get a reference and set a listener.
        mCompleteCheckBox = (CheckBox)v.findViewById(R.id.coffee_completed);
        mCompleteCheckBox.setChecked(mCoffee.isComplete());
        mCompleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the coffee-tasting event to 'completed'.
                mCoffee.setComplete(isChecked);
            }
        });

        // Add and wire up a report button
        mReportButton = (Button)v.findViewById(R.id.coffee_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implicit intent
                //Intent i = new Intent(Intent.ACTION_SEND);
                //i.setType("text/plain");
                // Include text of report, and string for subject of report
                // both as extras.
                //i.putExtra(Intent.EXTRA_TEXT, getCoffeeReport());
                //i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_report_subject));
                // Create chooser
                //i = Intent.createChooser(i, getString(R.string.send_report));
                //startActivity(i);
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getCoffeeReport())
                        .setSubject(getString(R.string.coffee_report))
                        .setChooserTitle(getString(R.string.send_report))
                        .startChooser();
            }
        });

        // Get a reference to mFriendButton and set a listener on it.
        // In the listener's implementation, create the implicit intent and pass it into
        // startActivityForResult(...). Also, once a friend is assigned, show the name on the
        // friend button.
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mFriendButton = (Button)v.findViewById(R.id.coffee_friend);
        mFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if (mCoffee.getFriend() != null) {
            mFriendButton.setText(mCoffee.getFriend());
        }

        // Guard against no contacts app
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            // If there is no contacts app installed,
            // disable the friend button.
            mFriendButton.setEnabled(false);
        }

        return v;
    }

    // CoffeeFragment's "onActivityResult" overriding method.
    // Retrieves the Intent's Extra, sets the date on the Coffee object,
    // and refreshes the text of the date button.
    // How:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If resultCode isn't our desired result
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        // Get the Date from the Extra.
        Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        // Set the date.
        mCoffee.setDate(date);

        // Use the input requestCode, and...
        switch (requestCode) {
            case (REQUEST_DATE) : {
                // If we are updating the date, do so:
                updateDate();
                break;
            }
            case (REQUEST_TIME) : {
                // If we are updating the time, do so:
                updateTime();
                break;
            }
        }

        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return
            // values for.
            String [] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            // Perform your query - the contactUri is like a "where" clause here.
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
            try {
                // Double-check that you actually have results
                if (c.getCount() == 0) {
                    return;
                }

                // Extract the first column of the first row of data -
                // that is your friend's name.
                c.moveToFirst();
                String friend = c.getString(0);
                mCoffee.setFriend(friend);
                mFriendButton.setText(friend);
            } finally {
                c.close();
                getActivity().finish();
            }
        }
    }

    // CoffeeFragment's private "updateDate" helper method.
    // Sets the new date displayed on the Date button.
    private void updateDate() {
        mDateButton.setText(simpleDateFormat.format(mCoffee.getDate()));
    }

    // CoffeeFragment's private "updateTime" helper method.
    // Sets the new time displayed on the Time button.
    private void updateTime() {
        mTimeButton.setText(DateFormat.format("h:mm a", mCoffee.getDate()));
    }

    // CoffeeFragment's private "getCoffeeReport" method.
    // Generates a report for the Coffee object. How:
    private String getCoffeeReport() {
        // Create empty String to fill in.
        String completeString = null;
        if (mCoffee.isComplete()) {
            // If we've completed the Coffee tasting, say so.
            completeString = getString(R.string.coffee_report_complete);
        } else {
            // If not, say so.
            completeString = getString(R.string.coffee_report_incomplete);
        }

        // Create String which outlines the date format to fill in.
        String dateFormat = "EEE, MMM dd";
        // Create and fill in the Date String using the format.
        String dateString = DateFormat.format(dateFormat, mCoffee.getDate()).toString();

        // Get the name of the friend we're having the Coffee with.
        // Self-explanatory.
        String friend = mCoffee.getFriend();
        if (friend == null) {
            friend = getString(R.string.coffee_report_no_friend);
        } else {
            friend = getString(R.string.coffee_report_friend, friend);
        }

        // Generate report using all the info above.
        String report = getString(R.string.coffee_report, mCoffee.getTitle(), dateString, completeString, friend);
        // Return the finished report to output.
        return report;
    }

    // CoffeeFragment's "onCreateOptionsMenu" overriding method.
    // Inflates the secondary (top) menu containing the Delete button. How:
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Create options menu with an inflater.
        super.onCreateOptionsMenu(menu, inflater);
        // Use inflater to inflate the designated top menu fragment.
        inflater.inflate(R.menu.fragment_coffee_two, menu);
    }

    // CoffeeFragment's "onOptionsItemSelected" overriding method.
    // Allows for entry deletion.
    // How: Pass in MenuItem as input, then..
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Get the MenuItem's ID:
        switch (item.getItemId()) {
            // If the MenuItem chosen is for deleting the Coffee object, then...
            case R.id.menu_item_delete_coffee :
                // Display a Toast telling the user that we're deleting the entry.
                Toast.makeText(getActivity(), "Deleting entry!@!@", Toast.LENGTH_LONG).show();
                // Directly delete the Coffee object from the CoffeeBar.
                CoffeeBar.get(getActivity()).deleteCoffee(mCoffee);
                // Finish the Activity cycle (to update the database).
                getActivity().finish();
                // Return.
                //return true;
            default :
                // Otherwise, proceed as usual.
                return super.onOptionsItemSelected(item);
        }
    }

}
