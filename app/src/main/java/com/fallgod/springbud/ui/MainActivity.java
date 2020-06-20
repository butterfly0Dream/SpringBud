package com.fallgod.springbud.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.sample_text);

//        mMainViewModel.getElapsedTime().observe(this, data->{
//            tv.setText(data + "");
//        });

        ViewDataBinding dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setVariable(BR.vm,mMainViewModel);
        dataBinding.setVariable(BR.event,new ClickProxy());
    }

    @Override
    protected void initViewModel() {
        mMainViewModel = getViewModel(MainViewModel.class);
    }

    public class ClickProxy{
        public void changeText(){
            mMainViewModel.textContent.setValue("hhhh");
        }
    }
}
