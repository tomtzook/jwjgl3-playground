package com.github.tomtzook.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Transform3 {

    private Vector3f mPosition;
    private Quaternionf mRotation;
    private Vector3f mScale;

    public Transform3() {
        mPosition = new Vector3f();
        mRotation = new Quaternionf(0, 0, 0, 1);
        mScale = new Vector3f(1, 1, 1);
    }

    public Vector3f getPosition() {
        return new Vector3f(mPosition);
    }

    public void setPosition(Vector3f position) {
        mPosition = position;
    }

    public Quaternionf getRotation() {
        return new Quaternionf(mRotation);
    }

    public void setRotation(Quaternionf rotation) {
        mRotation = rotation;
    }

    public Vector3f getScale() {
        return new Vector3f(mScale);
    }

    public void setScale(Vector3f scale) {
        mScale = scale;
    }

    public void copy(Transform3 other) {
        mPosition = new Vector3f(other.mPosition);
        mRotation = new Quaternionf(other.mRotation);
        mScale = new Vector3f(other.mScale);
    }

    public void move(Vector3fc motionVector) {
        mPosition.add(motionVector);
    }

    public void move(Vector3fc direction, float distance) {
        direction.mulAdd(distance, mPosition, mPosition);
    }

    public void rotate(Vector3fc axis, float angle) {
        mRotation.rotateAxis((float) Math.toRadians(angle), axis);
    }

    public void rotateAroundX(float angle) {
        rotate(Axes.X, angle);
    }

    public void rotateAroundY(float angle) {
        rotate(Axes.Y, angle);
    }

    public void rotateAroundZ(float angle) {
        rotate(Axes.Z, angle);
    }

    public void rotateAroundAxes(Vector3fc rotation) {
        rotateAroundX(rotation.x());
        rotateAroundY(rotation.y());
        rotateAroundZ(rotation.z());
    }

    public void getTransformation(Matrix4f out){
        out.identity()
                .translate(mPosition)
                .rotate(new Quaternionf(mRotation).conjugate())
                .scale(mScale);
    }

    @Override
    public String toString() {
        return String.format("Transform3d{position=%s, rotation=%s, scale=%s}",
                mPosition, mRotation, mScale);
    }
}
