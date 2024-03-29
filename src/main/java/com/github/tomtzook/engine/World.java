package com.github.tomtzook.engine;

import com.github.tomtzook.rendering.ElementRenderer3;

public class World implements AutoCloseable {

    private final ElementContainer<Entity> mContainer;

    public World() {
        mContainer = new ElementContainer<>();
    }

    public void addEntity(Entity entity) {
        mContainer.addElement(entity);
    }

    public void update(EngineController controller, float deltaTime) {
        mContainer.forEach((e)-> e.update(controller, deltaTime));
    }

    public void render(ElementRenderer3 renderer) {
        mContainer.forEach((e)-> e.render(renderer));
    }

    @Override
    public void close() throws Exception {
        mContainer.removeAll();
    }
}
