package com.fallgod.springbud;

import android.app.Application;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 16:48
 * Description:
 */
public class App extends Application {

    private static App mInstance;

    public static App getInstance(){
        if (mInstance == null){
            synchronized (App.class){
                if (mInstance == null){
                    mInstance = new App();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
