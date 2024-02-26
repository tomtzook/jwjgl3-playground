package com.github.tomtzook.engine;

import com.github.tomtzook.kinematics.Kinematics3;

public abstract class BaseEntity implements Entity {

    private final Kinematics3 mKinematics;

    public BaseEntity() {
        mKinematics = new Kinematics3();
    }

    @Override
    public Kinematics3 getKinematics() {
        return mKinematics;
    }
}
