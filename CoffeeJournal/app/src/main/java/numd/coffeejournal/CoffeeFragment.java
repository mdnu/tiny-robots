package numd.coffeejournal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.text.format.DateFormat;
import java.text.SimpleDateFormat;
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

    private Coffee mCoffee;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mCompleteCheckBox;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");
    private java.text.DateFormat mTimeFormat;

    // add Static method newInstance() to Fragment class.
    // Creates fragment instance, bundles up and sets its arguments.
    // instead of calling the constructor, the hosting activity calls this newInstance()
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
        //UUID coffeeId = (UUID)getActivity().getIntent().getSerializableExtra(CoffeeActivity.EXTRA_COFFEE_ID);
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

        mTimeFormat = android.text.format.DateFormat.getTimeFormat(this.getActivity());
        String time = mTimeFormat.format(mCoffee.getDate());
        // Get reference to button XML object and set button text.
        mDateButton = (Button)v.findViewById(R.id.coffee_date);
        mDateButton.setText(simpleDateFormat.format(mCoffee.getDate()) + ". " + time);
        // Default set to disabled state.
        mDateButton.setEnabled(false);

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

}
