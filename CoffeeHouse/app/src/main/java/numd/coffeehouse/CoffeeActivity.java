package numd.coffeehouse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeActivity extends Activity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        // Get the coffee from the intent
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        Drink drink = Drink.drinks[drinkNo];

        // Populate the coffee image
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());

        // Populate the coffee name
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        // Populate the coffee description
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}
