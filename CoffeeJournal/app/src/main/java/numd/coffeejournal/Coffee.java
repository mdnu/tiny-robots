package numd.coffeejournal;

import java.util.Date;
import java.util.UUID;

/**
 * model layer (I of II)
 *
 * Creates model 'coffee' objects by getting a random ID for each one,
 * and getting/setting a name for each one.
 *
 * Created by m on 16/01/2016.
 */

public class Coffee {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mComplete;
    private String mFriend;

    // Constructor
    public Coffee() {
        this(UUID.randomUUID());
    }

    public Coffee(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    public String getFriend() {
        return mFriend;
    }

    public void setFriend(String friend) {
        mFriend = friend;
    }

    public Date getDate() {
        //DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        //String mFormatDate = dateFormat.format(mDate);
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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
