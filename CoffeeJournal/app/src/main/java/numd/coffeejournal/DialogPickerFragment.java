package numd.coffeejournal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by m on 29/01/2016.
 */

public abstract class DialogPickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "etcetcetc";
    // Variable for date argument.
    private static final String ARG_DATE = "etcetc";

    protected Calendar mCalendar;
    protected abstract View initLayout();
    protected abstract Date getDate();

    protected static Bundle getArgs(Date date) {
        Bundle args = new Bundle();
        // This is where we store the date.
        args.putSerializable(ARG_DATE, date);
        return args;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Extract the date and initialize DatePicker.
        Date date = (Date)getArguments().getSerializable(ARG_DATE);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        //int year = mCalendar.get(Calendar.YEAR);
        //int month = mCalendar.get(Calendar.MONTH);
        //int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        View v = initLayout();

        // Construct an AlertDialog instance. Set its view to the DatePicker.
        return new AlertDialog
                .Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = getDate();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
