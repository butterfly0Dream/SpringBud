package com.fallgod.testopengl;

import android.content.Intent;
import android.os.Build;
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

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        //隐藏虚拟按键，并且全屏
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  //隐藏导航栏
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;   //主动显示导航栏、状态栏时，以半透明方式显示并且一段时间后自动消失
            decorView.setSystemUiVisibility(uiOptions);
        }else{ // lower api
            decorView.setSystemUiVisibility(View.GONE);
        }
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
