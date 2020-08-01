package com.fallgod.testopengl;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.fallgod.testopengl.ui.FFmpegActivity;
import com.fallgod.testopengl.ui.MainActivity2;
import com.fallgod.testopengl.ui.OpenGLActivity;
import com.fallgod.testopengl.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG,"onCreate ");
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

    //点击事件
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_opengl_aty://打开opengl页面
                startActivity(OpenGLActivity.class);
                break;
            case R.id.btn_camera2_aty://打开camera2界面
                startActivity(MainActivity2.class);
                break;
            case R.id.btn_ffmpeg_aty://打开FFmpeg界面
                startActivity(FFmpegActivity.class);
                break;
        }
    }

    private void startActivity(Class c ){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
}
