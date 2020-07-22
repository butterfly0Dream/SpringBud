package com.fallgod.springbud.jni;

/**
 * Author: JackPan
 * Date: 2020-06-19
 * Time: 15:42
 * Description:
 */
class NativeBean {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
