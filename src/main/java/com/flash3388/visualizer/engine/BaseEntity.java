package com.flash3388.visualizer.engine;

import com.flash3388.visualizer.kinematics.Kinematics3;

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
