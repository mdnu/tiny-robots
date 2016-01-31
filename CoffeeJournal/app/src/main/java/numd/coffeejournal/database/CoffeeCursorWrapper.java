package numd.coffeejournal.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import numd.coffeejournal.Coffee;
import numd.coffeejournal.database.CoffeeDbSchema.CoffeeTable;

/**
 * Created by m on 30/01/2016.
 */

public class CoffeeCursorWrapper extends CursorWrapper {

    public CoffeeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Coffee getCoffee() {
        String uuidString = getString(getColumnIndex(CoffeeTable.Cols.UUID));
        String title = getString(getColumnIndex(CoffeeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CoffeeTable.Cols.DATE));
        int isComplete = getInt(getColumnIndex(CoffeeTable.Cols.COMPLETE));

        Coffee coffee = new Coffee(UUID.fromString(uuidString));
        coffee.setTitle(title);
        coffee.setDate(new Date(date));
        coffee.setComplete(isComplete!= 0);

        return coffee;
    }

}
