//
// Created by Administrator on 2020/8/1.
//

#include "video_drawer.h"

VideoDrawer::VideoDrawer() : Drawer(0, 0) {
}

VideoDrawer::~VideoDrawer() {

}

void VideoDrawer::InitRender(JNIEnv *env, int video_width, int video_height, int *dst_size) {
    SetSize(video_width, video_height);
    dst_size[0] = video_width;
    dst_size[1] = video_height;
}

void VideoDrawer::Render(OneFrame *one_frame) {
    cst_data = one_frame->data;
}

void VideoDrawer::ReleaseRender() {
}

const char *VideoDrawer::GetVertexShader() {
    //用此方式定义再转换字符串，结果会有乱码
//    const GLbyte shader[] = "attribute vec4 aPosition;\n"
//                            "attribute vec2 aCoordinate;\n"
//                            "varying vec2 vCoordinate;\n"
//                            "void main() {\n"
//                            "  gl_Position = aPosition;\n"
//                            "  vCoordinate = aCoordinate;\n"
//                            "}";
//    return (char *) shader;

    return "attribute vec4 aPosition;\n"
           "attribute vec2 aCoordinate;\n"
           "varying vec2 vCoordinate;\n"
           "void main() {\n"
           //                            "  gl_Position = uMatrix*aPosition;\n"
           "  gl_Position = aPosition;\n"
           "  vCoordinate = aCoordinate;\n"
           "}";
}

const char *VideoDrawer::GetFragmentShader() {
//    const GLbyte shader[] = "precision mediump float;\n"
//                            "uniform sampler2D uTexture;\n"
//                            "varying vec2 vCoordinate;\n"
//                            "void main() {\n"
//                            "  vec4 color = texture2D(uTexture, vCoordinate);\n"
//                            "  gl_FragColor = color;\n"
//                            "}";
//    return (char *) shader;

    return  "precision mediump float;\n"
            "uniform sampler2D uTexture;\n"
            "varying vec2 vCoordinate;\n"
            "void main() {\n"
            "  vec4 color = texture2D(uTexture, vCoordinate);\n"
            "  gl_FragColor = color;\n"
            "}";
}

void VideoDrawer::InitCstShaderHandler() {

}

void VideoDrawer::BindTexture() {
    ActivateTexture();
}

void VideoDrawer::PrepareDraw() {
    if (cst_data != NULL) {
        glTexImage2D(GL_TEXTURE_2D, 0, // level一般为0
                     GL_RGBA, //纹理内部格式
                     origin_width(), origin_height(), // 画面宽高
                     0, // 必须为0
                     GL_RGBA, // 数据格式，必须和上面的纹理格式保持一直
                     GL_UNSIGNED_BYTE, // RGBA每位数据的字节数，这里是BYTE​: 1 byte
                     cst_data);// 画面数据
    }
}

void VideoDrawer::DoneDraw() {
}