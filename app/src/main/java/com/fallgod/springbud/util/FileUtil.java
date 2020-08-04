package com.fallgod.springbud.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: JackPan
 * Date: 2020-08-04
 * Time: 09:53
 * Description:
 * 1.保存到内部存储
 * 2.保存到外部存储
 * 3.保存到外部指定文件夹"fallgod"
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

//    public static final String FILE_PATH = "";
//    public static final String EXTERNAL_PATH = "";
    public static final String DIR_PATH = "";

    /**
     * 保存string到内置存储
     * @param str
     * @param name
     * @param context
     */
    public static void stringToFile(String str,String name, Context context){
        FileOutputStream outputStream;
        try {
            //创建获取内置存储目录data/data/"packagename"/file
//            File filesDir = context.getFilesDir();
            LogUtil.d(TAG,"string to file::\n"+str);
            outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从内置存储读取数据
     * @param name
     * @param context
     * @return
     */
    public static String getStringFromFile(String name, Context context){
        FileInputStream inputStream;
        StringBuilder str = new StringBuilder();
        try {
            inputStream = context.openFileInput(name);

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1){
                str.append(new String(buf,0,len));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d(TAG,"file from string::\n"+str);
        return str.toString();
    }

    /**
     * 保存文件到外置私有目录
     * @param str
     * @param name
     * @param context
     */
    public static void stringToExternalFile(String str, String name, Context context){
        if (isExternalStorageWritable()){
            File dir = new File(context.getExternalFilesDir(null),"calendar");
            if (!dir.exists()){
                if (!dir.mkdir()){
                    LogUtil.e(TAG,"Directory not created");
                    return;
                }
            }
            try {
                File file = new File(dir, name);
                if (file.exists()){
                    file.delete();
                }
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(str.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从外置私有目录读取文件
     * @param name
     * @param context
     * @return
     */
    public static String getStringFromExternalFile(String name, Context context){
        if (isExternalStorageReadable()){
            File dir = new File(context.getExternalFilesDir(null),"calendar");
            if (!dir.exists()){
                if (!dir.mkdir()){
                    LogUtil.e(TAG,"Directory not created");
                    return "";
                }
            }
            FileInputStream inputStream;
            StringBuilder str = new StringBuilder();
            try {
                File file = new File(dir,name);
                inputStream = new FileInputStream(file);

                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = inputStream.read(buf)) != -1){
                    str.append(new String(buf,0,len));
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LogUtil.d(TAG,"string from external file::\n"+str);
            return str.toString();
        }
        return "";
    }

    public static void stringToDirFile(String str){

    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        LogUtil.e(TAG,"外置存储目录不可写");
        return false;
    }

    /* Checks if external storage is available to at least read */
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        LogUtil.e(TAG,"外置存储目录不可读");
        return false;
    }


}
