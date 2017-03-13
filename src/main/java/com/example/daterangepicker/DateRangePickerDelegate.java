package com.example.daterangepicker;

import java.util.Calendar;
import java.util.Date;

public class DateRangePickerDelegate {

    private DateRange range = new DateRange();

    private OnClickMonthListener onClickMonthListener;

    public void setRange(DateRange range) {
        range.getFrom().add(Calendar.MONTH, -1);
        this.range = range;
    }

    public DateRange getRange() {
        return range;
    }

    public boolean isInRange(Calendar month) {
        return !month.before(range.getFrom()) && !month.after(range.getTo());
    }

    private boolean isSameMonth(Calendar one, Calendar two) {
        return one.get(Calendar.YEAR) == two.get(Calendar.YEAR)
                && one.get(Calendar.MONTH) == two.get(Calendar.MONTH);
    }

    public void setMinOrMax(Calendar month) {
        if (isSameMonth(month, range.getFrom())) {
            range.setTo(month);
        } else if (isSameMonth(month, range.getTo())) {
            range.setFrom(month);
        } else {
            long fromDifference = Math.abs(month.getTimeInMillis() - range.getFrom().getTimeInMillis());
            long toDifference = Math.abs(month.getTimeInMillis() - range.getTo().getTimeInMillis());
            if (fromDifference < toDifference) {
                range.setFrom(month);
            } else {
                range.setTo(month);
            }
        }
    }

    public int getDifferenceYears(Date minDate, Date maxDate) {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTime(minDate);
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTime(maxDate);
        return maxCalendar.get(Calendar.YEAR) - minCalendar.get(Calendar.YEAR);
    }

    public int yearToPosition(Date minDate, Calendar calendar) {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTime(minDate);
        return calendar.get(Calendar.YEAR) - minCalendar.get(Calendar.YEAR);
    }

    public Calendar positionToYear(Date minDate, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);
        calendar.add(Calendar.YEAR, position);
        return calendar;
    }

    public Calendar positionToMonth(Calendar calendar, int position) {
        calendar.set(Calendar.MONTH, position);
        return calendar;
    }

    public void setOnClickMonthListener(OnClickMonthListener onClickMonthListener) {
        this.onClickMonthListener = onClickMonthListener;
    }

    void onClickMonth(Calendar calendar) {
        onClickMonthListener.onClickMonth(calendar);
    }

    public interface OnClickMonthListener {
        void onClickMonth(Calendar calendar);
    }

}