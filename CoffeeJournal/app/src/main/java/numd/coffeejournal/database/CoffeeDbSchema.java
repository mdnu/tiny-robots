package numd.coffeejournal.database;

/**
 * database schema
 * Created by m on 30/01/2016.
 */

public class CoffeeDbSchema {
    public static final class CoffeeTable {
        public static final String NAME = "coffees";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COMPLETE = "complete";
            public static final String FRIEND = "friend";
            public static final String CONTACT_ID = "contact_id";

            // Example column location:
            // CoffeeTable.Cols.TITLE for "title" column.
        }
    }
}
