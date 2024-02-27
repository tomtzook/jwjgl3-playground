package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseEntity;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Renderer;
import com.github.tomtzook.util.AdditionalMath;
import com.jmath.vectors.Vector3;

public class TestEntity2 extends BaseEntity {

    private Mesh mMesh;

    public TestEntity2() {
    }

    @Override
    protected void added(Closer resourceHolder) {
        mMesh = Mesh.cuboid(1, 1, 1);
        /*float[] positions = new float[]{
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        mMesh = new Mesh(positions, colours, indices);*/

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
