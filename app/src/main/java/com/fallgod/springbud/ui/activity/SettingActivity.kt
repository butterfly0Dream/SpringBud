package com.fallgod.springbud.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import com.fallgod.springbud.BR
import com.fallgod.springbud.R
import com.fallgod.springbud.ui.base.BaseActivity
import com.fallgod.springbud.ui.base.DataBindingConfig
import com.fallgod.springbud.ui.viewmodel.SettingViewModel

class SettingActivity : BaseActivity() {
    private val TAG = "SettingActivity"

    private lateinit var mSettingViewModel: SettingViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setStatusBarColor() {
        statusBarColor = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getColor(R.color.colorPrimary)
        }else{
            resources.getColor(R.color.colorPrimary)
        }
    }

    override fun initViewModel() {
        mSettingViewModel = getViewModel(SettingViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_setting, mSettingViewModel)
                .addBindingParams(BR.event, ClickProxy())
    }

    inner class ClickProxy{
        fun back(){
            finish();
        }
    }
}