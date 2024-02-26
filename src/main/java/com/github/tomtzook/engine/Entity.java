package com.github.tomtzook.engine;

import com.github.tomtzook.kinematics.Kinematics3;
import com.github.tomtzook.kinematics.Transform3;

public interface Entity {

    Kinematics3 getKinematics();

     default Transform3 getTransform() {
         return getKinematics().getTransform();
     }

    void update(EngineController controller, double deltaTime); // TODO: NEED TO PASS SOME CONTROL INTERFACES
    void render(); // TODO: NEED TO PASS SHADER AND RENDERING INTERFACE
}
