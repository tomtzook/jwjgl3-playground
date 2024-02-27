package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.rendering.Renderer;
import com.github.tomtzook.util.AdditionalMath;
import com.jmath.matrices.Matrices;
import com.jmath.matrices.Matrix;
import com.jmath.quaternion.Quaternion;
import com.jmath.vectors.Vector2;
import com.jmath.vectors.Vector3;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends BaseEntity {

    private final Matrix mProjection;
    private final double mMovementSpeed;
    private final double mLookSensitivity;

    private boolean mIsMoseLocked;

    public Camera(Matrix projection, double movementSpeed, double lookSensitivity) {
        mProjection = projection;
        mMovementSpeed = movementSpeed;
        mLookSensitivity = lookSensitivity;

        mIsMoseLocked = false;
    }

    public Matrix getProjection() {
        return mProjection;
    }

    public Matrix getView() {
        Vector3 position = getTransform().getPosition().multiply(-1);
        Quaternion rotation = getTransform().getRotation();

        // TODO: USE QUATERNION MATH
        Matrix translationMat = Matrices.translation3d(position.x(), position.y(), position.z());
        Matrix rotationMat = AdditionalMath.toRotationMatrix(rotation);

        return rotationMat.multiply(translationMat);
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
            Vector2 deltaPos = input.getMousePosition();

            if (deltaPos.x() != 0) {
                getTransform().rotateAroundY(deltaPos.x() * mLookSensitivity);
            }

            if (deltaPos.y() != 0) {
                getTransform().rotateAroundX(deltaPos.y() * mLookSensitivity);
            }

            input.setMousePosition(windowCenterPosition);
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    protected void added(Closer resourceHolder) {

    }

    @Override
    protected void removed() {

    }
}
