//
// Created by Administrator on 2020/7/31.
//

#ifndef SPRINGBUD_A_DECODER_H
#define SPRINGBUD_A_DECODER_H

extern "C"{
//#include <libswscale/swscale.h>
#include "../../../ffmpeg/include/libswresample/swresample.h"
#include "../../../ffmpeg/include/libavcodec/avcodec.h"
#include "../../../ffmpeg/include/libavutil/samplefmt.h"
#include "../../../ffmpeg/include/libavutil/frame.h"
#include "../../../ffmpeg/include/libavutil/opt.h"
}

#include "../base_decoder.h"
#include "../../../utils/const.h"
#include "../../../utils/logger.h"
#include "../../render/audio/audio_render.h"
#include <jni.h>

class AudioDecoder: public BaseDecoder{
private:
    const char *TAG = "AudioDecoder";

    //音频转换器
    SwrContext *m_swr = NULL;

    //音频渲染器
    AudioRender *m_render = NULL;

    //输出缓冲
    uint8_t *m_out_buffer[1] = {NULL};

    //重采样后，每个通道包含的采样数
    // acc默认为1024，重采样后可能会变化
    int m_dest_nb_sample = 1024;

    // 重采样后一帧数据的大小
    size_t m_dest_data_size = 0;

    //初始化转换工具
    void InitSwr();

    // 初始化输出缓冲
    void InitOutBuffer();

    // 初始化渲染器
    void InitRender();

    // 释放缓冲区
    void ReleaseOutBuffer();

    // 采样格式：16位
    AVSampleFormat GetSampleFormat(){
        return AV_SAMPLE_FMT_S16;
    }

    // 目标采样率
    int GetSampleRate(int spr){
        return AUDIO_DEST_SAMPLE_RATE; //44100Hz
    }

public:
    AudioDecoder(JNIEnv *env, const jstring path, bool forSynthesizer);
    ~AudioDecoder();

    void SetRender(AudioRender *render);

protected:
    void Prepare(JNIEnv *env) override ;

    void Render(AVFrame *frame) override;

    void Release() override;

    bool NeedLoopDecode() override{
        return true;
    }

    AVMediaType GetMediaType() override{
        return AVMEDIA_TYPE_AUDIO;
    }

    const char *const LogSpec() override{
        return "AUDIO";
    }
};

#endif //SPRINGBUD_A_DECODER_H
