package com.example.daterangepicker;

import java.util.Calendar;

public class DateRange {

    private Calendar from;
    private Calendar to;

    public DateRange() {
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
    }

    public DateRange(Calendar from, Calendar to) {
        this.from = from;
        this.to = to;
    }

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }

}