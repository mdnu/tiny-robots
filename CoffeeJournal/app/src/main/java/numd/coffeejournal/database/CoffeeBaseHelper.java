package numd.coffeejournal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Import lets us refer to String constants in CoffeeDbSchema.CoffeeTable
// by typing "CoffeeTable.Cols.UUID" rather than the entire "CoffeeDbSchema.CoffeeTable.Cols.UUID"

import numd.coffeejournal.Coffee;
import numd.coffeejournal.database.CoffeeDbSchema.CoffeeTable;

/**
 *
 * SQLiteOpenHelper extension
 *
 * helps to open/create an SQLiteDatabase.
 *
 * Created by m on 30/01/2016.
 */

public class CoffeeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "coffeeBase.db";

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
                CoffeeTable.Cols.FRIEND + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
