package com.fallgod.springbud;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 16:48
 * Description:
 * 借助 Application 来管理一个应用级 的 SharedViewModel
 * 实现全应用范围内的 生命周期安全 且 事件源可追溯的 视图控制器 事件通知
 */
public class App extends Application  implements ViewModelStoreOwner {

    private static App mInstance;

    private ViewModelStore mAppViewModelStore;
    private ViewModelProvider.Factory mFactory;

    public static App getInstance(){
        if (mInstance == null){
            synchronized (App.class){
                if (mInstance == null){
                    mInstance = new App();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppViewModelStore = new ViewModelStore();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    public ViewModelProvider getAppViewModelProvider(Activity activity){
        return new ViewModelProvider((App)activity.getApplicationContext(),
                ((App)activity.getApplicationContext()).getAppFactory(activity));
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return mFactory;
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }
}
