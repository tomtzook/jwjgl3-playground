package com.github.tomtzook.engine;

import com.github.tomtzook.math.Transform3;

public abstract class BaseEntity extends BaseElement implements Entity {

    private final Transform3 mTransform;

    public BaseEntity() {
        mTransform = new Transform3();
    }

    @Override
    public final Transform3 getTransform() {
        return mTransform;
    }
}
