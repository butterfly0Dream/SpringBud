package com.fallgod.springbud.ui.base;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fallgod.springbud.App;
import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;
import com.fallgod.springbud.ui.viewmodel.ShareViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 17:57
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewModelProvider mActivityProvider;
    private ShareViewModel mShareViewModel;
    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    protected abstract void initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();

        mShareViewModel = ((App)getApplicationContext()).getAppViewModelProvider(this).get(ShareViewModel.class);

        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.setContentView(this,dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.vm,dataBindingConfig.getStateViewModel());
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0; i < bindingParams.size(); i++){
            binding.setVariable(bindingParams.keyAt(i),bindingParams.valueAt(i));
        }
        mBinding = binding;

        //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }



    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     *
     * @return
     */
    protected ViewDataBinding getBinding() {
        if (isDebug() && mBinding != null) {
            if (mTvStrictModeTip == null) {
                mTvStrictModeTip = new TextView(getApplicationContext());
                mTvStrictModeTip.setAlpha(0.5f);
                mTvStrictModeTip.setTextSize(16);
                mTvStrictModeTip.setBackgroundColor(Color.WHITE);
                mTvStrictModeTip.setText(R.string.debug_activity_databinding_warning);
                ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
            }
        }
        return mBinding;
    }

    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 获取ViewModel实例
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends ViewModel>T getViewModel(Class<T> modelClass){
        if (mActivityProvider == null){
            mActivityProvider = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()));
        }
        return mActivityProvider.get(modelClass);
    }

    public ShareViewModel getShareViewModel(){
        return mShareViewModel;
    }
}
