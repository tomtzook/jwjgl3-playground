package com.github.tomtzook.math;

import com.jmath.vectors.Vector3;

public class Kinematics3 {

    private final Transform3 mTransform;

    private Vector3 mLinearVelocity;
    private Vector3 mAngularVelocity;
    private Vector3 mLinearAcceleration;
    private Vector3 mAngularAcceleration;

    public Kinematics3(Transform3 transform) {
        mTransform = transform;

        mLinearVelocity = new Vector3();
        mAngularVelocity = new Vector3();
        mLinearAcceleration = new Vector3();
        mAngularAcceleration = new Vector3();
    }

    public Transform3 getTransform() {
        return mTransform;
    }

    public Vector3 getLinearVelocity() {
        return mLinearVelocity;
    }

    public void setLinearVelocity(Vector3 linearVelocity) {
        mLinearVelocity = linearVelocity;
    }

    public Vector3 getAngularVelocity() {
        return mAngularVelocity;
    }

    public void setAngularVelocity(Vector3 angularVelocity) {
        mAngularVelocity = angularVelocity;
    }

    public Vector3 getLinearAcceleration() {
        return mLinearAcceleration;
    }

    public void setLinearAcceleration(Vector3 linearAcceleration) {
        mLinearAcceleration = linearAcceleration;
    }

    public Vector3 getAngularAcceleration() {
        return mAngularAcceleration;
    }

    public void setAngularAcceleration(Vector3 angularAcceleration) {
        mAngularAcceleration = angularAcceleration;
    }

    public void updateWithTime(double deltaTime) {
        Vector3 linearVelocity = mLinearVelocity.add(mLinearAcceleration.multiply(deltaTime));
        Vector3 angularVelocity = mAngularVelocity.add(mAngularAcceleration.multiply(deltaTime));
        Vector3 positionChange = mLinearVelocity.multiply(deltaTime).add(mLinearAcceleration.multiply(deltaTime * deltaTime * 0.5));
        Vector3 rotationChange = mAngularVelocity.multiply(deltaTime).add(mAngularVelocity.multiply(deltaTime * deltaTime * 0.5));

        mLinearVelocity = linearVelocity;
        mAngularVelocity = angularVelocity;

        mTransform.move(positionChange);
        mTransform.rotateAroundAxes(rotationChange);
    }

    @Override
    public String toString() {
        return String.format("Kinematics3{transform=%s, linearVel=%s, angularVel=%s, linearAcc=%s, angularAcc=%s}",
                mTransform, mLinearVelocity, mAngularVelocity, mLinearAcceleration, mAngularAcceleration);
    }
}
