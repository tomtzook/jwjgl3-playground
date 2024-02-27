package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.kinematics.Axes;
import com.github.tomtzook.rendering.Renderer;

public class TestEntity extends BaseEntity {

    @Override
    public void update(EngineController controller, double deltaTime) {
        getKinematics().setLinearAcceleration(Axes.X.multiply(1));
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    protected void added(Closer resourceHolder) {

    }

    @Override
    protected void removed() {

    }
}
