package com.github.tomtzook.math;

import com.github.tomtzook.util.AdditionalMath;
import com.jmath.matrices.Matrices;
import com.jmath.matrices.Matrix;
import com.jmath.quaternion.Quaternion;
import com.jmath.vectors.Vector3;

public class Transform3 {

    private Vector3 mPosition;
    private Quaternion mRotation;
    private Vector3 mScale;

    public Transform3() {
        mPosition = new Vector3();
        mRotation = new Quaternion(0, 0, 0, 1);
        mScale = new Vector3(1, 1, 1);
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        mPosition = position;
    }

    public Quaternion getRotation() {
        return mRotation;
    }

    public void setRotation(Quaternion rotation) {
        mRotation = rotation;
    }

    public Vector3 getScale() {
        return mScale;
    }

    public void setScale(Vector3 scale) {
        mScale = scale;
    }

    public void copy(Transform3 other) {
        mPosition = other.mPosition;
        mRotation = other.mRotation;
        mScale = other.mScale;
    }

    public Vector3 forward() {
        return Axes.Z.rotate(mRotation);
    }

    public Vector3 backward() {
        return Axes.Z.multiply(-1).rotate(mRotation);
    }

    public Vector3 right() {
        return Axes.X.rotate(mRotation);
    }

    public Vector3 left() {
        return Axes.X.multiply(-1).rotate(mRotation);
    }

    public Vector3 up() {
        return Axes.Y.rotate(mRotation);
    }

    public Vector3 down() {
        return Axes.Y.multiply(-1).rotate(mRotation);
    }

    public void move(Vector3 motionVector) {
        mPosition = mPosition.add(motionVector);
    }

    public void move(Vector3 direction, double distance) {
        mPosition = mPosition.add(direction.multiply(distance));
    }

    public void moveForward(double distance) {
        move(forward(), distance);
    }

    public void moveBackward(double distance) {
        move(backward(), distance);
    }

    public void moveRight(double distance) {
        move(right(), distance);
    }

    public void moveLeft(double distance) {
        move(left(), distance);
    }

    public void moveUp(double distance) {
        move(up(), distance);
    }

    public void moveDown(double distance) {
        move(down(), distance);
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

    public Matrix getTransformation(){
        Matrix translationMatrix = Matrices.translation3d(mPosition.x(), mPosition.y(), mPosition.z());
        Matrix rotationMatrix = AdditionalMath.toRotationMatrix(mRotation.conjugate());
        Matrix scaleMatrix = Matrices.scaling3d(mScale.x(), mScale.y(), mScale.z());

        return translationMatrix.multiply(rotationMatrix).multiply(scaleMatrix);
    }

    @Override
    public String toString() {
        return String.format("Transform3d{position=%s, rotation=%s, scale=%s}",
                mPosition, mRotation, mScale);
    }
}
