package com.example.daterangepicker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class MonthView extends RecyclerView {

    private final GestureDetector gestureDetector;
    private DateRangePickerDelegate delegate;
    private Calendar year;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final GridLayoutManager lm = new GridLayoutManager(getContext(), 3, VERTICAL, false);
        super.setLayoutManager(lm);
        super.addItemDecoration(new DividerItemDecoration(context, VERTICAL));
        super.addItemDecoration(new DividerItemDecoration(context, HORIZONTAL));

        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void setDelegate(DateRangePickerDelegate delegate) {
        this.delegate = delegate;
    }

    public void setYear(Calendar year) {
        this.year = year;
        super.setAdapter(new MonthAdapter());
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View view = findChildViewUnder(e.getX(), e.getY());
            int position = getChildAdapterPosition(view);
            Calendar month = delegate.positionToMonth(year, position);
            delegate.setMinOrMax(month);
            delegate.onClickMonth(month);
            return super.onSingleTapUp(e);
        }
    }

    private class MonthAdapter extends Adapter<MonthViewHolder> {

        @Override
        public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_month, parent, false);
            return new MonthViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MonthViewHolder holder, int position) {
            String[] months = getContext().getResources().getStringArray(R.array.months);
            holder.text.setText(months[position]);
            Calendar month = delegate.positionToMonth(year, position);
            if (delegate.isInRange(month)) {
                holder.itemView.setBackgroundColor(Color.LTGRAY);
            }
        }

        @Override
        public int getItemCount() {
            return year.getActualMaximum(Calendar.MONTH) + 1;
        }
    }

    static class MonthViewHolder extends ViewHolder {

        private final TextView text;

        MonthViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

}