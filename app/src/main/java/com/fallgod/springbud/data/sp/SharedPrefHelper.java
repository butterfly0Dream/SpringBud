package com.fallgod.springbud.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: JackPan
 * Date: 2020-08-06
 * Time: 09:29
 * Description:
 */
public class SharedPrefHelper {

    private static final String SP_FILE_NAME = "app_sp";

    // 考勤历史文件的sha码
    private static final String KEY_ATTENDANCE_SHA = "attendance_sha";
    // 考勤历史文件的版本信息
    private static final String KEY_ATTENDANCE_VERSION = "attendance_version";

    private SharedPreferences mSP;
    private static SharedPrefHelper mInstance;

    private SharedPrefHelper(Context context){
        mSP = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefHelper getInstance(Context context){
        if (mInstance == null){
            synchronized (SharedPrefHelper.class){
                if (mInstance == null){
                    mInstance = new SharedPrefHelper(context);
                }
            }
        }
        return mInstance;
    }

    public void setAttendanceSha(String sha){
        mSP.edit().putString(KEY_ATTENDANCE_SHA,sha).apply();
    }

    public String getAttendanceSha(){
        return mSP.getString(KEY_ATTENDANCE_SHA,"");
    }


    public void setAttendanceVersion(int version){
        mSP.edit().putInt(KEY_ATTENDANCE_VERSION,version).apply();
    }

    public int getAttendanceVersion(){
        return mSP.getInt(KEY_ATTENDANCE_VERSION,0);
    }
}
