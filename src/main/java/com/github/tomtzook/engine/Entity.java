package com.github.tomtzook.engine;

import com.github.tomtzook.math.Transform3;
import com.github.tomtzook.rendering.ElementRenderer3;

public interface Entity extends Element {

    Transform3 getTransform();

    void update(EngineController controller, float deltaTime);
    void render(ElementRenderer3 renderer);
}
