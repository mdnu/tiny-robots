package numd.coffeehouse;

/**
 * Created by m on 06/01/2016.
 */

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;

public class CoffeeCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listCoffee = getListView();
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);
        listCoffee.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView,View itemView, int position, long id) {
        Intent intent = new Intent(CoffeeCategoryActivity.this, CoffeeActivity.class);
        intent.putExtra(CoffeeActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}
