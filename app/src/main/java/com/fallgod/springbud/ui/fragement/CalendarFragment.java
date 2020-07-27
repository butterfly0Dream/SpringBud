package com.fallgod.springbud.ui.fragement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.R;
import com.fallgod.springbud.databinding.FragmentCalendarBinding;
import com.fallgod.springbud.ui.base.BaseFragment;
import com.fallgod.springbud.ui.base.DataBindingConfig;
import com.fallgod.springbud.ui.dialog.AttendanceDialogFragment;
import com.fallgod.springbud.ui.viewmodel.CalendarViewModel;
import com.fallgod.springbud.util.LogUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import androidx.annotation.Nullable;

/**
 * Author: JackPan
 * Date: 2020-07-17
 * Time: 11:58
 * Description:
 */
public class CalendarFragment extends BaseFragment {
    private static final String TAG = "CalendarFragment";

    private CalendarViewModel mCalendarViewModel;
    private AttendanceDialogFragment mAttendanceDialog;

//    CalendarView mCalendarView;

    @Override
    protected void initViewModel() {
        mCalendarViewModel = getFragmentViewModel(CalendarViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_calendar, mCalendarViewModel)
                .addBindingParams(BR.click, new ClickProxy())
                .addBindingParams(BR.listener, new EventHandler());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    @SuppressLint("SetTextI18n")
    protected void initView() {
//        mCalendarView = getView().findViewById(R.id.calendarView);
        CalendarView mCalendarView = ((FragmentCalendarBinding)getBinding()).calendarView;
        mCalendarViewModel.textYear.setValue(mCalendarView.getCurYear());
        mCalendarViewModel.textMonthDay.setValue(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mCalendarViewModel.textLunar.setValue("今日");
        mCalendarViewModel.textMonth.setValue(String.valueOf(mCalendarView.getCurMonth()));
        mCalendarViewModel.textDay.setValue(String.valueOf(mCalendarView.getCurDay()));
    }

    protected void initData() {
        mCalendarViewModel.refreshSchemeData();
    }

    private void showAttendanceDialog(){
        if (mAttendanceDialog == null){
            Log.d(TAG, "showAttendanceDialog: create");
            mAttendanceDialog = AttendanceDialogFragment.newInstance();
            mAttendanceDialog.setViewModelStoreOwner(this);
        }
        mAttendanceDialog.show(getChildFragmentManager(),"dialog");
    }

    public class ClickProxy {
        public void scrollToCurrent(CalendarView calendarView) {
            Log.d(TAG, "scrollToCurrent: ");
            calendarView.scrollToCurrent();
        }

        public void showYearSelectLayout(CalendarView calendarView) {
            calendarView.showYearSelectLayout(calendarView.getCurYear());
            mCalendarViewModel.showLunar.postValue(false);
            mCalendarViewModel.textMonthDay.postValue(calendarView.getCurYear() + "");
            mCalendarViewModel.textYear.setValue(calendarView.getCurYear());
        }
    }

    public class EventHandler implements
            CalendarView.OnCalendarSelectListener,
            CalendarView.OnYearChangeListener,
            CalendarView.OnCalendarLongClickListener {

        @Override
        public void onCalendarOutOfRange(Calendar calendar) {
            LogUtil.d(TAG, "onCalendarOutOfRange");
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            LogUtil.d(TAG, "onCalendarSelect");
            mCalendarViewModel.showLunar.postValue(true);
            mCalendarViewModel.textLunar.postValue(calendar.getLunar());
            mCalendarViewModel.textYear.postValue(calendar.getYear());
            mCalendarViewModel.textMonth.postValue(String.valueOf(calendar.getMonth()));
            mCalendarViewModel.textDay.postValue(String.valueOf(calendar.getDay()));
            mCalendarViewModel.textMonthDay.postValue(calendar.getMonth() + "月" + calendar.getDay() + "日");
        }

        @Override
        public void onYearChange(int year) {
            mCalendarViewModel.textMonthDay.postValue(String.valueOf(year));
        }

        @Override
        public void onCalendarLongClickOutOfRange(Calendar calendar) {
            LogUtil.d(TAG, "onCalendarLongClickOutOfRange");
        }

        @Override
        public void onCalendarLongClick(Calendar calendar) {
            LogUtil.d(TAG, "Calendar long click:" + calendar.getYear() + calendar.getMonth() + "月" + calendar.getDay() + "日");
            showAttendanceDialog();
        }
    }
}
