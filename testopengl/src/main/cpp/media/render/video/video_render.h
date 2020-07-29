//
// Created by Administrator on 2020/7/29.
//

#ifndef SPRINGBUD_VIDEO_RENDER_H
#define SPRINGBUD_VIDEO_RENDER_H

#include <stdint.h>
#include <jni.h>

#include "../../one_frame.h"

class VideoRender {
public:
    virtual void InitRender(JNIEnv *env, int video_width, int video_height, int *dst_size) = 0;
    virtual void Render(OneFrame *one_frame) = 0;
    virtual void ReleaseRender() = 0;
};

#endif //SPRINGBUD_VIDEO_RENDER_H
