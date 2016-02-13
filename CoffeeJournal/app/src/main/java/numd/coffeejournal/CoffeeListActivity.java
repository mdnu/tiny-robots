package numd.coffeejournal;

import android.support.v4.app.Fragment;

/**
 * controller layer (III of IV)
 * Created by m on 22/01/2016.
 */

public class CoffeeListActivity extends SingleFragmentActivity {

    // "CoffeeListActivity" control class.
    // This is the launcher activity, i.e. the first thing launched when
    // we start the app. It hosts the CoffeeListFragment. i.e. Activity hosting a Fragment.
    // The class extends SingleFragmentActivity, so it inherits an onCreate method,
    // which inflates the activity View and creates a FragmentManager for CoffeeListFragment.

    @Override
    protected Fragment createFragment() {
        // for below, CoffeeListFragment should extend Fragment.
        return new CoffeeListFragment();
    }
}
