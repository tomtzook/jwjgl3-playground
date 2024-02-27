package com.github.tomtzook.rendering;

import com.github.tomtzook.util.Buffers;
import com.github.tomtzook.util.Resources;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture implements AutoCloseable {

    private final int mTextureObject;
    private final int mWidth;
    private final int mHeight;

    public Texture(ByteBuffer buffer, int width, int height) {
        mTextureObject = glGenTextures();
        mWidth = width;
        mHeight = height;

        glBindTexture(GL_TEXTURE_2D, mTextureObject);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }

    public Texture(BufferedImage image) {
        this(Buffers.fromImage(image), image.getWidth(), image.getHeight());
    }

    public Texture(String filename) {
        this(Resources.readImageOrThrow("/textures/" + filename, "missing texture resource"));
    }

    public void bind(int sampler) {
        if (sampler < 0 || sampler > 31) {
            throw new IllegalArgumentException("bad sampler");
        }

        glActiveTexture(GL_TEXTURE0 + sampler);
        glBindTexture(GL_TEXTURE_2D, mTextureObject);
    }

    @Override
    public void close() throws Exception {
        glDeleteTextures(mTextureObject);
    }
}
