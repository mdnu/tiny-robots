package numd.coffeejournal;

import android.support.v4.app.Fragment;

/**
 *
 * controller layer (III of IV)
 *
 * The launcher activity (first thing launched).
 * HOSTS the CoffeeListFragment.
 * Extends SingleFragmentActivity, so inherits its onCreate method,
 * which inflates the activity view and creates a fragment manager for it.
 *
 * Created by m on 22/01/2016.
 */

public class CoffeeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // for below, CoffeeListFragment should extend Fragment.
        return new CoffeeListFragment();
    }
}
