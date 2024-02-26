package com.github.tomtzook.engine;

import com.jmath.vectors.Vector2;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends BaseEntity {

    private final double mMovementSpeed;
    private final double mLookSensitivity;

    private boolean mIsMoseLocked;

    public Camera(double movementSpeed, double lookSensitivity) {
        mMovementSpeed = movementSpeed;
        mLookSensitivity = lookSensitivity;

        mIsMoseLocked = false;
    }

    @Override
    public void update(EngineController controller, double deltaTime) {
        Input input = controller.getInput();
        double moveAmount = mMovementSpeed * deltaTime;

        if (input.isKeyDown(GLFW_KEY_UP)) {
            getTransform().moveForward(moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_DOWN)) {
            getTransform().moveBackward(moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_RIGHT)) {
            getTransform().moveRight(moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_LEFT)) {
            getTransform().moveLeft(moveAmount);
        }

        if (!mIsMoseLocked && input.isMouseButtonDown(GLFW_MOUSE_BUTTON_1)) {
            mIsMoseLocked = true;
        } else if (mIsMoseLocked && input.isKeyDown(GLFW_KEY_ESCAPE)) {
            mIsMoseLocked = false;
        }

        if (mIsMoseLocked) {
            Vector2 windowCenterPosition = controller.getWindow().getCenter();
            Vector2 deltaPos = input.getMousePosition().sub(windowCenterPosition);

            if (deltaPos.y() != 0) {
                getTransform().rotateAroundY(deltaPos.y() * mLookSensitivity);
            }

            if (deltaPos.x() != 0) {
                getTransform().rotate(getTransform().getRotation().right(), -deltaPos.x() * mLookSensitivity);
            }

            input.setMousePosition(windowCenterPosition);
        }
    }

    @Override
    public void render() {

    }
}
