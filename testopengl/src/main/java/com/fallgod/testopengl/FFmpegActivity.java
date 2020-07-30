package com.fallgod.testopengl;

import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.fallgod.testopengl.util.LogUtil;

import androidx.appcompat.app.AppCompatActivity;

public class FFmpegActivity extends AppCompatActivity {
    private static final String TAG = "FFmpegActivity";

    private TextView mTv;
    private SurfaceView mSfv;

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
    private int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg);

        mTv = findViewById(R.id.tv);
        mSfv = findViewById(R.id.sfv);

        mTv.setText(ffmpegInfo());
        mTv.setOnClickListener((View view)->{
            if (player != 0){
                LogUtil.d(TAG,"getState(player):"+getState(player));
            }
        });

        initSfv();
    }

    private void initSfv(){
        mSfv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (player == 0){
                    player = createPlayer(path,holder.getSurface());
                    play(player);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    //----------------------------JNI相关接口--------------------------------------------

    public native String ffmpegInfo();

    public native int createPlayer(String path, Surface surface);
    public native void play(int player);
    public native void pause(int player);
    public native String getState(int player);

    static {
        System.loadLibrary("native-lib");
    }
}