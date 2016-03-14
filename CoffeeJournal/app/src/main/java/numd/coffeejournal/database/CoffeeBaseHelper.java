package numd.coffeejournal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Import lets us refer to String constants in CoffeeDbSchema.CoffeeTable
// by typing "CoffeeTable.Cols.UUID" rather than the entire "CoffeeDbSchema.CoffeeTable.Cols.UUID"

import numd.coffeejournal.Coffee;
import numd.coffeejournal.database.CoffeeDbSchema.CoffeeTable;

/**
 * SQLiteOpenHelper extension
 * Created by m on 30/01/2016.
 */

public class CoffeeBaseHelper extends SQLiteOpenHelper {

    /**
     * This class is called by the CoffeeBar constructor
     * in order to make a database of Coffee objects.
     * It extends SQLiteOpenHelper, so the CoffeeBar constructor
     * passes into CoffeeBaseHelper's constructor the application's
     * Context, and uses the inherited 'getWriteableDatabase' method
     * to create a database.
     */

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "coffeeBase.db";

    // "CoffeeBaseHelper" constructor.
    // Calls SQLiteOpenHelper to create a database, passing into
    // it the application Context.
    public CoffeeBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CoffeeTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        CoffeeTable.Cols.UUID + ", " +
                        CoffeeTable.Cols.TITLE + ", " +
                        CoffeeTable.Cols.DATE + ", " +
                        CoffeeTable.Cols.COMPLETE + "," +
                        CoffeeTable.Cols.FRIEND + "," +
                        CoffeeTable.Cols.CONTACT_ID + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
