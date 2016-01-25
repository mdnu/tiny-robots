package numd.coffeejournal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * controller layer (I of IV)
 *
 * extends the 'SingleFragmentActivity' superclass.
 *
 * Created by m on 16/01/2016.
 */

public class CoffeeActivity extends SingleFragmentActivity {

    //public static final String EXTRA_COFFEE_ID = "numd.coffeejournal.coffee_id";
    private static final String EXTRA_COFFEE_ID = "whatever";

    public static Intent newIntent(Context packageContext, UUID coffeeId) {
        // Create an explicit intent alongside extra content which passes the coffeeId.
        Intent intent = new Intent(packageContext, CoffeeActivity.class);
        // Pass in a string key and the value which the key maps to (the coffeeId).
        intent.putExtra(EXTRA_COFFEE_ID, coffeeId); // UUID coffeeId is Serializable.
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new CoffeeFragment();
        UUID coffeeId = (UUID)getIntent().getSerializableExtra(EXTRA_COFFEE_ID);
        return CoffeeFragment.newInstance(coffeeId);
    }
}
