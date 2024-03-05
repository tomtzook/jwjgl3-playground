package com.github.tomtzook.engine;

import com.github.tomtzook.rendering.Renderer;

import java.util.LinkedList;
import java.util.List;

public class World implements AutoCloseable {

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

    public void removeAll() {
        for (Entity entity : mEntities) {
            entity.onRemove();
        }

        mEntities.clear();
    }

    public void update(EngineController controller, double deltaTime) {
        for (Entity entity : mEntities) {
            entity.update(controller, deltaTime);
        }
    }

    public void render(Renderer renderer) {
        for (Entity entity : mEntities) {
            entity.render(renderer);
        }
    }

    @Override
    public void close() throws Exception {
        removeAll();
    }
}
