package com.flash3388.visualizer.engine;

public class WindowEntity extends BaseEntity {

    @Override
    public void update(EngineController controller, double deltaTime) {
        Window window = controller.getWindow();
        if (window.shouldClose()) {
            controller.requestClose();
        }
    }

    @Override
    public void render() {

    }
}
