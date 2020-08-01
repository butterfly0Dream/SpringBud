//
// Created by Administrator on 2020/7/29.
//

#ifndef SPRINGBUD_PLAYER_H
#define SPRINGBUD_PLAYER_H


#include "decoder/video/v_decoder.h"
#include "decoder/audio/a_decoder.h"
#include "render/audio/audio_render.h"

class Player {
private:
    VideoDecoder *m_v_decoder;
    VideoRender *m_v_render;

    // 新增音频解码和渲染器
    AudioDecoder *m_a_decoder;
    AudioRender *m_a_render;

public:
    Player(JNIEnv *jniEnv, jstring path, jobject surface);
    ~Player();

    void play();
    void pause();
    void stop();
    char * getState();
};

#endif //SPRINGBUD_PLAYER_H
