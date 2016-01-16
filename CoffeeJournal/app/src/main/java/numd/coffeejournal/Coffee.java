package numd.coffeejournal;

import java.util.UUID;

/**
 * model layer
 *
 * Creates model 'coffee' objects by getting a random ID for each one,
 * and getting/setting a name for each one.
 *
 * Created by m on 16/01/2016.
 */

public class Coffee {

    private UUID mId;
    private String mTitle;

    // Constructor
    public Coffee() {
        // Generate unique identifier
        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }
}
