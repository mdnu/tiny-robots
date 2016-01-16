package numd.coffeejournal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * controller layer (I of II)
 *
 * Main Class.
 * Extends FragmentActivity superclass for hosting support fragments.

 * Created by m on 16/01/2016.
 */

public class CoffeeActivity extends FragmentActivity {

    // Main method. Inflates view.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate activity view.
        setContentView(R.layout.activity_coffee);

        // Add a fragment manager.
        FragmentManager fm = getSupportFragmentManager();
        // Give a fragment to manage: retrieves CoffeeFragment from the FragmentManager
        // by using its container view ID in the FrameLayout of content_coffee.xml.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // If there is no fragment with the above ID, we create a new CoffeeFragment
        // and add it to the FragmentManager.
        if (fragment == null) {
            fragment = new CoffeeFragment();
            // Creates and commits a fragment transaction, used to add/remove/attach/detach/replace
            // fragments in the fragment list. Below, we use a fluent interface to create a
            // new fragment transaction, include one add operation on it, and then commit it.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            // the add(...) method takes in the view ID of our fragment container (defined in
            // content_coffee.xml) and adds it to the newly made fragment from line 36.
        }
    }

}
