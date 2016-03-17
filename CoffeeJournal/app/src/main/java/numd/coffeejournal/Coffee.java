package numd.coffeejournal;

import java.util.Date;
import java.util.UUID;

/**
 * model layer (I of II)
 * Created by m on 16/01/2016.
 */

public class Coffee {

    /**
     * Coffee objects have the following properties:
     * a unique ID, a title, an associated date, a value for if
     * we've completed tasting the coffee, and a chosen friend
     * which we recommend the coffee to.
     */

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mComplete;
    private String mFriend;
    private long mContactId;


    // Constructor #1
    // Creates a Coffee object with a random UUID
    public Coffee() {
        this(UUID.randomUUID());
    }

    // Constructor #2
    // Creates a Coffee object with a specified UUID.
    // Assigns Coffee object's mId as its UUID,
    // and mDate as the current date.
    public Coffee(UUID id) {
        mId = id;
        mDate = new Date();
    }

    // "Completeness" getter
    // For a given Coffee object, it will return its
    // "mComplete" state. (Either complete or not).
    public boolean isComplete() {
        return mComplete;
    }

    // "Completeness" setter
    // For a given Coffee object, it will set its
    // "mComplete" state. (Either complete or not),
    // using the boolean input.
    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    // "Friend" getter
    // For a given Coffee object, it will return
    // the friend associated with the Coffee.
    public String getFriend() {
        return mFriend;
    }

    // "Friend" setter
    // For a given Coffee object, it will set its
    // "mFriend" state, the name of the friend
    // using the String input.
    public void setFriend(String friend) {
        mFriend = friend;
    }

    // "Date" getter
    // For a given Coffee object, it will return its
    // "mDate" state.
    public Date getDate() {
        //DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        //String mFormatDate = dateFormat.format(mDate);
        return mDate;
    }

    // "Date" setter
    // For a given Coffee object, it will set its
    // "mDate" state, which is the Date associated
    // with the Coffee.
    public void setDate(Date date) {
        mDate = date;
    }

    // "Title" getter
    // For a given Coffee object, it will get its
    // "mTitle" state, which is the name of the Coffee.
    public String getTitle() {
        return mTitle;
    }

    // "Title" setter
    // For a given Coffee object, it will set its
    // "mTitle" state, which is the name of the Coffee.
    public void setTitle(String title) {
        mTitle = title;
    }

    // "UUID" getter
    // For a given Coffee object, it will get its
    // associated UUID, which is generated or passed onto
    // the object by the constructor.
    public UUID getId() {
        return mId;
    }

    public long getContactId() {
        return mContactId;
    }

    public void setContactId(long contactId) {
        mContactId = contactId;
    }

    public String getPhotoFileName() {
        return "IMG_" + getId().toString() + ".jpg";
    }

}
