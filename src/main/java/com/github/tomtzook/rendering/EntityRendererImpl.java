package com.github.tomtzook.rendering;

import com.github.tomtzook.engine.Camera;
import com.github.tomtzook.math.Transform3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class EntityRendererImpl implements ElementRenderer3 {

    private final Shader mShader;

    private final Matrix4f mCameraView;
    private final Matrix4f mObjectView;
    private final Matrix4f mObjectViewTransformFull;

    public EntityRendererImpl(Shader shader) {
        mShader = shader;
        mCameraView = new Matrix4f();
        mObjectView = new Matrix4f();
        mObjectViewTransformFull = new Matrix4f();
    }

    void startUse(Camera camera) {
        mShader.bind();

        Matrix4fc projection = camera.getProjection();
        mShader.setUniform("projection", projection);

        mCameraView.identity();
        camera.getView(mCameraView);
    }

    void endUse() {
        mShader.unbind();
    }

    @Override
    public void render(Transform3 transform, Mesh mesh) {
        mObjectView.identity();
        transform.getTransformation(mObjectView);

        mObjectViewTransformFull.identity();
        mCameraView.mul(mObjectView, mObjectViewTransformFull);

        mShader.setUniform("transformation", mObjectViewTransformFull);

        mesh.updateShader(mShader);
        mesh.render();
    }

    @Override
    public void finish() {
        endUse();
    }
}
