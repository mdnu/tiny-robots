package numd.coffeejournal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * abstract class.
 * acts as superclass to CoffeeActivity.
 *
 * Created by m on 22/01/2016.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    // Add TAG constant.
    private static final String TAG = "CoffeePagerActivity";

    // Start logging methods.
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    // End logging methods.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate activity view.
        setContentView(R.layout.activity_fragment);

        // Add a fragment manager.
        FragmentManager fm = getSupportFragmentManager();
        // Give a fragment to manage: retrieves CoffeeFragment from the FragmentManager
        // by using its container view ID in the FrameLayout of content_coffee.xml.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // If there is no fragment with the above ID, we create a new CoffeeFragment
        // and add it to the FragmentManager.
        if (fragment == null) {
            fragment = createFragment();
            // Creates and commits a fragment transaction, used to add/remove/attach/detach/replace
            // fragments in the fragment list. Below, we use a fluent interface to create a
            // new fragment transaction, include one add operation on it, and then commit it.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            // the add(...) method takes in the view ID of our fragment container (defined in
            // content_coffee.xml) and adds it to the newly made fragment from line 36.
        }
    }
}
