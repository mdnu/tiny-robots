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
 * model layer (II of II)
 * Created by m on 22/01/2016.
 */

public class CoffeeBar {

    // Basically, we make Coffee objects and store them inside this class.
    // We have the following methods:

    // A constructor to create a private CoffeeBar,
    // a public getter to get the private CoffeeBar object.
    // A way to get a specific Coffee from the CoffeeBar using its UUID.
    // A way to update a specific Coffee entry inside the CoffeeBar database.
    // A helper method to package all the Coffee details into a ContentValues object.
    // A way to query the database for a specific Coffee object.
    // A way to add a Coffee object to the CoffeeBar database (using the helper).
    // A way to delete a Coffee object in the CoffeeBar database.
    // A way to get a list of Coffee objects from the database.

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static CoffeeBar sCoffeeBar;
    //private List<Coffee> mCoffees;

    // CoffeeBar "get" method.
    // Retrieves the static "sCoffeeBar" object,
    // if it exists. If not, we create one by passing
    // in a Context as input.
    public static CoffeeBar get(Context context) {
        if (sCoffeeBar == null) {
            sCoffeeBar = new CoffeeBar(context);
        }
        return sCoffeeBar;
    }


    // Private "CoffeeBar" constructor.
    // This creates our main fragment.
    // The method sets the "mContext" state by getting the
    // application's context, and creates a database by
    // passing the above mContext to CoffeeBaseHelper, to get
    // a writable database for storing the Coffee objects.
    private CoffeeBar(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CoffeeBaseHelper(mContext).getWritableDatabase();
    }

    // CoffeeBar's "getCoffee" getter.
    // Retrieves a Coffee object from the CoffeeBar database.
    // How: We input a UUID, then...
    public Coffee getCoffee(UUID id) {
        // We search for all Coffee objects in the list of Coffees.
        // (We use the CoffeeCursorWrapper helper class)
        CoffeeCursorWrapper cursor = queryCoffees(
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        // If the list entry matches the id,
        // then return this list entry Coffee object.
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

    // CoffeeBar's "updateCoffee" method.
    // For a given CoffeeBar, we input a Coffee object
    // and update its values in the CoffeeBar database.
    // How: We input a Coffee object, then...
    public void updateCoffee(Coffee coffee) {
        // We extract its UUID,
        String uuidString = coffee.getId().toString();
        // ...then get its associated ContentValues,
        ContentValues values = getContentValues(coffee);

        // then we update the "mDatabase" SQLiteDatabase of Coffee objects
        // using the associated UUID and new ContentValues.
        mDatabase.update(CoffeeTable.NAME, values,
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    // CoffeeBar's private "getContentValues" helper method.
    // Helps to package the Coffee object's details into
    // a 'ContentValues' object, for use in communicating the Coffee
    // object's properties to the database.
    private static ContentValues getContentValues (Coffee coffee) {
        ContentValues values = new ContentValues();
        // Package the input Coffee object's properties into a ContentValues 'container'
        values.put(CoffeeTable.Cols.UUID, coffee.getId().toString());
        values.put(CoffeeTable.Cols.TITLE, coffee.getTitle());
        values.put(CoffeeTable.Cols.DATE, coffee.getDate().getTime());
        values.put(CoffeeTable.Cols.COMPLETE, coffee.isComplete() ? 1 : 0);
        values.put(CoffeeTable.Cols.FRIEND, coffee.getFriend());
        // Return the 'container' containing all the Coffee object's properties.
        return values;
    }

    // CoffeeBar's private "queryCoffees" method.
    // Communicates with the 'mDatabase' database, by querying
    // the input whereClause and whereArgs into a new cursor that scans the
    // database.
    private CoffeeCursorWrapper queryCoffees(String whereClause, String[] whereArgs) {
        // Query the database using a cursor and the inputs.
        Cursor cursor = mDatabase.query(
                CoffeeTable.NAME,
                null,   // Columns - null selects all columns
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   // having
                null    // orderBy
        );

        // Returns a new cursor using the above query.
        return new CoffeeCursorWrapper(cursor);
    }

    // CoffeeBar's "addCoffee" method.
    // Takes an input Coffee object, extracts its ContentValues and
    // inserts these values into the 'mDatabase' database.
    public void addCoffee(Coffee c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CoffeeTable.NAME, null, values);
    }

    // CoffeeBar's "deleteCoffee" method.
    // Takes an input Coffee object, gets its UUID, and deletes
    // its associated 'mDatabase' entry.
    public void deleteCoffee(Coffee c) {
        String uuidString = c.getId().toString();
        mDatabase.delete(CoffeeTable.NAME,
                CoffeeTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    // CoffeeBar's "getCoffees" method.
    // Creates an empty List of Coffee objects and uses
    // a cursor to scan the database and populate the List
    // as the cursor moves along the database entries.
    public List<Coffee> getCoffees() {
        // Creates empty Coffee List.
        List<Coffee> coffees = new ArrayList<>();

        // Creates a cursor to scan the database.
        CoffeeCursorWrapper cursor = queryCoffees(null, null);

        // Scan the database.
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // If the cursor position is between the first and last
                // position, we add the database entry retrieved by the
                // cursor into the List.
                coffees.add(cursor.getCoffee());
                // We continue.
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        // Return the List of Coffees extracted.
        return coffees;
    }
}
