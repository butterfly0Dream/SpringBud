package com.fallgod.testopengl.ui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.view.View;

import com.fallgod.testopengl.R;
import com.fallgod.testopengl.render.RectangleRenderer;
import com.fallgod.testopengl.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OpenGLActivity extends AppCompatActivity {
    private static final String TAG = "OpenGLActivity";

    private GLSurfaceView mGLSurfaceView;
    private RectangleRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl);
        LogUtil.d(TAG,"onCreate ");

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceview);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setEGLConfigChooser(8,8,8,8,16,0);
//        glSurfaceView.setRenderer(new TriangleRenderer());
        mRenderer = new RectangleRenderer(this);
        mGLSurfaceView.setRenderer(mRenderer);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    public void saveBitmap(View view){
        LogUtil.d(TAG,"save bitmap");
        //调用OpenGL的操作，必须要放到OpenGL的线程
//        mGLSurfaceView.queueEvent(()->mRenderer.createBitmap());
        String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gl_map.png";
        mGLSurfaceView.queueEvent(()->mRenderer.createAndroidBitmap(filename));
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
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,"onPause");
        mGLSurfaceView.onPause();
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