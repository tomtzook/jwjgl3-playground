package com.github.tomtzook.engine;

public interface EngineController {

    Window getWindow();
    Input getInput();

    void requestClose();
}
