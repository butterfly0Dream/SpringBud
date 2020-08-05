package com.fallgod.springbud.network;

import com.fallgod.springbud.util.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: JackPan
 * Date: 2020-08-05
 * Time: 11:55
 * Description:
 */
public class HttpRequest {
    private static final String TAG = "HttpRequest";


    public void calenderAdd(){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(
                "{\n  \"message\": \"commit from INSOMNIA\",\n  \"content\": \"bXkgbmV3IGZpbGUgY29udGVudHM=\"\n}",mediaType);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/butterfly0Dream/DataServer/contents/test3.txt")
                .put(body)
                .addHeader("authorization", "Basic YnV0dGVyZmx5MERyZWFtOnB4MTk5NzIyMA==")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "1d9ec6d8-8bdc-e651-257b-2224900686c3")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                LogUtil.d("response::\n"+response.body().string());
            }
        });
    }


    public void calenderRequest(Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/butterfly0Dream/DataServer/master/history_attendance.json")
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }


    public void calenderUpdate(Callback callback){
        OkHttpClient client = new OkHttpClient();

        // body内容：
        // message:commit说明
        // content:修改后的文件内容
        // sha:文件sha，每次修改都会发生变化，必须在修改前获取
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create( "{\n  \"message\": \"update from INSOMNIA\",\n  \"content\": \"Y3JlYXRlIGZpbGUgZnJvbSBJTlNPTU5JQQoKSXQncyB1cGRhdGVkISEhCgpJdCdzIHVwZGF0ZWQgYWdhaW4hIQ==\",\n  \"sha\": \"0d5a690c8fad5e605a6e8766295d9d459d65de42\"\n}",mediaType);
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/butterfly0Dream/DataServer/contents/test2.txt")
                .put(body)
                .addHeader("authorization", "Basic YnV0dGVyZmx5MERyZWFtOnB4MTk5NzIyMA==")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "fd6a9c0c-88ca-7a22-72eb-6060007fd860")
                .build();

        client.newCall(request).enqueue(callback);
    }


    public void calenderFileInfo(Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/butterfly0Dream/DataServer/contents/test2.txt")
                .build();

        client.newCall(request).enqueue(callback);
    }

}
