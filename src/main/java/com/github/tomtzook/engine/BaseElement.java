package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;

public abstract class BaseElement implements Element {

    private Closer mCurrentResourceHolder;

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
