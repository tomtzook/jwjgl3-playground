package com.github.tomtzook;

import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.kinematics.Axes;

public class TestEntity extends BaseEntity {

    @Override
    public void update(EngineController controller, double deltaTime) {
        getKinematics().setLinearAcceleration(Axes.X.multiply(1));
    }

    @Override
    public void render() {

    }
}
