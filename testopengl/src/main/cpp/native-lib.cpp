#include <jni.h>
#include <string>
#include <unistd.h>
#include <android/log.h>

#include "media/player/def_player/player.h"
#include "utils/logger.h"
#include "media/player/gl_player/gl_player.h"

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavfilter/avfilter.h>
#include <libavcodec/jni.h>

JNIEXPORT jstring JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_ffmpegInfo(JNIEnv *env, jobject  /* this */) {

    char info[40000] = {0};
    AVCodec *c_temp = av_codec_next(NULL);
    while (c_temp != NULL) {
        if (c_temp->decode != NULL) {
            sprintf(info, "%sdecode:", info);
        } else {
            sprintf(info, "%sencode:", info);
        }
        switch (c_temp->type) {
            case AVMEDIA_TYPE_VIDEO:
                sprintf(info, "%s(video):", info);
                break;
            case AVMEDIA_TYPE_AUDIO:
                sprintf(info, "%s(audio):", info);
                break;
            default:
                sprintf(info, "%s(other):", info);
                break;
        }
        sprintf(info, "%s[%s]\n", info, c_temp->name);
        c_temp = c_temp->next;
    }

    return env->NewStringUTF(info);
}

JNIEXPORT jint JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_createPlayer(JNIEnv *env,
                                                        jobject /* this */,
                                                        jstring path,
                                                        jobject surface) {
    GLPlayer *player = new GLPlayer(env, path);
    player->SetSurface(surface);
    return (jint) player;
}

JNIEXPORT void JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_play(JNIEnv *env,
                                                jobject /* this */,
                                                jint player) {
    GLPlayer *p = (GLPlayer *) player;
    p->PlayOrPause();
}

JNIEXPORT void JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_pause(JNIEnv *env,
                                                 jobject /* this */,
                                                 jint player) {
    GLPlayer *p = (GLPlayer *) player;
    p->PlayOrPause();
}

JNIEXPORT void JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_stop(JNIEnv *env,
                                                    jobject /* this */,
                                                    jint player) {
    GLPlayer *p = (GLPlayer *) player;
    p->Release();
}

JNIEXPORT jstring JNICALL
Java_com_fallgod_testopengl_ui_FFmpegActivity_getState(JNIEnv *env,
                                                    jobject /* this */,
                                                    jint player) {
    GLPlayer *p = (GLPlayer *) player;
    //将char * 转换成jstring
    return env->NewStringUTF(p->getState());
}

}
