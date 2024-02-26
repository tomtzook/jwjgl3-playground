package com.flash3388.visualizer.kinematics;

import com.jmath.quaternion.Quaternion;
import com.jmath.vectors.Vector3;

public class Transform3 {

    private Vector3 mPosition;
    private Quaternion mRotation;

    public Transform3() {
        mPosition = new Vector3();
        mRotation = new Quaternion();
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public Quaternion getRotation() {
        return mRotation;
    }

    public void move(Vector3 motionVector) {
        mPosition.add(motionVector);
    }

    public void move(Vector3 direction, double distance) {
        mPosition = mPosition.add(direction.multiply(distance));
    }

    public void moveForward(double distance) {
        move(mRotation.forward(), distance);
    }

    public void moveBackward(double distance) {
        move(mRotation.back(), distance);
    }

    public void moveRight(double distance) {
        move(mRotation.right(), distance);
    }

    public void moveLeft(double distance) {
        move(mRotation.left(), distance);
    }

    public void moveUp(double distance) {
        move(mRotation.up(), distance);
    }

    public void moveDown(double distance) {
        move(mRotation.down(), distance);
    }

    public void rotate(Vector3 axis, double angle) {
        mRotation = Quaternion.rotationOnAxis(axis, angle)
                .multiply(mRotation)
                .normalized();
    }

    public void rotateAroundX(double angle) {
        rotate(Axes.X, angle);
    }

    public void rotateAroundY(double angle) {
        rotate(Axes.Y, angle);
    }

    public void rotateAroundZ(double angle) {
        rotate(Axes.Z, angle);
    }

    public void rotateAroundAxes(Vector3 rotation) {
        rotateAroundX(rotation.x());
        rotateAroundY(rotation.y());
        rotateAroundZ(rotation.z());
    }

    @Override
    public String toString() {
        return String.format("Transform3d{position=%s, rotation=%s}",
                mPosition, mRotation);
    }
}
