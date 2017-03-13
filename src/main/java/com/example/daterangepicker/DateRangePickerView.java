package com.example.daterangepicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateRangePickerView extends RecyclerView {

    private Date minDate = new Date(0);
    private Date maxDate = new Date(4105116000000L);

    private final DateRangePickerDelegate delegate = new DateRangePickerDelegate();

    public DateRangePickerView(Context context) {
        this(context, null);
    }

    public DateRangePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateRangePickerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final LayoutManager lm = new LinearLayoutManager(context);
        super.setLayoutManager(lm);
        super.setAdapter(new YearAdapter());
        lm.scrollToPosition(delegate.yearToPosition(minDate, Calendar.getInstance()));

        delegate.setOnClickMonthListener(new DateRangePickerDelegate.OnClickMonthListener() {
            @Override
            public void onClickMonth(Calendar calendar) {
                getAdapter().notifyDataSetChanged();
                invalidateMonthViews();
            }
        });
    }

    public void setDate(Calendar calendar) {
        scrollToPosition(delegate.yearToPosition(minDate, calendar));
    }

    public void setRange(DateRange range) {
        getAdapter().notifyDataSetChanged();
        invalidateMonthViews();
    }

    public DateRange getRange() {
        return delegate.getRange();
    }

    public void clearRange() {
        delegate.setRange(new DateRange());
    }

    private void invalidateMonthViews() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof MonthView) {
                view.invalidate();
            }
        }
    }

    private class YearAdapter extends Adapter<YearViewHolder> {

        @Override
        public YearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_year, parent, false);
            return new YearViewHolder(view);
        }

        @Override
        public void onBindViewHolder(YearViewHolder holder, int position) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
            Calendar year = delegate.positionToYear(minDate, position);
            String formattedDate = sdf.format(year.getTime());
            holder.text.setText(formattedDate);
            holder.monthView.setYear(year);
            holder.monthView.setDelegate(delegate);
        }

        @Override
        public int getItemCount() {
            return delegate.getDifferenceYears(minDate, maxDate) + 1;
        }
    }

    static class YearViewHolder extends ViewHolder {

        private final TextView text;
        private final MonthView monthView;

        YearViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            monthView = (MonthView) itemView.findViewById(R.id.month_view);
        }
    }

}