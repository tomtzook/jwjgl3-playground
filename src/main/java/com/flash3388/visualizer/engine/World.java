package com.flash3388.visualizer.engine;

import com.flash3388.visualizer.kinematics.Kinematics3;

import java.util.LinkedList;
import java.util.List;

public class World {

    private final List<Entity> mEntities;

    public World() {
        mEntities = new LinkedList<>();
    }

    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    public void update(EngineController controller, double deltaTime) {
        for (Entity entity : mEntities) {
            entity.update(controller, deltaTime);

            Kinematics3 kinematics = entity.getKinematics();
            kinematics.updateWithTime(deltaTime);
        }
    }

    public void render() {
        for (Entity entity : mEntities) {
            entity.render();
        }
    }
}
