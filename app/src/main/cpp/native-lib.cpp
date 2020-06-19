#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_fallgod_springbud_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_fallgod_springbud_data_NativeBean_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()
}