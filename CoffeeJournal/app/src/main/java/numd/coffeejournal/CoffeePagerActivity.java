package numd.coffeejournal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.List;
import java.util.UUID;

/**
 *
 * controller layer (I of IV)
 *
 * Created by m on 28/01/2016.
 */

public class CoffeePagerActivity extends FragmentActivity {

    private static final String EXTRA_COFFEE_ID = "whatever";
    private ViewPager mViewPager;
    private List<Coffee> mCoffees;

    // Taken from CoffeeActivity
    public static Intent newIntent(Context packageContext, UUID coffeeId) {
        // Create an explicit intent alongside extra content which passes the coffeeId.
        Intent intent = new Intent(packageContext, CoffeePagerActivity.class);
        // Pass in a string key and the value which the key maps to (the coffeeId).
        intent.putExtra(EXTRA_COFFEE_ID, coffeeId); // UUID coffeeId is Serializable.
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // inflate view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_pager);

        UUID coffeeId = (UUID)getIntent().getSerializableExtra(EXTRA_COFFEE_ID);

        // Set up the pager adapter.
        // Uses FragmentStatePagerAdapter.
        mViewPager = (ViewPager)findViewById(R.id.activity_coffee_pager_view_pager);

        mCoffees = CoffeeBar.get(this).getCoffees();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            // Call position in array of coffees,
            // returns a CoffeeFragment configured to display coffee at given int position.

            @Override
            public Fragment getItem(int position) {
                Coffee coffee = mCoffees.get(position);
                return CoffeeFragment.newInstance(coffee.getId());
            }

            @Override
            public int getCount() {
                return mCoffees.size();
            }
        });

        // Loop through coffee ID's until we find the instance whose mId matches
        // the coffeeId in the intent extra. Once found, we set the ViewPager to
        // display that item.
        for (int i = 0; i < mCoffees.size(); i++) {
            if (mCoffees.get(i).getId().equals(coffeeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
