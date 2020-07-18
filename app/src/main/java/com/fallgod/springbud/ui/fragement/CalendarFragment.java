package com.fallgod.springbud.ui.fragement;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;
import com.fallgod.springbud.ui.base.BaseFragment;
import com.fallgod.springbud.ui.base.DataBindingConfig;
import com.fallgod.springbud.ui.viewmodel.CalendarViewModel;

/**
 * Author: JackPan
 * Date: 2020-07-17
 * Time: 11:58
 * Description:
 */
public class CalendarFragment extends BaseFragment {

    private CalendarViewModel mCalendarViewModel;

    @Override
    protected void initViewModel() {
        mCalendarViewModel = getFragmentViewModel(CalendarViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_calendar,mCalendarViewModel)
                .addBindingParams(BR.click,new ClickProxy());
    }

    public class ClickProxy{

    }
}
