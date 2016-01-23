package numd.coffeejournal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_list, container, false);

        mCoffeeRecyclerView = (RecyclerView) view.findViewById(R.id.coffee_recycler_view);
        mCoffeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        CoffeeBar coffeeBar = CoffeeBar.get(getActivity());
        List<Coffee> coffees = coffeeBar.getCoffees();

        mAdapter = new CoffeeAdapter(coffees);
        mCoffeeRecyclerView.setAdapter(mAdapter);
    }

    // 'CoffeeHolder' ViewHolder inner class.
    // Retrieve data from list_item_coffee.xml
    private class CoffeeHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCompleteCheckBox;
        private Coffee mCoffee;

        // Wire up the views.
        public CoffeeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_coffee_date_text_view);
            mCompleteCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_coffee_complete_check_box);
        }

        // Set up values for the wired up views.
        public void bindCoffee(Coffee coffee) {
            mCoffee = coffee;
            mTitleTextView.setText(mCoffee.getTitle());
            mDateTextView.setText(mCoffee.getDate().toString());
            mCompleteCheckBox.setChecked(mCoffee.isComplete());
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

        // onBindViewHolder
        // binds a ViewHolder's View to model object.
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
