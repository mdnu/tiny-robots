package numd.coffeejournal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * controller layer (II of II)
 *
 * Presents details of a coffee-tasting journal entry, and allows
 * the user to update these details.
 *
 * Created by m on 16/01/2016.
 */

public class CoffeeFragment extends Fragment {

    private Coffee mCoffee;
    private EditText mTitleField;

    // Public onCreate method (as opposed to protected)
    // so that any activity hosting the fragment can call it.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoffee = new Coffee();
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

        return v;
    }

}
