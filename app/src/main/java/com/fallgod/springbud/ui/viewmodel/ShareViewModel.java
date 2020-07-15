package com.fallgod.springbud.ui.viewmodel;

import com.fallgod.springbud.callback.EventLiveData;

import androidx.lifecycle.ViewModel;

/**
 * Author: JackPan
 * Date: 2020-07-15
 * Time: 16:17
 * Description:全局的ViewModel，只存在于base页面中
 * 通过 EventLiveData<Event<Object>> 配合 SharedViewModel 来发送 生命周期安全的、事件源可追溯的 通知
 * 在页面通信的场景下，使用全局 ViewModel，是因为它被封装在 base 页面中，避免页面之外的组件拿到，从而造成不可预期的推送，
 * 而且尽可能使用单例或 ViewModel 托管 liveData，这样调试时能根据内存中的 liveData 对象找到事件源
 * liveDataBus 这种通过 tag 来标记的，难以找到
 */
public class ShareViewModel extends ViewModel {

    //打开、关闭drawerLayout事件
    public final EventLiveData<Boolean> openOrCloseDrawer = new EventLiveData<>();

}
