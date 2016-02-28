package numd.coffeejournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * controller layer (IV of V)
 * Created by m on 22/01/2016.
 */

public class CoffeeListFragment extends Fragment {

    // Displays a list of Coffee objects to the user using a RecyclerView.
    // Not the same as 'storing' the Coffee objects. That's done in the CoffeeBar class.
    // Hooks up the RecyclerView in fragment_coffee_list.xml to CoffeeListFragment.

    // We have several fragment methods and two inner classes.
    // Home methods:
    // An 'updateSubtitle' method that sets the toolbar's subtitle.
    // An 'updateUI' method that updates the values in the subtitle.

    // Fragment methods:
    // ================
    // An 'onCreate' overriding method.
    //  Tells the FragmentManager that our fragment should receive a call to onCreateOptionsMenu(...)
    // An 'onCreateView' overriding method, which inflates and updates our view.
    // An 'onResume' overriding method that updates the UI as well.
    // An 'onSaveInstanceState' overriding method
    // An 'onCreateOptionsMenu' overriding method that inflates a menu resource.
    // An 'onOptionsItemSelected' overriding method that creates the options menu.

    // "CoffeeAdapter" inner class (extends Adapter class).
    // ===========================
    // Two Adapter methods:
    //  An 'onBindViewHolder' overriding method.
    //  An 'onCreateViewHolder' overriding method.
    // A 'getItemCount' overriding method.
    // A 'CoffeeAdapter' constructor method.
    // A 'setCoffees' setter method.

    // "CoffeeHolder" inner class (extends ViewHolder, implements onClickListener)
    // ===========================
    // An 'onClick' method that implements onClickListener
    // A 'CoffeeHolder' constructor which wires up the Views.
    // A 'bindCoffee' method that sets the values for the Views.

    private RecyclerView mCoffeeRecyclerView;
    private CoffeeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private int mLastAdapterClickPosition = -1;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");
    private static final String SAVED_SUBTITLE_VISIBLE = "whatever";

    /* Fragment methods start here */
    // CoffeeListFragment's "onCreate" overriding method.
    // Standard onCreate, though we include a 'setHasOptionsMenu' call.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // we have to tell the FragmentManager that our fragment should receive
        // a call to onCreateOptionsMenu(...)
        setHasOptionsMenu(true);
    }

    // CoffeeListFragment's "onResume" overriding method.
    // When CoffeeListActivity is created, it receives a call to onResume() from the OS.
    // Once received, the FragmentManager calls onResume() on the fragments that it's hosting.
    // Here, the only fragment CoffeeListActivity is hosting is this one, CoffeeListFragment.
    // We include an updateUI() call to reload the list of Coffee objects.
    @Override
    public void onResume() {
        super.onResume();
        // Call to updateUI() method below.
        updateUI();
    }

    // CoffeeListFragment's "onCreateView" overriding method.
    // Sets up the View for CoffeeListFragment.
    // Hooks up the fragment_coffee_list.xml layout file,
    // and finds the RecyclerView in the layout file.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_list, container, false);

        // Set our created mCoffeeRecyclerView from above to find the coffee_recycler_view View.
        mCoffeeRecyclerView = (RecyclerView) view.findViewById(R.id.coffee_recycler_view);
        // We give mCoffeeRecyclerView a LayoutManager.
        // Note that RecyclerView always requires a LayoutManager to work.
        // Without one, the app will crash.
        mCoffeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Saves subtitle visibility state between app rotations.
        // If a saved instance state exists, retrieve and set the assigned subtitle.
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        // Call to updateUI() method below.
        updateUI();
        // Returns configured View object.
        return view;
    }

    // CoffeeListFragment's "onSaveInstanceState" overriding method.
    // Overwritten to include subtitle-saving functionality.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Use saved instance state mechanism to save the subtitle.
        // This allows us to save 'mSubtitleVisible' across rotations of the app.
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    // CoffeeListFragment's "onCreateOptionsMenu" overriding method.
    // Creates a menu, implemented in a Fragment.
    // Inflates the menu resource defined in fragment_coffee_list.xml.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Call MenuInflater.inflate(int, Menu) and pass in the resource ID of menu file.
        // In this case, fragment_coffee_list.
        // This populates the Menu instance with items defined in our file.
        inflater.inflate(R.menu.fragment_coffee_list, menu);

        // Updates 'Show #' MenuItem.
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    // CoffeeListFragment's "onOptionsItemSelected" overriding method.
    // Responds to user interaction with the specified MenuItem.
    // MenuItem is chosen by 'item.getItemId()' call.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_coffee :
                // Add new coffee item.
                Coffee coffee = new Coffee();
                CoffeeBar.get(getActivity()).addCoffee(coffee);
                Intent intent = CoffeePagerActivity
                        .newIntent(getActivity(), coffee.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle :
                // Triggers a re-creation of the action items when
                // user presses the "Show #" action item.
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                // Calls updateSubtitle() method.
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /* Fragment methods end here */

    // CoffeeListFragment's "updateSubtitle" private method.
    // Sets the subtitle of the toolbar.
    private void updateSubtitle() {
        CoffeeBar coffeeBar = CoffeeBar.get(getActivity());
        int coffeeCount = coffeeBar.getCoffees().size();
        // Generates the subtitle string using the getString(...) method:
        String subtitle = getString(R.string.subtitle_format, coffeeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        // Activity hosting the CoffeeListFragment is cast to an AppCompatActivity object.
        // CoffeeJournal uses the AppCompat library, so all activities will be a subclass
        // of AppCompatActivity, which allows us to access the toolbar.
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    // CoffeeListFragment's "updateUI" private method.
    // Connects the RecyclerView (mCoffeeRecyclerView) to our Adapter (mAdapter, a CoffeeAdapter).
    public void updateUI() {
        CoffeeBar coffeeBar = CoffeeBar.get(getActivity());
        List<Coffee> coffees = coffeeBar.getCoffees();

        if (mAdapter == null) {
            // Create new Adapter (if it doesn't exist already) and connects it
            // to the RecyclerView.
            mAdapter = new CoffeeAdapter(coffees);
            mCoffeeRecyclerView.setAdapter(mAdapter);
        } else {
            if (mLastAdapterClickPosition < 0) {
                // updates the main list fragment if the adapter contents have changed.
                // 'else' logic: if position has changed, reload the last adapter position.
                mAdapter.setCoffees(coffees);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemChanged(mLastAdapterClickPosition);
                mLastAdapterClickPosition = -1;
            }

            // Call to updateSubtitle method.
            updateSubtitle();
        }
    }

    /* Inner Classes start here */
    // 'CoffeeHolder' ViewHolder private inner class.
    // Retrieve data from list_item_coffee.xml
    // Implement View.OnClickListener to listen for presses.
    private class CoffeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCompleteCheckBox;
        private Coffee mCoffee;

        // CoffeeHolder inner class' "CoffeeHolder" public constructor.
        // Maintains references to several View objects.
        public CoffeeHolder(View itemView) {
            super(itemView);
            // Set CoffeeHolder as the receiver for click events.
            itemView.setOnClickListener(this);
            // Set the references to the View objects below.
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_date_text_view);
            mCompleteCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_coffee_complete_check_box);
        }

        // CoffeeHolder inner class' "bindCoffee" method.
        // Set up values for the wired up views.
        // Given a Coffee object, CoffeeHolder will update the title, date and checkbox.
        public void bindCoffee(Coffee coffee) {
            mCoffee = coffee;
            mTitleTextView.setText(mCoffee.getTitle());
            mDateTextView.setText(simpleDateFormat.format(mCoffee.getDate()) + ". " + DateFormat.format("h:mm a", mCoffee.getDate()));
            mCompleteCheckBox.setChecked(mCoffee.isComplete());
        }

        // CoffeeHolder inner class' "onClick" overriding method.
        // Sets up onClick behavior.
        @Override
        public void onClick(View v) {
            // Start CoffeePagerActivity using an intent.
            // Get position of item. We use this in updateUI() method logic.
            mLastAdapterClickPosition = getAdapterPosition();
            // UPDATE: Switched from CoffeeActivity to CoffeePagerActivity.
            Intent intent = CoffeePagerActivity.newIntent(getActivity(), mCoffee.getId());
            startActivity(intent);
        }

    }

    // 'CoffeeAdapter' adapter inner class.
    // RecyclerView communicates with this adapter when a ViewHolder needs to be created
    // or connected with a Coffee object.
    private class CoffeeAdapter extends RecyclerView.Adapter<CoffeeHolder> {

        private List<Coffee> mCoffees;
        public CoffeeAdapter(List<Coffee> coffees) {
            mCoffees = coffees;
        }

        // CoffeeAdapter inner class' 'onCreateViewHolder' overriding method.
        // Calls fellow inner class "CoffeeHolder"
        // Called by the RecyclerView when it needs a new View to display an item.
        @Override
        public CoffeeHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            // Create a View, inflated from an android library layout, and wrap it in a ViewHolder.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // Create View and wrap it in a ViewHolder.
            // Inflate the "list_item_coffee" XML layout.
            View view = layoutInflater.inflate(R.layout.list_item_coffee, parent, false);
            return new CoffeeHolder(view);
        }

        // CoffeeAdapter inner class' 'onBindViewHolder' overriding method.
        // Binds a ViewHolder's View to model object.
        // Takes as input a ViewHolder and a position in the data set.
        @Override
        public void onBindViewHolder(CoffeeHolder holder, int position) {
            // Use position to find correct model data.
            Coffee coffee = mCoffees.get(position);
            // update the View to reflect the above model data.
            // Connect the CoffeeAdapter to the above CoffeeHolder
            // i.e. get all the info on views and their values.
            holder.bindCoffee(coffee);
        }

        // CoffeeAdapter inner class' getters and setters.
        // Self-explanatory.
        @Override
        public int getItemCount() {
            return mCoffees.size();
        }

        // Setter.
        public void setCoffees(List<Coffee> coffees) {
            mCoffees = coffees;
        }
    }
    /* Inner Classes end here */
}
