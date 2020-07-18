package com.fallgod.springbud.ui.fragement;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;
import com.fallgod.springbud.ui.base.BaseFragment;
import com.fallgod.springbud.ui.base.DataBindingConfig;
import com.fallgod.springbud.ui.viewmodel.WeatherViewModel;

/**
 * Author: JackPan
 * Date: 2020-07-17
 * Time: 11:32
 * Description:
 */
public class WeatherFragment extends BaseFragment {

    private WeatherViewModel mWeatherViewModel;

    @Override
    protected void initViewModel() {
        mWeatherViewModel = getFragmentViewModel(WeatherViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_weather,mWeatherViewModel)
                .addBindingParams(BR.click,new ClickProxy());
    }

    public class ClickProxy{

    }
}
