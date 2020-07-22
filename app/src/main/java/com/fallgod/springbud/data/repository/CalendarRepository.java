package com.fallgod.springbud.data.repository;

import com.fallgod.springbud.data.AppDatabase;
import com.fallgod.springbud.data.bean.CalendarScheme;
import com.fallgod.springbud.util.LogUtil;
import com.haibin.calendarview.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: JackPan
 * Date: 2020-07-20
 * Time: 16:29
 * Description:
 */
public class CalendarRepository {
    private static final String TAG = "CalendarRepository";

    public void saveData(List<CalendarScheme> list){
        LogUtil.d(TAG,"save data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().insertAll(list);
            }
        }.start();
    }

    public void saveData(CalendarScheme... calendarScheme){
        LogUtil.d(TAG,"save data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().insertAll(calendarScheme);
            }
        }.start();
    }

    public void update(CalendarScheme... calendarScheme){
        LogUtil.d(TAG,"update data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().update(calendarScheme);
            }
        }.start();
    }

    public void delete(CalendarScheme... calendarScheme){
        LogUtil.d(TAG,"delete data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().delete(calendarScheme);
            }
        }.start();
    }

    public List<CalendarScheme> getSchemeDataList(){
        List<CalendarScheme> list = AppDatabase.getInstance().calendarSchemeDao().getAll();
        LogUtil.d(TAG,"list size:"+list.size());
        return list;
    }

    public Map<String, Calendar> getSchemeData(){
        Map<String, Calendar> map = new HashMap<>();
        List<CalendarScheme> list = AppDatabase.getInstance().calendarSchemeDao().getAll();
        for (CalendarScheme scheme:list){
            map.put(getSchemeCalendar(scheme).toString(),getSchemeCalendar(scheme));
        }
        return map;
    }

//    public Map<String, Calendar> getSchemeData(){
//        int year = 2020;
//        int month = 7;
//
//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
//
//        return map;
//    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    private Calendar getSchemeCalendar(CalendarScheme data) {
        Calendar calendar = new Calendar();
        calendar.setYear(data.year);
        calendar.setMonth(data.month);
        calendar.setDay(data.day);
        calendar.setSchemeColor(data.color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(data.text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

}
