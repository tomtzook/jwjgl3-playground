package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.math.Kinematics3;
import com.github.tomtzook.rendering.ElementRenderer3;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Texture;
import com.github.tomtzook.rendering.obj.ObjLoader;

import java.io.IOException;

public class TestEntity2 extends BaseEntity {

    private final Kinematics3 mKinematics;
    private Mesh mMesh;

    public TestEntity2() {
        mKinematics = new Kinematics3(getTransform());
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
    }

    @Override
    protected void removed() {

    }

    @Override
    public void update(EngineController controller, float deltaTime) {
        mKinematics.updateWithTime(deltaTime);
    }

    @Override
    public void render(ElementRenderer3 renderer) {
        renderer.render(getTransform(), mMesh);
    }
}
