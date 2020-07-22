package com.fallgod.springbud.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;
import com.fallgod.springbud.ui.base.BaseActivity;
import com.fallgod.springbud.ui.base.DataBindingConfig;
import com.fallgod.springbud.ui.viewmodel.MainViewModel;
import com.fallgod.springbud.util.LogUtil;

import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private MainViewModel mMainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //处理打开、关闭drawer事件
        getShareViewModel().openOrCloseDrawer.observe(this,aBoolean ->
                mMainViewModel.openDrawer.setValue(aBoolean));
    }

    @Override
    protected void initViewModel() {
        mMainViewModel = getViewModel(MainViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main,mMainViewModel)
                .addBindingParams(BR.event,new ClickProxy())
                .addBindingParams(BR.listener,new EventHandler());
    }


    public class ClickProxy{
        public void changeText(){
            mMainViewModel.textContent.setValue("hhhh");
        }

        public void openDrawer(){
            mMainViewModel.openDrawer.setValue(true);
        }
    }

    public class EventHandler extends DrawerLayout.SimpleDrawerListener{
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            LogUtil.d(TAG,"onDrawerOpened");
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            LogUtil.d(TAG,"onDrawerClosed");
        }
    }

}
