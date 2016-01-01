package numd.coffeerec;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

public class CoffeeActivity extends Activity {
    // Calls the BeerExpert class to create a (private) new BeerExpert object.
    // Recall that BeerExpert stores all the brand names we want.
    private CoffeeRec rec = new CoffeeRec();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
    }

    // Call when the user clicks the button
    // note: name of method is specific to the field android:onClick="onClickFindCoffee"
    public void onClickFindCoffee(View view){
        // Get a reference to the TextView:
        // i.e. gets the TextView from content_coffee.xml with ID android:id="@+id/types"
        // and assigns it to the TextView variable "types".
        TextView types = (TextView) findViewById(R.id.types);

        // Get a reference to the Spinner:
        // i.e. gets the Spinner from content_coffee.xml with ID android:id="@+id/flavor"
        // and assigns it to the Spinner variable "flavor".
        Spinner flavor = (Spinner) findViewById(R.id.flavor);

        // Get the selected item in the Spinner
        // i.e. gets the String value of the "flavor" Spinner object and assigns it to
        // a String value of coffeeType.
        String coffeeType = String.valueOf(flavor.getSelectedItem());

        // Display the selected item
        // i.e. controls our "types" TextView object, calling the setText method
        // and inserting the above String coffeeType into the android:text="@string/types" field
        // in content_coffee.xml
        types.setText(coffeeType);

        // Get recommendations from the CoffeeRec class
        // i.e. calls the getRec method in the 'rec' CoffeeRec object
        // and passes to it the coffeeType input, gets the output and assigns it to a List object
        // of Strings called 'typesList', then formats it by displaying each brand on a new line.
        List<String> typesList = rec.getRec(coffeeType); // Gets a List object of brands
        StringBuilder typesFormatted = new StringBuilder(); // Create new empty String
        for (String brand : typesList) {
            typesFormatted.append(brand).append('\n'); // Display each brand on a new line
        } // for each brand String in the String List brandsList, append it to the brandsFormatted String.

        // Display the coffee
        // i.e. calls to the types TextView object's setText method and inserts the
        // final typesFormatted string.
        types.setText(typesFormatted);
    }
}

