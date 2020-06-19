package com.fallgod.springbud.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.fallgod.springbud.R;

public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.sample_text);

        mMainViewModel.getElapsedTime().observe(this, data->{
            tv.setText(data + "");
        });
    }

    @Override
    protected void initViewModel() {
        mMainViewModel = getViewModel(MainViewModel.class);
    }
}
