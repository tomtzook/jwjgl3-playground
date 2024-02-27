package com.github.tomtzook.engine;

import com.github.tomtzook.kinematics.Kinematics3;
import com.github.tomtzook.kinematics.Transform3;
import com.github.tomtzook.rendering.Renderer;

public interface Entity {

    Kinematics3 getKinematics();
    Transform3 getTransform();

     void onAdd();
     void onRemove();

    void update(EngineController controller, double deltaTime);
    void render(Renderer renderer);
}
