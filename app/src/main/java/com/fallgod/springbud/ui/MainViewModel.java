package com.fallgod.springbud.ui;

import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 16:19
 * Description:
 */
public class MainViewModel extends ViewModel {

    private Long mInitialTime;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    public MainViewModel(){
        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue);
            }
        }, 1000, 1000);
    }

    public LiveData<Long> getElapsedTime(){
        return mElapsedTime;
    }

}
