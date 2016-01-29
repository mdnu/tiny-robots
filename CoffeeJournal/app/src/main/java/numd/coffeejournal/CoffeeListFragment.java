package numd.coffeejournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * controller layer (IV of IV)
 *
 * extends the android Fragment superclass.
 * displays a list of coffees to the user using a RecyclerView.
 *
 * Created by m on 22/01/2016.
 */

public class CoffeeListFragment extends Fragment {

    // Hook up the RecyclerView in fragment_coffee_list.xml to CoffeeListFragment.
    private RecyclerView mCoffeeRecyclerView;
    private CoffeeAdapter mAdapter;
    // Challenge
    private int mLastAdapterClickPosition = -1;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("cccc, MMMM d, yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_list, container, false);

        mCoffeeRecyclerView = (RecyclerView) view.findViewById(R.id.coffee_recycler_view);
        mCoffeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CoffeeBar coffeeBar = CoffeeBar.get(getActivity());
        List<Coffee> coffees = coffeeBar.getCoffees();

        //mAdapter = new CoffeeAdapter(coffees);
        //mCoffeeRecyclerView.setAdapter(mAdapter);
        if (mAdapter == null) {
            mAdapter = new CoffeeAdapter(coffees);
            mCoffeeRecyclerView.setAdapter(mAdapter);
        } else {
            if (mLastAdapterClickPosition < 0) {
                // updates the main list fragment if the adapter contents have changed.
                // 'else' logic: if position has changed, reload the last adapter position.
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemChanged(mLastAdapterClickPosition);
                mLastAdapterClickPosition = -1;
            }
        }
    }

    // 'CoffeeHolder' ViewHolder inner class.
    // Retrieve data from list_item_coffee.xml
    // Implement View.OnClickListener to listen for presses.
    private class CoffeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCompleteCheckBox;
        private Coffee mCoffee;

        // Wire up the views.
        public CoffeeHolder(View itemView) {
            super(itemView);
            // Set CoffeeHolder as the receiver for click events.
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_date_text_view);
            mCompleteCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_coffee_complete_check_box);
        }

        // Set up values for the wired up views.
        public void bindCoffee(Coffee coffee) {
            mCoffee = coffee;
            mTitleTextView.setText(mCoffee.getTitle());
            mDateTextView.setText(simpleDateFormat.format(mCoffee.getDate()) + ". " + DateFormat.format("h:mm a", mCoffee.getDate()));
            mCompleteCheckBox.setChecked(mCoffee.isComplete());
        }

        // Set up toast for onClick press.
        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mCoffee.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

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

        // 'onCreateViewHolder'
        // called by the RecyclerView when it needs a new View to display an item.

        @Override
        public CoffeeHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            // Create a View, inflated from an android library layout, and wrap it in a ViewHolder.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_coffee, parent, false);
            return new CoffeeHolder(view);
        }

        // Method onBindViewHolder.
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

        @Override
        public int getItemCount() {
            return mCoffees.size();
        }

    }
}
