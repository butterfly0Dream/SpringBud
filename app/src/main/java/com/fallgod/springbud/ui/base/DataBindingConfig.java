package com.fallgod.springbud.ui.base;

import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * Author: JackPan
 * Date: 2020-07-15
 * Time: 09:59
 * Description:
 *  将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 *  通过这样的方式，来彻底解决 视图调用的一致性问题，
 */
public class DataBindingConfig {

    private int layout;
    private ViewModel stateViewModel;
    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, ViewModel stateViewModel){
        this.layout = layout;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout(){
        return layout;
    }

    public ViewModel getStateViewModel(){
        return stateViewModel;
    }

    public SparseArray getBindingParams(){
        return bindingParams;
    }

    public DataBindingConfig addBindingParams(int variable, Object object){
        if (bindingParams.get(variable) == null){
            bindingParams.put(variable,object);
        }
        return this;
    }
}
