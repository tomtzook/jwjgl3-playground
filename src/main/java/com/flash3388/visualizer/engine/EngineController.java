package com.flash3388.visualizer.engine;

public interface EngineController {

    Window getWindow();
    Input getInput();

    void requestClose();
}
