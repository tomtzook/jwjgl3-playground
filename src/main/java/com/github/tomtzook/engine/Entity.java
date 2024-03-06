package com.github.tomtzook.engine;

import com.github.tomtzook.math.Transform3;
import com.github.tomtzook.rendering.Renderer;

public interface Entity {

    Transform3 getTransform();

     void onAdd();
     void onRemove();

    void update(EngineController controller, float deltaTime);
    void render(Renderer renderer);
}
