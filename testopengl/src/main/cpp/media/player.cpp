//
// Created by Administrator on 2020/7/29.
//

#include "player.h"
#include "render/video/native_render/native_render.h"
#include "../utils/logger.h"
#include "render/audio/opensl_render.h"

Player::Player(JNIEnv *jniEnv, jstring path, jobject surface) {
    m_v_decoder = new VideoDecoder(jniEnv, path);
    m_v_render = new NativeRender(jniEnv, surface);
    m_v_decoder->SetRender(m_v_render);

    m_a_decoder = new AudioDecoder(jniEnv, path, false);
    m_a_render = new OpenSLRender();
    m_a_decoder->SetRender(m_a_render);
}

Player::~Player() {
    // 此处不需要 delete 成员指针
    // 在BaseDecoder中的线程已经使用智能指针，会自动释放
}

void Player::play() {
    if (m_v_decoder != NULL) {
        m_v_decoder->GoOn();
    }
    if (m_a_decoder != NULL){
        m_a_decoder->GoOn();
    }
}

void Player::pause() {
    if (m_v_decoder != NULL) {
        m_v_decoder->Pause();
    }
    if (m_a_decoder != NULL){
        m_a_decoder->Pause();
    }
}

void Player::stop() {
    if (m_v_decoder != NULL) {
        m_v_decoder->Stop();
    }
    if (m_a_decoder != NULL){
        m_a_decoder->Stop();
    }
}

char * Player::getState() {
    if (m_v_decoder != NULL){
        return m_v_decoder->GetStateStr();
    }
    return "";
}