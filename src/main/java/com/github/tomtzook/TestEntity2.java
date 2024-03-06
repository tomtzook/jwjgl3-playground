package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Renderer;
import com.github.tomtzook.rendering.Texture;
import com.github.tomtzook.rendering.obj.ObjLoader;
import com.github.tomtzook.util.Buffers;
import org.joml.Vector3f;

import java.io.IOException;

public class TestEntity2 extends BaseEntity {

    //private final Kinematics3 mKinematics;
    private Mesh mMesh;
    private Texture mTexture;

    public TestEntity2() {
        //mKinematics = new Kinematics3(getTransform());
    }

    @Override
    protected void added(Closer resourceHolder) {
        try {
            mMesh = ObjLoader.loadFromResource("/cube.obj");
            mMesh.setTexture(new Texture("checkers.png"));
            resourceHolder.add(mMesh);
        } catch (IOException e) {
            throw new Error(e);
        }

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
