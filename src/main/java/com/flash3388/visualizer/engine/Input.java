package com.flash3388.visualizer.engine;

import com.jmath.vectors.Vector2;

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

    public Vector2 getMousePosition() {
        glfwGetCursorPos(mWindow.mWindow, mMouseGetX, mMouseGetY);

        Vector2 windowCenter = mWindow.getCenter();

        double x = mMouseGetX[0] - windowCenter.x();
        double y = -(mMouseGetY[0] - windowCenter.y());

        return new Vector2(x, y);
    }

    public void setMousePosition(Vector2 position) {
        glfwSetCursorPos(mWindow.mWindow, position.x(), position.y());
    }
}
