package com.github.tomtzook.rendering;

import com.github.tomtzook.math.Transform2;
import com.github.tomtzook.math.Transform3;
import org.joml.Matrix4f;
import org.joml.Vector2fc;

public class HudRendererImpl implements ElementRenderer2 {

    private final Shader mShader;
    private final Matrix4f mObjectView;
    private final Matrix4f mObjectViewTransformFull;
    private final Matrix4f mOrthoView;

    public HudRendererImpl(Shader shader) {
        mShader = shader;
        mObjectView = new Matrix4f();
        mObjectViewTransformFull = new Matrix4f();
        mOrthoView = new Matrix4f();
    }

    void startUse(Vector2fc windowSize) {
        mShader.bind();

        mOrthoView.identity();
        mOrthoView.ortho2D(0, windowSize.x(), windowSize.y(), 0);
    }

    void endUse() {
        mShader.unbind();
    }

    @Override
    public void render(Transform2 transform, Mesh mesh) {
        mObjectView.identity();
        transform.getTransformation(mObjectView);

        mObjectViewTransformFull.identity();
        mOrthoView.mul(mObjectView, mObjectViewTransformFull);

        mShader.setUniform("projection", mObjectViewTransformFull);

        mesh.updateShader(mShader);
        mesh.render();
    }

    @Override
    public void finish() {
        endUse();
    }
}
