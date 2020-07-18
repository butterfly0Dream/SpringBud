package com.fallgod.springbud.ui.base;

import android.widget.CalendarView;

import androidx.databinding.BindingAdapter;

/**
 * Author: JackPan
 * Date: 2020-07-17
 * Time: 10:34
 * Description:
 */
public class CalendarBindingAdapter {

    @BindingAdapter(value = "setDateChangeListener",requireAll = false)
    public static void addDateState(CalendarView calendarView,CalendarView.OnDateChangeListener listener){
        calendarView.setOnDateChangeListener(listener);
    }
}
