package com.fallgod.testopengl.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.fallgod.testopengl.R;
import com.fallgod.testopengl.util.LogUtil;

import androidx.appcompat.app.AppCompatActivity;

public class FFmpegActivity extends AppCompatActivity {
    private static final String TAG = "FFmpegActivity";

    private TextView mTv;
    private SurfaceView mSfv;

//    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/HDiCarCam/HDiCar_Movie/video.avi";
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

        path = getPath(this);

        initSfv();

        //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != 0){
            play(player);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != 0){
            pause(player);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != 0){
            stop(player);
            player = 0;
        }
    }



    private String getPath(Context context) {
        String path = "";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor;
        // 获取指定目录的所有文件
        cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null ,null , null, null);
        // 遍历文件信息
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
            Log.d(TAG, "getPath: "+name);
            if (name.equals("video.avi")) {
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String realPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH));
                String str = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                Log.d(TAG, "getLocalVideoBeans: name:" + name + "  id:" + id + "  uri:" + uri + "  date:" + date);
                Log.d(TAG, "getLocalVideoBeans: realPath:" + realPath +"  "+uri.getPath() + "  "+str);
//                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + realPath + name;
                path = str;
                Log.d(TAG, " final getPath: "+path);
            }
        }
        // 使用完后关闭cursor
        cursor.close();

        return path;
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

    //me

    public native String ffmpegInfo();

    public native int createPlayer(String path, Surface surface);
    public native void play(int player);
    public native void pause(int player);
    public native void stop(int player);
    public native String getState(int player);

    static {
        System.loadLibrary("native-lib");
    }
}