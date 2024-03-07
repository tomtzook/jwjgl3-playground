package com.github.tomtzook.engine;

import com.github.tomtzook.math.Transform2;
import com.github.tomtzook.rendering.ElementRenderer2;

public interface HudElement extends Element {

    Transform2 getTransform();

    void update(EngineController controller, float deltaTime);
    void render(ElementRenderer2 renderer);
}
