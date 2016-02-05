package numd.coffeejournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import numd.coffeejournal.database.CoffeeBaseHelper;
import numd.coffeejournal.database.CoffeeCursorWrapper;
import numd.coffeejournal.database.CoffeeDbSchema.CoffeeTable;

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
    //private List<Coffee> mCoffees;
    private static CoffeeBar sCoffeeBar;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CoffeeBar get(Context context) {
        if (sCoffeeBar == null) {
            sCoffeeBar = new CoffeeBar(context);
        }
        return sCoffeeBar;
    }
    // 'get' method.
    // Pass in 'Context' object.
    // If our CoffeeBar object is empty, create a new one,
    // else return the existing one.

    // This is our main Fragment.
    private CoffeeBar(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CoffeeBaseHelper(mContext).getWritableDatabase();
        //mCoffees = new ArrayList<>();

        /**
        String coffeeTitle = "misc";
        // Generate 4 sample Coffees.

        for (int i = 0; i < 4; i++) {
            Coffee coffee = new Coffee();
            switch (i) {
                case (0) : coffeeTitle = "Tim Hortons Large Double-Double";
                    break;
                case (1) : coffeeTitle = "Starbucks Grande Mocha";
                    break;
                case (2) : coffeeTitle = "Home-Brew Kilimanjaro";
                    break;
                case (3) : coffeeTitle = "Second-Cup Sumatra Mandheling";
            }
            coffee.setTitle(coffeeTitle);
            //coffee.setComplete(i % 2 == 0); // Take only even arguments.
            mCoffees.add(coffee);
        }*/
    }
    // Private constructor.
    // Other classes unable to create CoffeeBar object.

    public Coffee getCoffee(UUID id) {
        CoffeeCursorWrapper cursor = queryCoffees(
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCoffee();
        } finally {
            cursor.close();
        }
    }
    // 'getCoffee' method.
    // Input an id. Search for all coffee objects in list of coffees,
    // If list entry matches id, then return this list entry coffee object.

    public void updateCoffee(Coffee coffee) {
        String uuidString = coffee.getId().toString();
        ContentValues values = getContentValues(coffee);

        mDatabase.update(CoffeeTable.NAME, values,
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
    // UpdateCoffee method.
    // Updates the rows in the database.

    // Package coffee details into a 'ContentValues' object.
    private static ContentValues getContentValues (Coffee coffee) {
        ContentValues values = new ContentValues();
        values.put(CoffeeTable.Cols.UUID, coffee.getId().toString());
        values.put(CoffeeTable.Cols.TITLE, coffee.getTitle());
        values.put(CoffeeTable.Cols.DATE, coffee.getDate().getTime());
        values.put(CoffeeTable.Cols.COMPLETE, coffee.isComplete() ? 1 : 0);
        values.put(CoffeeTable.Cols.FRIEND, coffee.getFriend());
        return values;
    }

    // Query for Coffees.
    private CoffeeCursorWrapper queryCoffees(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CoffeeTable.NAME,
                null,   // Columns - null selects all columns
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   // having
                null    // orderBy
        );

        return new CoffeeCursorWrapper(cursor);
    }

    public void addCoffee(Coffee c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CoffeeTable.NAME, null, values);
    }
    // Method to add a new coffee.
    // Takes coffee object and extracts its content values,
    // placing it into a ContentValues object.

    public void deleteCoffee(UUID coffeeID) {
        String uuidString = coffeeID.toString();
        mDatabase.delete(CoffeeTable.NAME,
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public List<Coffee> getCoffees() {
        List<Coffee> coffees = new ArrayList<>();

        CoffeeCursorWrapper cursor = queryCoffees(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                coffees.add(cursor.getCoffee());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return coffees;
    }
}
