package com.fallgod.testopengl.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author: JackPan
 * Date: 2020-07-24
 * Time: 11:38
 * Description:
 */
public class TriangleRenderer implements GLSurfaceView.Renderer {

    /**
     * gl_Position为预定义的值
     * <p>属性解释</p>
     * <ul>
     * <li>uniform 由外部程序传递给shader,不可更改</li>
     * <li>attribute 只能在Vertex中使用的变量</li>
     * <li>varying 变量是vertex shader和fragment shader之间做数据传递</li>
     * </ul>
     */
    private static final String VERTEX_SHADER =
            "attribute vec4 vPosition;\n"
                    + "uniform mat4 uMVPMatrix;\n"
                    + "void main(){\n"
                    + "    gl_Position = uMVPMatrix * vPosition;\n"
                    + "}";

    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n"
                    + "void main(){\n"
                    + "    gl_FragColor = vec4(0.5,0,0,1);\n"
                    + "}";

    //顶点坐标
    private static final float[] VERTEX = {   // in counterclockwise order:
            0f, 0.5f, 0f,  // top
            -0.5f, 0f, 0f,  // bottom left
            0.5f, 0f, 0f,  // bottom right
    };

    private FloatBuffer mVertexBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mMatrixHandle;
    private final float[] mMvpMatrix = new float[16];

    public TriangleRenderer() {
        //设置顶点的缓存
        mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        mVertexBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mProgram = GLES20.glCreateProgram();     //创建GLSL程序

        //加载shader代码
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        //绑定shader
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);      //链接GLSL程序
        GLES20.glUseProgram(mProgram);       //使用GLSL程序
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");       //获取shader程序中的变量索引
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glEnableVertexAttribArray(mPositionHandle);       //启用vertex
        // 12的含义是步长，我的理解是一个顶点位置所需要的buffer大小，即3 * sizeof(float)的大小
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, mVertexBuffer);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0, 0, i, i1);
        Matrix.perspectiveM(mMvpMatrix, 0, 45, (float) i / i1, 1f, 100f);//得到透视投影矩阵
        //由于历史原因，Matrix.perspectiveM 会让 z 轴方向倒置，所以左乘投影矩阵之后，顶点 z 坐标需要在 -zNear~-zFar 范围内才会可见。
        Matrix.translateM(mMvpMatrix, 0, 0f, 0f, -10f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMvpMatrix, 0);//为uMVPMatrix复制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
