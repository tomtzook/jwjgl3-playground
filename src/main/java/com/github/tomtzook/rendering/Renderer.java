package com.github.tomtzook.rendering;

import com.github.tomtzook.engine.Camera;
import com.github.tomtzook.math.Transform3;
import com.jmath.matrices.Matrix;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

import static org.lwjgl.opengl.GL11.*;

public class Renderer implements AutoCloseable {

    private final Camera mMainCamera;
    private final Shader mShader;
    
    private final Matrix4f mCameraView;
    private final Matrix4f mObjectView;
    private final Matrix4f mObjectViewTransformFull;

    public Renderer(Camera mainCamera, Shader shader) {
        mMainCamera = mainCamera;
        mShader = shader;
        
        mCameraView = new Matrix4f();
        mObjectView = new Matrix4f();
        mObjectViewTransformFull = new Matrix4f();
    }

    public void startRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        mShader.bind();

        Matrix4fc projection = mMainCamera.getProjection();
        mShader.setUniform("projection", projection);

        mCameraView.identity();
        mMainCamera.getView(mCameraView);
    }

    public void endRender() {
        mShader.unbind();
    }

    public void render(Transform3 transform, Mesh mesh) {
        mObjectView.identity();
        transform.getTransformation(mObjectView);

        mObjectViewTransformFull.identity();
        mCameraView.mul(mObjectView, mObjectViewTransformFull);

        mShader.setUniform("transformation", mObjectViewTransformFull);

        mesh.render();
    }

    @Override
    public void close() throws Exception {
        mShader.close();
    }
}
