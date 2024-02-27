package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.kinematics.Kinematics3;
import com.github.tomtzook.kinematics.Transform3;

public abstract class BaseEntity implements Entity {

    private final Kinematics3 mKinematics;
    private Closer mCurrentResourceHolder;

    public BaseEntity() {
        mKinematics = new Kinematics3();
    }

    @Override
    public final Kinematics3 getKinematics() {
        return mKinematics;
    }

    @Override
    public final Transform3 getTransform() {
        return mKinematics.getTransform();
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
