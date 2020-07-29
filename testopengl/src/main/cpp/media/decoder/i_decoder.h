//
// Created by Administrator on 2020/7/29.
//

#ifndef SPRINGBUD_I_DECODER_H
#define SPRINGBUD_I_DECODER_H

//纯虚类，定义解码器基础功能
class IDecoder {
public:
    virtual void GoOn() = 0;
    virtual void Pause() = 0;
    virtual void Stop() = 0;
    virtual bool IsRunning() = 0;
    virtual long GetDuration() = 0;
    virtual long GetCurPos() = 0;
};

#endif //SPRINGBUD_I_DECODER_H
