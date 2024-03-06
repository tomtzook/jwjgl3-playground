package com.github.tomtzook.rendering;

import com.github.tomtzook.util.AdditionalMath;
import com.github.tomtzook.util.Buffers;
import com.jmath.vectors.Vector3;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh implements AutoCloseable {

    private final int mDrawCount;
    private final int mVertexObject;
    private final int mTextCoordsObject;
    private final int mNormalsObject;
    private final int mIndexObject;

    private Texture mTexture;
    private Vector3fc mColor;

    public Mesh(FloatBuffer positions, FloatBuffer textCoords, FloatBuffer normals, IntBuffer indices) {
        mDrawCount = indices.limit();

        mVertexObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mVertexObject);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);

        mTextCoordsObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mTextCoordsObject);
        glBufferData(GL_ARRAY_BUFFER, textCoords, GL_STATIC_DRAW);

        mNormalsObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mNormalsObject);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);

        mIndexObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndexObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        mColor = new Vector3f();
    }

    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        this(Buffers.fromArray(positions), Buffers.fromArray(textCoords), Buffers.fromArray(normals), Buffers.fromArray(indices));
    }

    public void setTexture(Texture texture) {
        mTexture = texture;
    }

    public void setColor(Vector3fc color) {
        mColor = new Vector3f(color);
    }

    void updateShader(Shader shader) {
        shader.setUniform("color", mColor);
        shader.setUniform("useColor", mTexture == null ? 1 : 0);

        if (mTexture != null) {
            shader.setUniform("textureSampler", 0);
        }
    }

    void render() {
        if (mTexture != null) {
            mTexture.bind(0);
        }

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, mVertexObject);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, mTextCoordsObject);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, mNormalsObject);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndexObject);
        glDrawElements(GL_TRIANGLES, mDrawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        if (mTexture != null) {
            mTexture.unbind();
        }
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(mVertexObject);
        glDeleteBuffers(mTextCoordsObject);
        glDeleteBuffers(mNormalsObject);
        glDeleteBuffers(mIndexObject);

        if (mTexture != null) {
            mTexture.close();
        }
    }
}
