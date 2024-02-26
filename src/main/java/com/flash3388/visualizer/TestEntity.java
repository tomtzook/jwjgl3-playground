package com.flash3388.visualizer;

import com.flash3388.visualizer.engine.BaseEntity;
import com.flash3388.visualizer.engine.EngineController;
import com.flash3388.visualizer.kinematics.Axes;

public class TestEntity extends BaseEntity {

    @Override
    public void update(EngineController controller, double deltaTime) {
        getKinematics().setLinearAcceleration(Axes.X.multiply(1));
    }

    @Override
    public void render() {

    }
}
