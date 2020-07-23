package com.fallgod.springbud.ui.dialog;

import com.fallgod.springbud.BR;
import com.fallgod.springbud.Constants;
import com.fallgod.springbud.R;
import com.fallgod.springbud.ui.base.BaseDialogFragment;
import com.fallgod.springbud.ui.base.DataBindingConfig;
import com.fallgod.springbud.ui.viewmodel.CalendarViewModel;
import com.fallgod.springbud.util.LogUtil;

import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Author: JackPan
 * Date: 2020-07-22
 * Time: 10:04
 * Description:
 */
public class AttendanceDialogFragment extends BaseDialogFragment {
    private static final String TAG = "AttendanceDialogFragmen";

    private ViewModelStoreOwner mViewModelStoreOwner;
    private CalendarViewModel mCalendarViewModel;

    public AttendanceDialogFragment() {
        /*每一个继承了 Fragment 的类都必须有一个空参的构造方法，这样当 Activity 被恢复状态时 Fragment 能够被实例化。
        Google强烈建议我们不要使用构造方法进行传参，因为 Fragment 被实例化的时候，这些带参构造函数不会被调用。如果要
        要传递参数，可以使用 setArguments(bundle) 方式来传参。*/
    }

    public static AttendanceDialogFragment newInstance() {
        return new AttendanceDialogFragment();
    }

    public void setViewModelStoreOwner(ViewModelStoreOwner owner){
        mViewModelStoreOwner = owner;
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.dialog_calendar_attendance,mCalendarViewModel)
                .addBindingParams(BR.click, new AttendanceDialogFragment.ClickProxy());
    }

    @Override
    protected void initViewModel() {
        mCalendarViewModel = getFragmentViewModel(CalendarViewModel.class,mViewModelStoreOwner);
    }

    public class ClickProxy {

        public void toWork() {
            LogUtil.d(TAG, "to work");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_ON_WORK);
            dismiss();
        }

        public void offWork() {
            LogUtil.d(TAG, "off work");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_OFF_WORK);
            dismiss();
        }

        public void Work() {
            LogUtil.d(TAG, "work");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_WORK);
            dismiss();
        }

        public void holiday() {
            LogUtil.d(TAG, "holiday");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_HOLIDAY);
            dismiss();
        }

        public void leave() {
            LogUtil.d(TAG, "leave");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_LEAVE);
            dismiss();
        }

        public void somethingLeave() {
            LogUtil.d(TAG, "something leave");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_SOMETHING_LEAVE);
            dismiss();
        }

        public void sickLeave() {
            LogUtil.d(TAG, "something leave");
            mCalendarViewModel.saveData(Constants.ATTENDANCE_SICK_LEAVE);
            dismiss();
        }
    }

}
