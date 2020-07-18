package com.fallgod.springbud.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 16:19
 * Description:
 */
public class MainViewModel extends ViewModel {

    public final MutableLiveData<String> textContent = new MutableLiveData<>();
    public final MutableLiveData<String> nvHeader = new MutableLiveData<>();
    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>();
    public final MutableLiveData<Boolean> allowOpenDrawer = new MutableLiveData<>();

    {
        allowOpenDrawer.setValue(true);
        openDrawer.setValue(false);
        nvHeader.setValue("Header View");
        textContent.setValue("Spring Bud");

    }
}
