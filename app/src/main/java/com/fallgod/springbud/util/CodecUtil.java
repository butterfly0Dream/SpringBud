package com.fallgod.springbud.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Author: JackPan
 * Date: 2020-08-06
 * Time: 11:49
 * Description:
 */
public class CodecUtil {

    public static String enByBase64(String str){
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String deBase64(String str){
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), Base64.NO_WRAP));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
