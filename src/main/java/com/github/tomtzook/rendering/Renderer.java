package com.github.tomtzook.rendering;

import com.github.tomtzook.engine.Camera;
import com.github.tomtzook.kinematics.Transform3;
import com.jmath.matrices.Matrix;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private final Camera mMainCamera;
    private final Shader mShader;

    public Renderer(Camera mainCamera, Shader shader) {
        mMainCamera = mainCamera;
        mShader = shader;
    }

    public void startRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        mShader.bind();

        Matrix projection = mMainCamera.getProjection();
        mShader.setUniform("projection", projection);
    }

    public void endRender() {
        mShader.unbind();
    }

    public void render(Transform3 transform, Mesh mesh) {
        Matrix view = mMainCamera.getView();
        Matrix transformation = transform.getTransformation();
        Matrix itemView = view.multiply(transformation);

        mShader.setUniform("transformation", itemView);

        mesh.render();
    }
}
