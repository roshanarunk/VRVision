package org.syriancarrot.hellovr;

import android.opengl.GLES20;
import android.util.Log;

import static android.content.ContentValues.TAG;



public class MyShaders {
    private static final String vertexShaderCode =
            "attribute vec4 position;" +
                    "attribute vec2 inputTextureCoordinate;" +
                    "varying vec2 textureCoordinate;" +
                    "void main()" +
                    "{" +
                    "gl_Position = position;" +
                    "textureCoordinate = inputTextureCoordinate;" +
                    "}";

    private static final String fragmentShaderCode =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;" +
                    "varying vec2 textureCoordinate;   \n" +
                    "uniform samplerExternalOES s_texture; \n" +
                    "uniform float size; \n"+
                    "uniform float brightness; \n"+
                    "uniform float pos_x; \n"+
                    "uniform float pos_y; \n"+
                    "uniform float dif_x; \n"+
                    "uniform float dif_y; \n"+
                    "void main(void) {" +
                    "if( (textureCoordinate.x > pos_x ) && (textureCoordinate.x < pos_x +size) && (textureCoordinate.y > pos_y ) && (textureCoordinate.y < pos_y +size) )"+
                    "{vec2 position = textureCoordinate; position.x = position.x + dif_x; position.y = position.y + dif_y;  gl_FragColor = texture2D( s_texture, position ); }\n" +
                    //"else  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n" +
                    "else  gl_FragColor = texture2D( s_texture, textureCoordinate );\n" +
                    "  gl_FragColor = brightness * gl_FragColor;\n" +
                    "}";

    private static final String fragmentShaderCodeInverse =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;" +
                    "varying vec2 textureCoordinate;                            \n" +
                    "uniform samplerExternalOES s_texture;               \n" +
                    "void main() {\n"
                    + "  vec4 color = texture2D(s_texture, textureCoordinate);\n"
                    + "  float colorR = (1.0 - color.r) / 1.0;\n"
                    + "  float colorG = (1.0 - color.g) / 1.0;\n"
                    + "  float colorB = (1.0 - color.b) / 1.0;\n"
                    + "  gl_FragColor = vec4(colorR, colorG, colorB, color.a);\n"
                    + "}\n";


    private static int loadGLShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0) {
            Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }

        if (shader == 0) {
            throw new RuntimeException("Error creating shader.");
        }

        return shader;
    }

    public static int loadVertexShader() {
        return loadGLShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    }

    public static int loadFragmentShader(int i) {
        if(i == 0)
        return loadGLShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        return loadGLShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCodeInverse);
    }
}
