package com.fallgod.springbud.ui.base.binding;

import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Author: JackPan
 * Date: 2020-07-15
 * Time: 15:50
 * Description:自定义的主页DrawerLayout属性值
 */
public class DrawerBindingAdapter {

    /**
     * drawerLayout的打开关闭行为
     * requireAll = false:设置任何属性时都调用适配器
     * @param drawerLayout
     * @param isOpenDrawer
     */
    @BindingAdapter(value = {"isOpenDrawer"}, requireAll = false)
    public static void openDrawer(DrawerLayout drawerLayout, boolean isOpenDrawer){
        if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.openDrawer(GravityCompat.START);
        }else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //是否允许打开drawer
    @BindingAdapter(value = {"allowDrawerOpen"}, requireAll = false)
    public static void allowDrawerOpen(DrawerLayout drawerLayout,boolean allowDrawerOpen){
        drawerLayout.setDrawerLockMode(allowDrawerOpen
                ?DrawerLayout.LOCK_MODE_UNLOCKED
                :DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    //添加drawerLayout打开状态监听
    @BindingAdapter(value = {"bindDrawerListener"}, requireAll = false)
    public static void listenDrawerState(DrawerLayout drawerLayout, DrawerLayout.SimpleDrawerListener listener){
        drawerLayout.addDrawerListener(listener);
    }

}
