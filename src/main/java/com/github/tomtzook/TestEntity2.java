package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Renderer;
import com.jmath.vectors.Vector3;

public class TestEntity2 extends BaseEntity {

    private Mesh mMesh;

    public TestEntity2() {
    }

    @Override
    protected void added(Closer resourceHolder) {
        mMesh = Mesh.cuboid(1, 1, 1);
        resourceHolder.add(mMesh);

        getTransform().move(new Vector3(0, 0, 0));
    }

    @Override
    protected void removed() {

    }

    @Override
    public void update(EngineController controller, double deltaTime) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.render(getTransform(), mMesh);
    }
}
