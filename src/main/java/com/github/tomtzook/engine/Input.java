package com.github.tomtzook.engine;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private final Window mWindow;

    private final double[] mMouseGetX;
    private final double[] mMouseGetY;

    public Input(Window window) {
        mWindow = window;

        mMouseGetX = new double[1];
        mMouseGetY = new double[1];
    }

    public boolean isKeyDown(int key) {
        return glfwGetKey(mWindow.mWindow, key) == GLFW_PRESS;
    }

    public boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(mWindow.mWindow, button) == GLFW_PRESS;
    }

    public Vector2f getMousePosition() {
        glfwGetCursorPos(mWindow.mWindow, mMouseGetX, mMouseGetY);

        Vector2f windowCenter = mWindow.getCenter();

        float x = (float) (mMouseGetX[0] - windowCenter.x());
        float y = (float) -(mMouseGetY[0] - windowCenter.y());

        return new Vector2f(x, y);
    }

    public void setMousePosition(Vector2f position) {
        glfwSetCursorPos(mWindow.mWindow, position.x(), position.y());
    }
}
