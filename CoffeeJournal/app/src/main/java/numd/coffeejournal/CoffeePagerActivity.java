package numd.coffeejournal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * controller layer (I of V)
 * main controller class.
 * Created by m on 28/01/2016.
 */

public class CoffeePagerActivity extends AppCompatActivity {

    /**
     * Hosts the CoffeeFragment activity.
     * That is, CoffeePagerActivity hosts the CoffeeFragment activity.
     * Consists of an instance of ViewPager, which lets users navigate
     * between list items via swiping across the screen forwards/backwards
     * through Coffee entries.
     * We have the following methods:
     * An 'onCreate' method which overrides the AppCompatActivity onCreate,
     * This inflates the view, sets up the pager adapter and hosts the CoffeeFragment.
     * A 'newIntent' method which creates explicit intents for communicating with other
     * control classes.
     */

    private static final String EXTRA_COFFEE_ID = "whatever";
    private ViewPager mViewPager;
    private List<Coffee> mCoffees;

    /**
     * CoffeePagerActivity's "newIntent" method.
     * This intent is meant to start CoffeePagerActivity, using some extra info.
     * Creates a new intent with extra content which passes the UUID of the Coffee object.
     * Basically, this method is called from outside classes to start the CoffeePagerActivity
     * with a specified initial Coffee UUID to start with.
     */
    public static Intent newIntent(Context packageContext, UUID coffeeId) {
        Intent intent = new Intent(packageContext, CoffeePagerActivity.class);
        // Pass in a string key and the value which the key maps to (the coffeeId).
        // Note: UUID coffeeId is serializable.
        intent.putExtra(EXTRA_COFFEE_ID, coffeeId);
        // Return the intent (meaning, start the CoffePagerActivity with the extras).
        return intent;
    }

    /**
     * CoffeePagerActivity's 'onCreate' method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the View.
        super.onCreate(savedInstanceState);
        // Set the View content to display the 'activity_coffee_pager' XML object.
        setContentView(R.layout.activity_coffee_pager);

        // Get the UUID from the intent which is associated with the Coffee name
        // given in "EXTRA_COFFEE_ID".
        UUID coffeeId = (UUID)getIntent().getSerializableExtra(EXTRA_COFFEE_ID);

        // Set up the pager adapter.
        // i.e. assign the 'mViewPager' ViewPager with the View named
        // 'activity_coffee_pager_view_pager'.
        mViewPager = (ViewPager)findViewById(R.id.activity_coffee_pager_view_pager);

        /**
         * IMPORTANT
         * Host the CoffeeFragment.
         * How: Get the List of Coffees from the CoffeeBar taken from the
         * savedInstanceState using "this" activity's Context.
         */
        mCoffees = CoffeeBar.get(this).getCoffees();
        // Create a new FragmentManager and...
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Set the View 'mViewPager' (which requires a PageAdapter to display Views)
        // to display the FragmentManager, using an anon inner class.
        // How: we set the pager adapter and implement its getCount and getItem methods.
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            // Conversation between ViewPager and PagerAdapter:
            // Using FragmentStatePageAdapter, a subclass of PagerAdapter:
            // Call getItem for a position in our Coffee List, it returns a
            // CoffeeFragment configured to display the Coffee at that position.

            // We add these returned fragments into our activity, and help the
            // ViewPager identify the fragments' views so they are placed correctly.

            @Override
            public Fragment getItem(int position) {
                // Fetch the Coffee instance for the given input position.
                Coffee coffee = mCoffees.get(position);
                // Use the UUID to create a properly configured CoffeeFragment,
                // and return it.
                return CoffeeFragment.newInstance(coffee.getId());
            }

            @Override
            public int getCount() {
                return mCoffees.size();
            }
        });

        // Loop through Coffee UUIDs until we find the instance whose UUID 'mId' matches
        // the intent extras' coffee UUID. Once found, we set the ViewPager to display that item.
        for (int i = 0; i < mCoffees.size(); i++) {
            if (mCoffees.get(i).getId().equals(coffeeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        // Basically, when other control classes use newIntent to start the CoffeePagerActivity,
        // we use the extra info of the desired UUID and use the above loop to find the associated
        // Coffee and its View and then we display that View.

    }
}
