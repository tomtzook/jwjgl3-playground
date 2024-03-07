package com.github.tomtzook.engine;

import com.github.tomtzook.rendering.ElementRenderer2;

public class Hud implements AutoCloseable {

    private final ElementContainer<HudElement> mContainer;

    public Hud() {
        mContainer = new ElementContainer<>();
    }

    public void addElement(HudElement element) {
        mContainer.addElement(element);
    }

    public void update(EngineController controller, float deltaTime) {
        mContainer.forEach((e)-> e.update(controller, deltaTime));
    }

    public void render(ElementRenderer2 renderer) {
        mContainer.forEach((e)-> e.render(renderer));
    }

    @Override
    public void close() throws Exception {
        mContainer.removeAll();
    }
}
