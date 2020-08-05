package com.fallgod.springbud.data.repository;

import android.text.TextUtils;

import com.fallgod.springbud.App;
import com.fallgod.springbud.Constants;
import com.fallgod.springbud.data.AppDatabase;
import com.fallgod.springbud.data.bean.CalendarScheme;
import com.fallgod.springbud.network.HttpRequest;
import com.fallgod.springbud.util.FileUtil;
import com.fallgod.springbud.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author: JackPan
 * Date: 2020-07-20
 * Time: 16:29
 * Description:
 */
public class CalendarRepository {
    private static final String TAG = "CalendarRepository";

    public void saveData(List<CalendarScheme> list) {
        LogUtil.d(TAG, "save data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().insertAll(list);
            }
        }.start();
    }

    public void saveData(CalendarScheme... calendarScheme) {
        LogUtil.d(TAG, "save data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().insertAll(calendarScheme);
            }
        }.start();
    }

    public void update(CalendarScheme... calendarScheme) {
        LogUtil.d(TAG, "update data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().update(calendarScheme);
            }
        }.start();
    }

    public void delete(CalendarScheme... calendarScheme) {
        LogUtil.d(TAG, "delete data");
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getInstance().calendarSchemeDao().delete(calendarScheme);
            }
        }.start();
    }

    public void getRemoteData(MutableLiveData<Map<String, Calendar>> liveData){
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.calenderRequest(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(TAG,"CalenderRequest onFailure!");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body == null){
                    LogUtil.e(TAG,"CalenderRequest response is null!");
                }else {
                    String json = body.string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String version = jsonObject.getString("version");
                        String data = jsonObject.getString("data");
                        // TODO: 2020/8/5 使用Gson解析ObjectList（data）
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public List<CalendarScheme> getSchemeDataList() {
        List<CalendarScheme> list = AppDatabase.getInstance().calendarSchemeDao().getAll();
        LogUtil.d(TAG, "list size:" + list.size());
        return list;
    }

    public Map<String, Calendar> getSchemeData() {
        Map<String, Calendar> map = new HashMap<>();
        List<CalendarScheme> list = getSchemeDataList();
        for (CalendarScheme scheme : list) {
            map.put(getSchemeCalendar(scheme).toString(), getSchemeCalendar(scheme));
        }
        return map;
    }

    public Map<String, Calendar> getSchemeDataFromFile() {
        Map<String, Calendar> map = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        //泛型对象解析
        String listJson = getHistoryFromFile();
        if (TextUtils.isEmpty(listJson)) {
            LogUtil.e(TAG, "没有获取到有效内容");
            return null;
        }
        List<CalendarScheme> list = gson.fromJson(listJson, new TypeToken<List<CalendarScheme>>() {}.getType());
        saveData(list);
        for (CalendarScheme scheme : list) {
            map.put(getSchemeCalendar(scheme).toString(), getSchemeCalendar(scheme));
        }
        return map;
    }

    //将历史考勤数据保存成json
    public void saveHistoryToJson() {
        new Thread() {
            @Override
            public void run() {
                List<CalendarScheme> list = getSchemeDataList();

//                List<CalendarScheme> list = new ArrayList<>();
//                for (int i = 20200604; i < 20200804; ) {
//                    CalendarScheme data = new CalendarScheme();
//                    data.cId = i;
//                    data.year = 2020;
//                    data.month = Integer.parseInt(String.valueOf(i).substring(4, 6));
//                    data.day = Integer.parseInt(String.valueOf(i).substring(6));
//                    data.text = "卡";
//                    data.color = -14983648;
//                    list.add(data);
//                    if (data.day == 31) {
//                        i += 70;
//                    } else {
//                        i++;
//                    }
//                }

                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(list);
                FileUtil.stringToExternalFile(json, Constants.ATTENDANCE_JSON_NAME, App.getInstance());
            }
        }.start();
    }

    // 从json中读取历史考勤数据
    private String getHistoryFromFile() {
        return FileUtil.getStringFromExternalFile(Constants.ATTENDANCE_JSON_NAME, App.getInstance());
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
