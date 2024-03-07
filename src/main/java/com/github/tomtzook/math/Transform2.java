package com.github.tomtzook.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform2 {

    private Vector2f mPosition;
    private float mRotation;
    private Vector2f mScale;

    public Transform2() {
        mPosition = new Vector2f();
        mRotation = 0;
        mScale = new Vector2f(1, 1);
    }

    public Vector2f getPosition() {
        return new Vector2f(mPosition);
    }

    public void setPosition(Vector2f position) {
        mPosition = position;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setRotation(float rotation) {
        mRotation = rotation;
    }

    public Vector2f getScale() {
        return mScale;
    }

    public void setScale(Vector2f scale) {
        mScale = scale;
    }

    public void getTransformation(Matrix4f out){
        Vector3f translation = new Vector3f(mPosition.x, mPosition.y, 0);
        Vector3f scale = new Vector3f(mScale.x, mScale.y, 1);

        out.identity()
                .translate(translation)
                .rotateZ((float) Math.toRadians(mRotation))
                .scale(scale);
    }
}
