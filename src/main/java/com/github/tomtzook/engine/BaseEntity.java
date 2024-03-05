package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.math.Transform3;

public abstract class BaseEntity implements Entity {

    private final Transform3 mTransform;
    private Closer mCurrentResourceHolder;

    public BaseEntity() {
        mTransform = new Transform3();
    }

    @Override
    public final Transform3 getTransform() {
        return mTransform;
    }

    @Override
    public final void onAdd() {
        mCurrentResourceHolder = Closer.empty();
        added(mCurrentResourceHolder);
    }

    @Override
    public final void onRemove() {
        if (mCurrentResourceHolder != null) {
            try {
                mCurrentResourceHolder.close();
            } catch (Throwable ignore) {}

            mCurrentResourceHolder = null;
        }
    }

    protected abstract void added(Closer resourceHolder);
    protected abstract void removed();
}
