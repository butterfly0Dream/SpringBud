//
// Created by Administrator on 2020/7/29.
//

#ifndef SPRINGBUD_PLAYER_H
#define SPRINGBUD_PLAYER_H


#include "decoder/video/v_decoder.h"

class Player {
private:
    VideoDecoder *m_v_decoder;
    VideoRender *m_v_render;

public:
    Player(JNIEnv *jniEnv, jstring path, jobject surface);
    ~Player();

    void play();
    void pause();
};

#endif //SPRINGBUD_PLAYER_H
