package numd.coffeejournal;

import android.support.v4.app.Fragment;

/**
 * controller layer (I of IV)
 *
 * extends the 'SingleFragmentActivity' superclass.
 *
 * Created by m on 16/01/2016.
 */

public class CoffeeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CoffeeFragment();
    }
}
