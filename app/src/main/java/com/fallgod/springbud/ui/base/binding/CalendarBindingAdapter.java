package com.fallgod.springbud.ui.base.binding;

import com.fallgod.springbud.util.LogUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.Map;

import androidx.databinding.BindingAdapter;

/**
 * Author: JackPan
 * Date: 2020-07-20
 * Time: 11:45
 * Description:Calendar添加各种监听
 */
public class CanlendarBingdingAdapter {

    @BindingAdapter(value = {"calendarSelectListener"}, requireAll = false)
    public static void listenCalendarSelect(CalendarView calendarView, CalendarView.OnCalendarSelectListener listener){
        calendarView.setOnCalendarSelectListener(listener);
    }

    @BindingAdapter(value = {"calendarLongClickListener"}, requireAll = false)
    public static void listenCalendarLongClick(CalendarView calendarView, CalendarView.OnCalendarLongClickListener listener){
        calendarView.setOnCalendarLongClickListener(listener);
    }

    @BindingAdapter(value = {"yearChangeListener"}, requireAll = false)
    public static void listenYearChange(CalendarView calendarView, CalendarView.OnYearChangeListener listener){
        calendarView.setOnYearChangeListener(listener);
    }

    /**
     * 通过这种方式将一个view绑定到一个liveData上后，view创建时以及每次liveData变化时，都会触发这个方法
     * @param calendarView
     * @param map
     */
    @BindingAdapter(value = {"schemeListener"}, requireAll = false)
    public static void listenSchemeData(CalendarView calendarView, Map<String, Calendar> map){
        LogUtil.d("jack","setSchemeDate");
        if (map != null && map.size() > 0){
            calendarView.setSchemeDate(map);
        }
    }

}
