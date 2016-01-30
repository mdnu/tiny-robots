package numd.coffeejournal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by m on 29/01/2016.
 */

public class TimePickerFragment extends DialogPickerFragment {

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = getArgs(date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initLayout() {
        // inflate time view and wire it.
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = (TimePicker)v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(false);
        mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        return v;
    }

    @Override
    protected Date getDate() {
        // Get date from calendar.
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Pull time from time picker.
        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();
        return new GregorianCalendar(year, month, day, hour, minute).getTime();
    }
}
