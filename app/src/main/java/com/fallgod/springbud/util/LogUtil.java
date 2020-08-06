package com.fallgod.springbud.util;

import android.util.Log;

/**
 * Author: JackPan
 * Date: 2020-06-16
 * Time: 17:00
 * Description:
 */
public class LogUtil {
    private static final String TAG = "SpringBudLog";

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int NOTHING = 9;

    public static int level = VERBOSE;

    public static void v(String tag,String msg){
        if (level <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void v(String msg){
        v(TAG,msg);
    }

    public static void d(String tag,String msg){
        if (level <= DEBUG){
            while (msg.length() > 0){
                if (msg.length() > 1000){
                    String str = msg.substring(0,1000);
                    Log.d(tag, str);
                    msg = msg.substring(1000);
                }else {
                    Log.d(tag, msg);
                    msg = "";
                }
            }
        }
    }

    public static void d(String msg){
        d(TAG,msg);
    }

    public static void i(String tag,String msg){
        if (level <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void i(String msg){
        i(TAG,msg);
    }

    public static void w(String tag,String msg){
        if (level <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void w(String msg){
        w(TAG,msg);
    }

    public static void e(String tag,String msg){
        if (level <= ERROR){
            Log.e(tag, msg);
        }
    }

    public static void e(String msg){
        e(TAG,msg);
    }
}
