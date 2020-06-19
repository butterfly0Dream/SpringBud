package com.fallgod.springbud.ui;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.fallgod.springbud.App;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 17:57
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewModelProvider mActivityProvider;

    protected abstract void initViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initViewModel();
    }

    /**
     * 获取ViewModel实例
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends ViewModel>T getViewModel(Class<T> modelClass){
        if (mActivityProvider == null){
            mActivityProvider = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()));
        }
        return mActivityProvider.get(modelClass);
    }
}
