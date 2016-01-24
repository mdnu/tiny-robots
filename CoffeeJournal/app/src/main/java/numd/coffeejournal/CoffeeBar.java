package numd.coffeejournal;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * model layer (II of II)
 *
 * singleton class. functions as the owner of the coffee data
 * provides a way to pass this data between controller classes.
 *
 * Created by m on 22/01/2016.
 */

public class CoffeeBar {
    private static CoffeeBar sCoffeeBar;
    private List<Coffee> mCoffees;

    // 'get' method.
    // Pass in 'Context' object.
    // If our CoffeeBar object is empty, create a new one,
    // else return the existing one.
    public static CoffeeBar get(Context context) {
        if (sCoffeeBar == null) {
            sCoffeeBar = new CoffeeBar(context);
        }
        return sCoffeeBar;
    }

    // Private constructor.
    // Other classes unable to create CoffeeBar object.
    private CoffeeBar(Context context) {
        mCoffees = new ArrayList<>();
        // Generate 100 Coffees.
        for (int i = 0; i < 11; i++) {
            Coffee coffee = new Coffee();
            coffee.setTitle("Coffee #" + i);
            coffee.setComplete(i % 2 == 0); // Take only even arguments.
            mCoffees.add(coffee);
        }
    }

    // 'getCoffee' method.
    // Input an id. Search for all coffee objects in list of coffees,
    // If list entry matches id, then return this list entry coffee object.
    public Coffee getCoffee(UUID id) {
        for (Coffee coffee: mCoffees) {
            if (coffee.getId().equals(id)) {
                return coffee;
            }
        }
        return null;
    }

    public List<Coffee> getCoffees() {
        return mCoffees;
    }
}
