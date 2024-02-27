package com.github.tomtzook.util;

import com.jmath.matrices.Matrix;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffers {

    private Buffers() {}

    public static FloatBuffer fromMat(Matrix matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(matrix.rows() * matrix.cols());

        // because of the representation of matrices in opengl, we rotate the matrix
        // positioning
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                double value = matrix.get(j, i);
                buffer.put((float) value);
            }
        }

        buffer.flip();
        return buffer;
    }

    public static ByteBuffer fromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] raw = image.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer buffer = BufferUtils.createByteBuffer(raw.length);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = raw[i * width + j];
                // in order, rgba
                buffer.put((byte) ((pixel >> 16) & 0xff));
                buffer.put((byte) ((pixel >> 8) & 0xff));
                buffer.put((byte) (pixel & 0xff));
                buffer.put((byte) ((pixel >> 24) & 0xff));
            }
        }

        buffer.flip();
        return buffer;
    }

    public static FloatBuffer fromArray(float[] arr) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(arr.length);
        buffer.put(arr);
        buffer.flip();

        return buffer;
    }

    public static IntBuffer fromArray(int[] arr) {
        IntBuffer buffer = BufferUtils.createIntBuffer(arr.length);
        buffer.put(arr);
        buffer.flip();

        return buffer;
    }
}
