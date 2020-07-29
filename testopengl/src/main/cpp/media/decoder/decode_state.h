//
// Created by Administrator on 2020/7/29.
//

#ifndef SPRINGBUD_DECODE_STATE_H
#define SPRINGBUD_DECODE_STATE_H

//解码器状态
enum DecodeState {
    STOP,
    PREPARE,
    START,
    DECODING,
    PAUSE,
    FINISH
};

#endif //SPRINGBUD_DECODE_STATE_H