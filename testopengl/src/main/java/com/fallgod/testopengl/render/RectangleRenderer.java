package com.fallgod.testopengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.fallgod.testopengl.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author: JackPan
 * Date: 2020-07-24
 * Time: 15:18
 * Description:
 */
public class RectangleRenderer implements GLSurfaceView.Renderer {
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
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";
    private static final String FRAGMENT_SHADER =
            "precision mediump float;" +
                    "varying vec2 v_texCoord;" +
                    "uniform sampler2D s_texture;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
                    "}";

    //顶点坐标（6个点，两个重复）
    private static final float[] VERTEX = {   // in counterclockwise order:
            1, 1, 0,   // top right
            -1, 1, 0,  // top left
            -1, -1, 0, // bottom left
            1, -1, 0,  // bottom right
    };

    //顶点绘制顺序
    private static final short[] VERTEX_INDEX = { 0, 1, 2, 0, 2, 3 };

    //指定截取纹理的哪部分绘制
    private static final float[] TEX_VERTEX = {   // in clockwise order:
            1, 0,  // bottom right
            0, 0,  // bottom left
            0, 1,  // top left
            1, 1,  // top right
    };

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mTexVertexBuffer;
    private final ShortBuffer mVertexIndexBuffer;
    private final float[] mMvpMatrix = new float[16];

    private int mProgram;
    private int mPositionHandle;
    private int mMatrixHandle;
    private int mTexCoordHandle ;
    private int mTexSamplerHandle ;
    private int mTexName;
    private Context mContext;

    public RectangleRenderer(Context context) {
        mContext = context;

        //设置顶点的缓存
        mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        mVertexBuffer.position(0);

        //设置顶点绘制顺序的缓存
        mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(VERTEX_INDEX);
        mVertexIndexBuffer.position(0);

        //设置纹理截图部分的缓存
        mTexVertexBuffer = ByteBuffer.allocateDirect(TEX_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TEX_VERTEX);
        mTexVertexBuffer.position(0);
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
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texCoord");
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        mTexSamplerHandle = GLES20.glGetUniformLocation(mProgram, "s_texture");

        GLES20.glEnableVertexAttribArray(mPositionHandle);       //启用vertex
        // 12的含义是步长，我的理解是一个顶点位置所需要的buffer大小，即3 * sizeof(float)的大小
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, mVertexBuffer);

        GLES20.glEnableVertexAttribArray(mTexCoordHandle);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, mTexVertexBuffer);


        int[] texNames = new int[1];
        //创建纹理
        GLES20.glGenTextures(1, texNames, 0);
        mTexName = texNames[0];
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.tset);
        //激活指定编号的纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //将新建的纹理和编号绑定起来
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexName);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);//设置缩小时的纹理过滤方式为GL_LINEAR,线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);//设置放大时的纹理过滤方式为GL_LINEAR,线性过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_REPEAT);//设置x轴的纹理环绕方式为GL_REPEAT，默认行为，重复纹理图像
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_REPEAT);//设置y轴的纹理环绕方式为GL_REPEAT，默认行为，重复纹理图像
        //将图片数据拷贝到纹理中
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0, 0, i, i1);

        Matrix.perspectiveM(mMvpMatrix, 0, 45, (float) i / i1, 0.1f, 100f);//得到透视投影矩阵
        //由于历史原因，Matrix.perspectiveM 会让 z 轴方向倒置，所以左乘投影矩阵之后，顶点 z 坐标需要在 -zNear~-zFar 范围内才会可见。
        Matrix.translateM(mMvpMatrix, 0, 0f, 0f, -5f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMvpMatrix, 0);//为uMVPMatrix赋值
        GLES20.glUniform1i(mTexSamplerHandle,0);

        //用glDrawElements 来绘制，mVertexIndexBuffer 指定了顶点绘制顺序
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length, GLES20.GL_UNSIGNED_SHORT,mVertexIndexBuffer);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
