package com.github.tomtzook.engine;

import com.github.tomtzook.kinematics.Kinematics3;
import com.github.tomtzook.rendering.Renderer;

import java.util.LinkedList;
import java.util.List;

public class World {

    private final List<Entity> mEntities;

    public World() {
        mEntities = new LinkedList<>();
    }

    public void addEntity(Entity entity) {
        mEntities.add(entity);
        entity.onAdd();
    }

    public void removeEntity(Entity entity) {
        if (mEntities.remove(entity)) {
            entity.onRemove();
        }
    }

    public void update(EngineController controller, double deltaTime) {
        for (Entity entity : mEntities) {
            entity.update(controller, deltaTime);

            Kinematics3 kinematics = entity.getKinematics();
            //kinematics.updateWithTime(deltaTime);
        }
    }

    public void render(Renderer renderer) {
        for (Entity entity : mEntities) {
            entity.render(renderer);
        }
    }
}
