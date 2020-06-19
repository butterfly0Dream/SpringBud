package com.fallgod.testopengl.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.fallgod.testopengl.util.LogUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author: JackPan
 * Date: 2020-06-18
 * Time: 09:04
 * Description:
 */
class PointRender implements GLSurfaceView.Renderer {
    private static final String TAG = "PointRender";

    /**
     * 顶点着色器
     */
    private static String VERTEX_SHADER = "" +
            //vec4: 四个分量的向量：x,y,z,w
            "attribute vec4 a_Position;\n" +
            "void main()\n" +
            "{\n"+
            // gl_Position:GL中默认定义的输出变量，决定了当前顶点的最终位置
            "   gl_Position = a_Position;\n" +
            // gl_PointSize:GL中默认定义的输出变量，决定了当前顶点的大小
            "   gl_PointSize = 40.0;\n" +
            "}";

    /**
     * 片段着色器
     */
    private static String FRAGMENT_SHADER = "" +
            // 定义所有浮点数据类型的默认精读；有lowp/mediump/heightp三种，但只有部分硬件支持片段着色器使用hightp.(顶点着色器默认hightp)
            "precision mediump float;\n" +
            "uniform mediump vec4 u_Color;\n" +
            "void main()\n" +
            "{\n" +
            // gl_FragColor:GL中默认定义的输出变量，决定了当前片段的颜色
            "   gl_FragColor = u_Color;\n" +
            "}";



    /**
     * 编译着色器
     * @param type 着色器类型
     * @param shaderCode 编译代码
     * @return 着色器对象ID
     */
    private int compileShader(int type,String shaderCode){
        // 1.创建一个新的着色器对象
        final int shaderObjectId = GLES20.glCreateShader(type);

        // 2.获取创建状态
        if (shaderObjectId == 0){
            // 在OpenGL中，都是通过整型值去作为OpenGL对象的引用。之后进行操作的时候都是将这个整型值传回给OpenGL进行操作
            // 返回值0代表创建失败
            LogUtil.d(TAG,"创建shader失败");
            return 0;
        }

        // 3.将着色器代码上传到着色器对象中
        GLES20.glShaderSource(shaderObjectId,shaderCode);

        // 4.编译着色器对象
        GLES20.glCompileShader(shaderObjectId);

        // 5.获取编译状态：OpenGL将想要获取的值放入长度为1的数组首位
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId,GLES20.GL_COMPILE_STATUS,compileStatus,0);
        LogUtil.d(TAG,"Results of source:\n" + shaderCode + "\n" + GLES20.glGetShaderInfoLog(shaderObjectId));

        // 6.验证编译状态
        if (compileStatus[0] == 0){
            // 如果编译失败，则删除创建的着色器对象
            GLES20.glDeleteShader(shaderObjectId);
            LogUtil.d(TAG,"Compilation of shader failed.");
            return 0;
        }

        // 7.返回着色器对象
        return shaderObjectId;
    }

    /**
     * 创建OpenGL程序：通过链接顶点着色器、片段着色器
     *
     * @param vertexShaderId   顶点着色器ID
     * @param fragmentShaderId 片段着色器ID
     * @return OpenGL程序ID
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

        // 1.创建一个OpenGL程序对象
        final int programObjectId = GLES20.glCreateProgram();

        // 2.获取创建状态
        if (programObjectId == 0) {
            LogUtil.d(TAG,"Could not create new program");
            return 0;
        }

        // 3.将顶点着色器依附到OpenGL程序对象
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        // 3.将片段着色器依附到OpenGL程序对象
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        // 4.将两个着色器链接到OpenGL程序对象
        GLES20.glLinkProgram(programObjectId);

        // 5.获取链接状态：OpenGL将想要获取的值放入长度为1的数组的首位
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);

        LogUtil.d(TAG,"Results of linking program:\n"
                + GLES20.glGetProgramInfoLog(programObjectId));

        // 6.验证链接状态
        if (linkStatus[0] == 0) {
            // 链接失败则删除程序对象
            GLES20.glDeleteProgram(programObjectId);
            LogUtil.d(TAG,"Linking of program failed.");
            // 7.返回程序对象：失败，为0
            return 0;
        }

        // 7.返回程序对象：成功，非0
        return programObjectId;
    }

    /**
     * 验证OpenGL程序对象状态
     *
     * @param programObjectId OpenGL程序ID
     * @return 是否可用
     */
    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + GLES20.glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

    /**
     * 创建OpenGL程序对象
     *
     * @param vertexShader   顶点着色器代码
     * @param fragmentShader 片段着色器代码
     */
    protected void makeProgram(String vertexShader, String fragmentShader) {
        // 步骤1：编译顶点着色器
        int vertexShaderId = compileShader(GLES20.GL_VERTEX_SHADER,vertexShader);
        // 步骤2：编译片段着色器
        int fragmentShaderId = compileShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
        // 步骤3：将顶点着色器、片段着色器进行链接，组装成一个OpenGL程序
        mProgram = linkProgram(vertexShaderId, fragmentShaderId);

        validateProgram(mProgram);

        // 步骤4：通知OpenGL开始使用该程序
        GLES20.glUseProgram(mProgram);
    }

    int mProgram;
    /**
     * Surface被创建时调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        makeProgram(VERTEX_SHADER,FRAGMENT_SHADER);
        
    }

    /**
     * Surface尺寸改变时调用
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    /**
     * 渲染绘制每一帧时调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
