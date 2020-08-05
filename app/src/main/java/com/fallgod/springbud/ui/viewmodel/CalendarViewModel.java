package com.fallgod.springbud.ui.viewmodel;

import android.util.Log;

import com.fallgod.springbud.App;
import com.fallgod.springbud.Constants;
import com.fallgod.springbud.R;
import com.fallgod.springbud.data.bean.CalendarScheme;
import com.fallgod.springbud.data.repository.CalendarRepository;
import com.haibin.calendarview.Calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author: JackPan
 * Date: 2020-07-17
 * Time: 11:59
 * Description:
 */
public class CalendarViewModel extends ViewModel {
    private static final String TAG = "CalendarViewModel";

    private CalendarRepository repository = new CalendarRepository();

    private MutableLiveData<String> historyData = new MutableLiveData<>();

    //当前日期
    public final MutableLiveData<String> textMonthDay = new MutableLiveData<>();
    //当前年份
    public final MutableLiveData<Integer> textYear = new MutableLiveData<>();
    //当前月份
    public final MutableLiveData<String> textMonth = new MutableLiveData<>();
    //当前day日期
    public final MutableLiveData<String> textDay = new MutableLiveData<>();
    //当前阴历日期
    public final MutableLiveData<String> textLunar = new MutableLiveData<>();
    //是否展示阴历日期和年份
    public final MutableLiveData<Boolean> showLunar = new MutableLiveData<>();
    //日历scheme数据
    public final MutableLiveData<Map<String, Calendar>> schemeData = new MutableLiveData<>();

    {
        showLunar.setValue(true);
    }

    public void refreshSchemeData(){
        new Thread() {
            @Override
            public void run() {
                schemeData.postValue(repository.getSchemeData());
            }
        }.start();
        repository.getRemoteData(schemeData);
    }

    public void saveData(){
        List<CalendarScheme> list = new ArrayList<>();
        list.add(getScheme(20200701,2020,7,1,0xFF40db25,"假"));
        list.add(getScheme(20200703,2020,7,3,0xFFe69138,"事"));
        list.add(getScheme(20200705,2020,7,5,0xFFdf1356,"休"));
        repository.saveData(list);
    }

    //保存json
    public void saveJson(){
        repository.saveHistoryToJson();
    }

    //读取json
    public void fromJson(){
        new Thread() {
            @Override
            public void run() {
                schemeData.postValue(repository.getSchemeDataFromFile());
            }
        }.start();
    }

    /**
     * 按照考勤事件类型更新并持久化数据
     * @param action
     */
    public void saveData(int action){
        CalendarScheme calendarScheme = null;
        switch (action){
            case Constants.ATTENDANCE_ON_WORK:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_green_100),"上");
                break;
            case Constants.ATTENDANCE_OFF_WORK:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_green_300),"下");
                break;
            case Constants.ATTENDANCE_WORK:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_green_900),"卡");
                break;
            case Constants.ATTENDANCE_HOLIDAY:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_blue_grey_500),"休");
                break;
            case Constants.ATTENDANCE_LEAVE:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_deep_orange_500),"假");
                break;
            case Constants.ATTENDANCE_SOMETHING_LEAVE:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_red_100),"事");
                break;
            case Constants.ATTENDANCE_SICK_LEAVE:
                calendarScheme = getSchemeToday(
                        ContextCompat.getColor(App.getInstance(), R.color.md_red_300),"病");
                break;
        }
        Log.d(TAG, "calendarScheme: ");
        Map<String, Calendar> map = schemeData.getValue();
        if (map.containsKey(getSchemeCalendar(calendarScheme).toString()) &&
                map.get(getSchemeCalendar(calendarScheme).toString()).getScheme().equals(calendarScheme.text)){
            //重复的数据，放弃保存
            return;
        }
        if (!map.containsKey(getSchemeCalendar(calendarScheme).toString())){//插入新值
            Log.d(TAG, "calendarScheme: update" + calendarScheme);
            //更新缓存数据
            map.put(getSchemeCalendar(calendarScheme).toString(),getSchemeCalendar(calendarScheme));
            schemeData.postValue(map);

            //存储到数据库
            repository.saveData(calendarScheme);
        }
        else {
            if (map.get(getSchemeCalendar(calendarScheme).toString()).getScheme().equals("上") && calendarScheme.text.equals("下")
                    || map.get(getSchemeCalendar(calendarScheme).toString()).getScheme().equals("下") && calendarScheme.text.equals("上")) {
                //更新缓存数据
                calendarScheme.text = "卡";
                calendarScheme.color = ContextCompat.getColor(App.getInstance(), R.color.md_green_900);
            }
            map.put(getSchemeCalendar(calendarScheme).toString(),getSchemeCalendar(calendarScheme));
            schemeData.postValue(map);
            //更新到数据库
            repository.update(calendarScheme);
        }
    }

    private CalendarScheme getSchemeToday(int color,String text){
        String year = textYear.getValue().toString();
        String month = textMonth.getValue();
        if (month.length() == 1){
            month = "0" + month;
        }
        String day = textDay.getValue();
        if (day.length() == 1){
            day = "0" + day;
        }
        int cId = Integer.parseInt(year + month + day);
        int m = Integer.parseInt(textMonth.getValue());
        int d = Integer.parseInt(textDay.getValue());
        return getScheme(cId, textYear.getValue(), m, d, color, text);
    }

    private CalendarScheme getScheme(int cId, int year, int month, int day, int color, String text){
        CalendarScheme calendarScheme = new CalendarScheme();
        calendarScheme.cId = cId;
        calendarScheme.year = year;
        calendarScheme.month = month;
        calendarScheme.day = day;
        calendarScheme.color = color;
        calendarScheme.text = text;
        return calendarScheme;
    }

    private Calendar getSchemeCalendar(CalendarScheme data) {
        Calendar calendar = new Calendar();
        calendar.setYear(data.year);
        calendar.setMonth(data.month);
        calendar.setDay(data.day);
        calendar.setSchemeColor(data.color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(data.text);
//        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

}
