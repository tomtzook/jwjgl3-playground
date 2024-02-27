package com.github.tomtzook.rendering;

import com.github.tomtzook.util.AdditionalMath;
import com.github.tomtzook.util.Buffers;
import com.jmath.vectors.Vector3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh implements AutoCloseable {

    private final int mDrawCount;
    private final int mVertexObject;
    private final int mColorsObject;
    private final int mIndexObject;

    public Mesh(FloatBuffer positions, FloatBuffer colors, IntBuffer indices) {
        mDrawCount = indices.limit();

        mVertexObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mVertexObject);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);

        mColorsObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mColorsObject);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);

        mIndexObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndexObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public Mesh(float[] positions, float[] colors, int[] indices) {
        this(Buffers.fromArray(positions), Buffers.fromArray(colors), Buffers.fromArray(indices));
    }

    public static Mesh cuboid(double width, double height, double length) {
        Vector3[] vertices = AdditionalMath.cuboidVertices(width, height, length);
        FloatBuffer verticesBuffer = AdditionalMath.verticesToBuffer(vertices);

        float[] colors = {
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        int[] indices = {
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            4, 0, 3, 5, 4, 3,
            // Right face
            3, 2, 7, 5, 3, 7,
            // Left face
            6, 1, 0, 6, 0, 4,
            // Bottom face
            2, 1, 6, 2, 6, 7,
            // Back face
            7, 6, 4, 7, 4, 5
        };

        return new Mesh(verticesBuffer, Buffers.fromArray(colors), Buffers.fromArray(indices));
    }

    void render() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, mVertexObject);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, mColorsObject);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndexObject);
        glDrawElements(GL_TRIANGLES, mDrawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    @Override
    public void close() throws Exception {
        glDeleteBuffers(mVertexObject);
        glDeleteBuffers(mColorsObject);
        glDeleteBuffers(mIndexObject);
    }
}
