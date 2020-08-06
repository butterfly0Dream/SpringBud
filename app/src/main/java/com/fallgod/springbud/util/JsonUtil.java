package com.fallgod.springbud.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: JackPan
 * Date: 2020-08-06
 * Time: 10:57
 * Description:
 */
public class JsonUtil {
    private static final String TAG = "JsonUtil";

    public static String getString(String json, String key){
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getInt(String json, String key){
        int value = -1;
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String addKey(String key, Object value, String rawStr, String arrayKey){
        StringBuilder result = new StringBuilder();
        result.append("\"");
        result.append(key);
        result.append("\":");
        if (value instanceof String){
            result.append("\"");
            result.append(value);
            result.append("\",");
        }else {
            result.append(value);
        }

        if (TextUtils.isEmpty(arrayKey)){//认为rawStr是jsonObject
            return "{" + result.toString() + rawStr.substring(1);
        }else {//认为rawStr是jsonArray,arrayKey作为它的key
            result.append("\"");
            result.append(arrayKey);
            result.append("\":");
            return "{" + result.toString() + rawStr + "}";
        }
    }

}
