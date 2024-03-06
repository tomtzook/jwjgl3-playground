package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.rendering.Renderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends BaseEntity {

    private final Matrix4f mProjection;
    private final float mMovementSpeed;
    private final float mLookSensitivity;

    private boolean mIsMoseLocked;

    public Camera(Matrix4f projection, float movementSpeed, float lookSensitivity) {
        mProjection = projection;
        mMovementSpeed = movementSpeed;
        mLookSensitivity = lookSensitivity;

        mIsMoseLocked = false;
    }

    public Camera(
            float fov, float aspectRatio, float zNear, float zFar,
            float movementSpeed, float lookSensitivity) {
        this(new Matrix4f().perspective(fov, aspectRatio, zNear, zFar), movementSpeed, lookSensitivity);
    }

    public Camera(
            float fov, Vector2f windowSize, float zNear, float zFar,
            float movementSpeed, float lookSensitivity) {
        this(fov, windowSize.x / windowSize.y, zNear, zFar, movementSpeed, lookSensitivity);
    }

    public Matrix4fc getProjection() {
        return mProjection;
    }

    public void getView(Matrix4f out) {
        Vector3f position = getTransform().getPosition();
        Quaternionf rotation = getTransform().getRotation();

        out.identity()
                .rotate(rotation)
                .translate(position.negate());
    }

    public Vector3f getForward() {
        return new Vector3f(0, 0, -1).rotate(new Quaternionf(getTransform().getRotation()).conjugate());
    }

    public Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(new Quaternionf(getTransform().getRotation()).conjugate());
    }

    public Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(new Quaternionf(getTransform().getRotation()).conjugate());
    }

    @Override
    public void update(EngineController controller, float deltaTime) {
        Input input = controller.getInput();
        float moveAmount = mMovementSpeed * deltaTime;

        if (input.isKeyDown(GLFW_KEY_UP)) {
            getTransform().move(getForward(), moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_DOWN)) {
            getTransform().move(getForward(), -moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_RIGHT)) {
            getTransform().move(getLeft(), moveAmount);
        }
        if (input.isKeyDown(GLFW_KEY_LEFT)) {
            getTransform().move(getLeft(), -moveAmount);
        }

        if (!mIsMoseLocked && input.isMouseButtonDown(GLFW_MOUSE_BUTTON_1)) {
            mIsMoseLocked = true;
        } else if (mIsMoseLocked && input.isKeyDown(GLFW_KEY_ESCAPE)) {
            mIsMoseLocked = false;
        }

        if (mIsMoseLocked) {
            Vector2f windowCenterPosition = controller.getWindow().getCenter();
            Vector2f deltaPos = input.getMousePosition();

            // left-right
            if (deltaPos.x() != 0) {
                getTransform().rotate(
                        getUp(),
                        -deltaPos.x() * mLookSensitivity);
            }

            // up-down
            if (deltaPos.y() != 0) {
                getTransform().rotate(
                        getLeft(),
                        -deltaPos.y() * mLookSensitivity);
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
