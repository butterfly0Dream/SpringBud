package com.fallgod.springbud.ui.base;

import android.os.Bundle;
import android.util.SparseArray;

import com.fallgod.springbud.App;
import com.fallgod.springbud.BR;
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
