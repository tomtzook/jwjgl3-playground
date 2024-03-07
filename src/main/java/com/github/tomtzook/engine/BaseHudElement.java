package com.github.tomtzook.engine;

import com.github.tomtzook.math.Transform2;

public abstract class BaseHudElement extends BaseElement implements HudElement {

    private final Transform2 mTransform;

    protected BaseHudElement() {
        mTransform = new Transform2();
    }

    @Override
    public final Transform2 getTransform() {
        return mTransform;
    }
}
