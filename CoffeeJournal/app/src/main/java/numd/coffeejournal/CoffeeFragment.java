package numd.coffeejournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * controller layer (II of IV)
 *
 * Presents details of a coffee-tasting journal entry, and allows
 * the user to update these details.
 *
 * Created by m on 16/01/2016.
 */

public class CoffeeFragment extends Fragment {

    private static final String ARG_COFFEE_ID = "whatever";
    private static final String DIALOG_DATE = "whatever";
    private static final String DIALOG_TIME = "whatever";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Coffee mCoffee;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mCompleteCheckBox;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");

    // Add Static method newInstance() to Fragment class.
    // Creates fragment instance, bundles up and sets its arguments.
    // Instead of calling the constructor, the hosting activity calls this newInstance()
    // to get an instance of this fragment.

    public static CoffeeFragment newInstance(UUID coffeeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COFFEE_ID, coffeeId);

        CoffeeFragment fragment = new CoffeeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Public onCreate method (as opposed to protected)
    // so that any activity hosting the fragment can call it.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCoffee = new Coffee();
        UUID coffeeId = (UUID)getArguments().getSerializable(ARG_COFFEE_ID);
        mCoffee = CoffeeBar.get(getActivity()).getCoffee(coffeeId);
    }

    // Inflates the fragment's view layout, and returns the inflated view to the host activity.
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
    }

    private void updateDate() {
        mDateButton.setText(simpleDateFormat.format(mCoffee.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(DateFormat.format("h:mm a", mCoffee.getDate()));
    }
}
