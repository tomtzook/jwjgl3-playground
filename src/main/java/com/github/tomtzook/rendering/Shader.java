package com.github.tomtzook.rendering;

import static org.lwjgl.opengl.GL20.*;

import com.github.tomtzook.util.Buffers;
import com.github.tomtzook.util.Resources;
import com.jmath.matrices.Matrix;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Shader implements AutoCloseable {

    private final int mProgramObject;
    private final int mVertexShaderObject;
    private final int mFragmentShaderObject;

    public Shader(String vertexData, String fragmentData) {
        try {
            mProgramObject = glCreateProgram();

            mVertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(mVertexShaderObject, vertexData);
            glCompileShader(mVertexShaderObject);
            if (glGetShaderi(mVertexShaderObject, GL_COMPILE_STATUS) != 1) {
                throw new ShaderCompileException(glGetShaderInfoLog(mVertexShaderObject));
            }

            mFragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(mFragmentShaderObject, fragmentData);
            glCompileShader(mFragmentShaderObject);
            if (glGetShaderi(mFragmentShaderObject, GL_COMPILE_STATUS) != 1) {
                throw new ShaderCompileException(glGetShaderInfoLog(mFragmentShaderObject));
            }

            glAttachShader(mProgramObject, mVertexShaderObject);
            glAttachShader(mProgramObject, mFragmentShaderObject);

            glLinkProgram(mProgramObject);
            if (glGetProgrami(mProgramObject, GL_LINK_STATUS) != 1) {
                throw new ShaderCompileException(glGetProgramInfoLog(mProgramObject));
            }

            glValidateProgram(mProgramObject);
            if (glGetProgrami(mProgramObject, GL_VALIDATE_STATUS) != 1) {
                throw new ShaderCompileException(glGetProgramInfoLog(mProgramObject));
            }
        } catch (RuntimeException | Error e) {
            try {
                close();
            } catch (Throwable ignore) {}

            throw e;
        }
    }

    public Shader(String baseFilename) {
        this(Resources.readFileOrThrow("/shaders/" + baseFilename + ".vs", "Failed to read shader file"),
                Resources.readFileOrThrow("/shaders/" + baseFilename + ".fs", "Failed to read shader file"));
    }

    public void setUniform(String name, int value) {
        int location = glGetUniformLocation(mProgramObject, name);
        if (location == -1) {
            return;
        }

        glUniform1i(location, value);
    }

    public void setUniform(String name, Matrix matrix) {
        int location = glGetUniformLocation(mProgramObject, name);
        if (location == -1) {
            return;
        }

        // todo: better memory management
        FloatBuffer buffer = Buffers.fromMat(matrix);
        glUniformMatrix4fv(location, false, buffer);
    }

    public void bind() {
        glUseProgram(mProgramObject);
    }

    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void close() throws Exception {
        glDetachShader(mProgramObject, mVertexShaderObject);
        glDetachShader(mProgramObject, mFragmentShaderObject);
        glDeleteShader(mVertexShaderObject);
        glDeleteShader(mFragmentShaderObject);
        glDeleteProgram(mProgramObject);
    }
}