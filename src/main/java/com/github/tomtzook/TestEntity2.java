package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.math.Kinematics3;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Renderer;
import com.jmath.vectors.Vector3;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;

public class TestEntity2 extends BaseEntity {

    //private final Kinematics3 mKinematics;
    private Mesh mMesh;

    public TestEntity2() {
        //mKinematics = new Kinematics3(getTransform());
    }

    @Override
    protected void added(Closer resourceHolder) {
        mMesh = Mesh.cuboid(1, 1, 1);
        resourceHolder.add(mMesh);

        getTransform().move(new Vector3f(0, 0, -2));
        //mKinematics.setLinearVelocity(new Vector3(0, 0, 0));
        //mKinematics.setLinearAcceleration(new Vector3(0.01, 0, 0));
    }

    @Override
    protected void removed() {

    }

    @Override
    public void update(EngineController controller, float deltaTime) {
        //mKinematics.updateWithTime(deltaTime);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.render(getTransform(), mMesh);
    }
}
