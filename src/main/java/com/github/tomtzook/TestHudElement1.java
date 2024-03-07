package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.BaseHudElement;
import com.github.tomtzook.engine.EngineController;
import com.github.tomtzook.rendering.ElementRenderer2;
import com.github.tomtzook.rendering.Mesh;
import com.github.tomtzook.rendering.Text;

public class TestHudElement1 extends BaseHudElement {

    private Mesh mMesh;

    @Override
    protected void added(Closer resourceHolder) {
        mMesh = Text.buildMesh("hello", "font2.png", 8, 8);
        resourceHolder.add(mMesh);
    }

    @Override
    protected void removed() {

    }

    @Override
    public void update(EngineController controller, float deltaTime) {

    }

    @Override
    public void render(ElementRenderer2 renderer) {
        renderer.render(getTransform(), mMesh);
    }
}
