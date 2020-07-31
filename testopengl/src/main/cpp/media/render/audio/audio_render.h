//
// Created by Administrator on 2020/7/31.
//

#ifndef SPRINGBUD_AUDIO_RENDER_H
#define SPRINGBUD_AUDIO_RENDER_H

#include <cstdint>

class AudioRender{
public:
    virtual void InitRender() = 0;
    virtual void Render(uint8_t *pcm, int size) = 0;
    virtual void ReleaseRender() = 0;
    virtual ~AudioRender(){}
};

#endif //SPRINGBUD_AUDIO_RENDER_H
