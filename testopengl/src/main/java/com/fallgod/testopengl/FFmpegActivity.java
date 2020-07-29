package com.fallgod.testopengl;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FFmpegActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg);

        mTv = findViewById(R.id.tv_ffmpeg_info);
        mTv.setText(ffmpegInfo());
    }


    public native String ffmpegInfo();

    static {
        System.loadLibrary("native-lib");
    }
}