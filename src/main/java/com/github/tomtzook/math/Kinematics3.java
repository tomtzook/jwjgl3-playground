package com.github.tomtzook.math;

import org.joml.Vector3f;

public class Kinematics3 {

    private final Transform3 mTransform;

    private Vector3f mLinearVelocity;
    private Vector3f mAngularVelocity;
    private Vector3f mLinearAcceleration;
    private Vector3f mAngularAcceleration;

    public Kinematics3(Transform3 transform) {
        mTransform = transform;

        mLinearVelocity = new Vector3f();
        mAngularVelocity = new Vector3f();
        mLinearAcceleration = new Vector3f();
        mAngularAcceleration = new Vector3f();
    }

    public Transform3 getTransform() {
        return mTransform;
    }

    public Vector3f getLinearVelocity() {
        return mLinearVelocity;
    }

    public void setLinearVelocity(Vector3f linearVelocity) {
        mLinearVelocity = linearVelocity;
    }

    public Vector3f getAngularVelocity() {
        return mAngularVelocity;
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        mAngularVelocity = angularVelocity;
    }

    public Vector3f getLinearAcceleration() {
        return mLinearAcceleration;
    }

    public void setLinearAcceleration(Vector3f linearAcceleration) {
        mLinearAcceleration = linearAcceleration;
    }

    public Vector3f getAngularAcceleration() {
        return mAngularAcceleration;
    }

    public void setAngularAcceleration(Vector3f angularAcceleration) {
        mAngularAcceleration = angularAcceleration;
    }

    public void updateWithTime(float deltaTime) {
        float squardHalfedDT = deltaTime * deltaTime * 0.5f;
        
        // calc positioning changes
        Vector3f positionChange = new Vector3f();
        mLinearVelocity.mul(deltaTime, positionChange);
        mLinearAcceleration.mulAdd(squardHalfedDT, positionChange, positionChange);
        
        Vector3f rotationChange = new Vector3f();
        mAngularVelocity.mul(deltaTime, rotationChange);
        mAngularAcceleration.mulAdd(squardHalfedDT, rotationChange, rotationChange);
        
        // calc velocity changes
        mLinearAcceleration.mulAdd(deltaTime, mLinearVelocity, mLinearVelocity);
        mAngularAcceleration.mulAdd(deltaTime, mAngularVelocity, mAngularVelocity);
        
        mTransform.move(positionChange);
        mTransform.rotateAroundAxes(rotationChange);
    }

    @Override
    public String toString() {
        return String.format("Kinematics3{transform=%s, linearVel=%s, angularVel=%s, linearAcc=%s, angularAcc=%s}",
                mTransform, mLinearVelocity, mAngularVelocity, mLinearAcceleration, mAngularAcceleration);
    }
}
