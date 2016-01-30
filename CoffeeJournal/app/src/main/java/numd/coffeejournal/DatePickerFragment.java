package numd.coffeejournal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by m on 29/01/2016.
 */

public class DatePickerFragment extends DialogPickerFragment {

    private DatePicker mDatePicker;

    // Called by CoffeeFragment to set the date.
    public static DatePickerFragment newInstance(Date date) {
        // get args from DialogPickerFragment
        Bundle args = getArgs(date);

        DatePickerFragment fragment = new DatePickerFragment();
        // This is how the user sets the date:
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initLayout() {
        // inflate date view and wire it.
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), null);
        return v;
    }

    @Override
    protected Date getDate() {
        // Get date from date picker.
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();

        // Pull time from calendar.
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        return new GregorianCalendar(year, month, day, hour, minute).getTime();
    }
}