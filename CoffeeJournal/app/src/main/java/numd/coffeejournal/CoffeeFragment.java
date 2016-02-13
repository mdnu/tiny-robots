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
 * controller layer (II of IV)
 * Created by m on 16/01/2016.
 */

public class CoffeeFragment extends Fragment {

    // "CoffeeFragment" control class.
    // These are the Fragments that CoffeePagerActivity displays.
    // Basically, the CoffeeFragment displays details of a Coffee object and interacts
    // with the View layer to update/change the properties of the Coffee object.

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
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 1;

    private Coffee mCoffee;
    private EditText mTitleField;
    private CheckBox mCompleteCheckBox;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mFriendButton;
    private Button mReportButton;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");

    public static CoffeeFragment newInstance(UUID coffeeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COFFEE_ID, coffeeId);

        CoffeeFragment fragment = new CoffeeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCoffee = new Coffee();
        UUID coffeeId = (UUID)getArguments().getSerializable(ARG_COFFEE_ID);
        mCoffee = CoffeeBar.get(getActivity()).getCoffee(coffeeId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        CoffeeBar.get(getActivity()).updateCoffee(mCoffee);
    }

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
        // Default set to disabled state.
        //mDateButton.setEnabled(false);

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
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                // Include text of report, and string for subject of report
                // both as extras.
                i.putExtra(Intent.EXTRA_TEXT, getCoffeeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_report_subject));
                // Create chooser
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        // Get a reference to mFriendButton and set a listener on it.
        // In the listener's implementation, create teh implicit intent and pass it into
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        mCoffee.setDate(date);

        switch (requestCode) {
            case (REQUEST_DATE) : {
                updateDate();
                break;
            }
            case (REQUEST_TIME) : {
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
            // Perform your query - the contactUri is like a "where"
            // clause here
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
            try {
                // Double-check that you actually have results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your friend's name.
                c.moveToFirst();
                String friend = c.getString(0);
                mCoffee.setFriend(friend);
                mFriendButton.setText(friend);
            } finally {
                c.close();
            }
        }
    }

    private void updateDate() {
        mDateButton.setText(simpleDateFormat.format(mCoffee.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(DateFormat.format("h:mm a", mCoffee.getDate()));
    }

    private String getCoffeeReport() {
        String completeString = null;
        if (mCoffee.isComplete()) {
            completeString = getString(R.string.coffee_report_complete);
        } else {
            completeString = getString(R.string.coffee_report_incomplete);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCoffee.getDate()).toString();

        String friend = mCoffee.getFriend();
        if (friend == null) {
            friend = getString(R.string.coffee_report_no_friend);
        } else {
            friend = getString(R.string.coffee_report_friend, friend);
        }

        String report = getString(R.string.coffee_report, mCoffee.getTitle(), dateString, completeString, friend);

        return report;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_coffee_two, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_coffee :
                if (mCoffee != null) {
                    Toast.makeText(getActivity(), "Deleting entry", Toast.LENGTH_LONG).show();
                    CoffeeBar.get(getActivity()).deleteCoffee(mCoffee);
                    getActivity().finish();
                }
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

}
