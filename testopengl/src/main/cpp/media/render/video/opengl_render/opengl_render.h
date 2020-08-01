//
// Created by Administrator on 2020/8/1.
//

#ifndef SPRINGBUD_OPENGL_RENDER_H
#define SPRINGBUD_OPENGL_RENDER_H

#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <jni.h>
#include "../video_render.h"
#include "../../../../opengl/drawer/drawer.h"
#include "../../../../opengl/egl/egl_surface.h"
#include "../../../../opengl/drawer/proxy/drawer_proxy.h"
#include <memory>


class OpenGLRender {
private:

    const char *TAG = "OpenGLRender";

    enum STATE {
        NO_SURFACE, //没有有效的surface
        FRESH_SURFACE, //持有一个未初始化的新的surface
        RENDERING, //初始化完毕，可以开始渲染
        SURFACE_DESTROY, //surface销毁
        STOP //停止绘制
    };

    JNIEnv *m_env = NULL;

    // 线程依附的JVM环境
    JavaVM *m_jvm_for_thread = NULL;

    // Surface引用，必须使用引用，否则无法在线程中操作
    jobject m_surface_ref = NULL;

    // 本地屏幕
    ANativeWindow *m_native_window = NULL;

    // EGL显示表面
    EglSurface *m_egl_surface = NULL;

    // 绘制代理器
    DrawerProxy *m_drawer_proxy = NULL;

    int m_window_width = 0;
    int m_window_height = 0;

    STATE m_state = NO_SURFACE;

    // 初始化相关的方法
    void InitRenderThread();
    bool InitEGL();
    void InitDspWindow(JNIEnv *env);

    // 创建Surface
    void CreateSurface();
    void DestroySurface();

    // 渲染方法
    void Render();

    // 释放资源相关方法
    void ReleaseRender();
    void ReleaseDrawers();
    void ReleaseSurface();
    void ReleaseWindow();

    // 渲染线程回调方法
    static void sRenderThread(std::shared_ptr<OpenGLRender> that);

public:
    OpenGLRender(JNIEnv *env, DrawerProxy *drawer_proxy);
    ~OpenGLRender();

    void SetSurface(jobject surface);
    void SetOffScreenSize(int width, int height);
    void Stop();
};

#endif //SPRINGBUD_OPENGL_RENDER_H
