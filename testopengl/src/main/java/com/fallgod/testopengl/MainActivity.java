package com.fallgod.testopengl;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.fallgod.testopengl.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG,"onRestart");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.d(TAG,"onSaveInstanceState");
    }
}
