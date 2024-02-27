package com.github.tomtzook.engine;

import com.jmath.vectors.Vector2;

import static org.lwjgl.glfw.GLFW.*;

public class Window implements AutoCloseable {

    final long mWindow;

    private final int[] mWindowGetWidth;
    private final int[] mWindowGetHeight;

    public Window(long window) {
        mWindow = window;
        mWindowGetWidth = new int[1];
        mWindowGetHeight = new int[1];
    }

    public static Window create(int width, int height, String title) {
        long window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new Error("failed to create window");
        }

        return new Window(window);
    }

    public void show() {
        glfwShowWindow(mWindow);
        glfwMakeContextCurrent(mWindow);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(mWindow);
    }

    public void swapBuffers() {
        glfwSwapBuffers(mWindow);
    }

    public Vector2 getCenter() {
        Vector2 size = getSize();
        return size.multiply(0.5);
    }

    public Vector2 getSize() {
        glfwGetWindowSize(mWindow, mWindowGetWidth, mWindowGetHeight);
        return new Vector2(mWindowGetWidth[0], mWindowGetHeight[0]);
    }

    @Override
    public void close() throws Exception {
        glfwDestroyWindow(mWindow);
    }
}
