package numd.coffeejournal;

import android.support.v4.app.Fragment;

/**
 *
 * controller layer (III of IV)
 *
 * extends the 'SingleFragmentActivity' superclass.
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
