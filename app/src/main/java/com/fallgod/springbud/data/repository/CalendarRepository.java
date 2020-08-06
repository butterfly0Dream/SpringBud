package com.fallgod.springbud.data.repository;

import android.text.TextUtils;

import com.fallgod.springbud.App;
import com.fallgod.springbud.Constants;
import com.fallgod.springbud.data.AppDatabase;
import com.fallgod.springbud.data.bean.CalendarScheme;
import com.fallgod.springbud.data.sp.SharedPrefHelper;
import com.fallgod.springbud.network.HttpRequest;
import com.fallgod.springbud.util.FileUtil;
import com.fallgod.springbud.util.JsonUtil;
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

    /**
     * 更新数据
     * 1.检查本地考勤文件sha和网络端sha，不一致则更新
     * 2.一致则读取本地json文件更新
     */
    public void refreshData(MutableLiveData<Map<String, Calendar>> liveData){
        getRemoteData(liveData);
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.calenderFileInfo(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(TAG,"calenderFileInfo onFailure!");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body == null){
                    LogUtil.e(TAG,"calenderFileInfo response is null!");
                }else {
                    String sha = JsonUtil.getString(body.string(),"sha");
                    LogUtil.d(TAG,"remote sha is: "+sha);
                    SharedPrefHelper.getInstance(App.getInstance()).setAttendanceSha(sha);
                }
            }
        });
    }

    private void getRemoteData(MutableLiveData<Map<String, Calendar>> liveData){
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
                        int version = jsonObject.getInt("version");
                        int s = SharedPrefHelper.getInstance(App.getInstance()).getAttendanceVersion();
                        if (s > version){
                            liveData.postValue(getSchemeData());//展示数据库的缓存文件
                            List<CalendarScheme> list = getSchemeDataList();
                            Gson gson = new GsonBuilder().create();
                            String dataList = gson.toJson(list);
                            String content = JsonUtil.addKey("version",s,dataList,"data");
                            String sha = SharedPrefHelper.getInstance(App.getInstance()).getAttendanceSha();
                            String message = "更新至版本" + s;
                            pushData(message, content, sha);
                        }else if (s == version){
                            liveData.postValue(getSchemeData());//展示数据库的缓存文件
                        }else {
                            String data = jsonObject.getString("data");
                            SharedPrefHelper.getInstance(App.getInstance()).setAttendanceVersion(version);
                            liveData.postValue(getSchemeMapByJson(data,true));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void pushData(String message, String content, String sha){
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.calenderUpdate(message, content, sha, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(TAG,"calenderUpdate onFailure!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body == null){
                    LogUtil.e(TAG,"calenderUpdate response is null!");
                }else {
                    LogUtil.d(TAG, "calenderUpdate onResponse: "+body.string());
                }
            }
        });
    }

    public List<CalendarScheme> getSchemeDataList() {
        List<CalendarScheme> list = AppDatabase.getInstance().calendarSchemeDao().getAll();
        LogUtil.d(TAG, "list size:" + list.size());
        return list;
    }

    private Map<String, Calendar> getSchemeData() {
        Map<String, Calendar> map = new HashMap<>();
        List<CalendarScheme> list = getSchemeDataList();
        for (CalendarScheme scheme : list) {
            map.put(getSchemeCalendar(scheme).toString(), getSchemeCalendar(scheme));
        }
        return map;
    }

    public Map<String, Calendar> getSchemeDataFromFile() {
        String listJson = getHistoryFromFile();
        if (TextUtils.isEmpty(listJson)) {
            LogUtil.e(TAG, "没有获取到有效内容");
            return null;
        }
        return getSchemeMapByJson(listJson,true);
    }

    //将历史考勤数据保存成json
    public void saveHistoryToJson() {
        new Thread() {
            @Override
            public void run() {
                List<CalendarScheme> list = getSchemeDataList();
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(list);
                FileUtil.stringToExternalFile(json, Constants.ATTENDANCE_JSON_NAME, App.getInstance());
            }
        }.start();
    }

    private Map<String, Calendar> getSchemeMapByJson(String json, Boolean isSaveDb){
        if (TextUtils.isEmpty(json)) {
            LogUtil.e(TAG, "没有获取到有效内容");
            return null;
        }
        Map<String, Calendar> map = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        //泛型对象解析
        List<CalendarScheme> list = gson.fromJson(json, new TypeToken<List<CalendarScheme>>() {}.getType());
        if (isSaveDb){
            saveData(list);
        }
        for (CalendarScheme scheme : list) {
            map.put(getSchemeCalendar(scheme).toString(), getSchemeCalendar(scheme));
        }
        return map;
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
