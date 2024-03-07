package com.github.tomtzook.rendering;

import com.github.tomtzook.engine.Camera;
import org.joml.Vector2fc;

import static org.lwjgl.opengl.GL11.*;

public class Renderer implements AutoCloseable {
    // TODO: BETTER API
    //      REFLECT LIMITED STUFF TO USE (NOT START-END)
    //      PROPERLY SEPARATE, BUT DON'T DUPLICATE HUD AND NORMAL

    private final Vector2fc mWindowSize;
    private final Camera mMainCamera;
    private final Shader mShader;
    private final Shader mHudShader;

    private final EntityRendererImpl mEntityRenderer;
    private final HudRendererImpl mHudRenderer;

    public Renderer(Vector2fc windowSize, Camera mainCamera, Shader shader, Shader hudShader) {
        mWindowSize = windowSize;
        mMainCamera = mainCamera;
        mShader = shader;
        mHudShader = hudShader;

        mEntityRenderer = new EntityRendererImpl(shader);
        mHudRenderer = new HudRendererImpl(hudShader);
    }

    public void startRendering() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, (int) mWindowSize.x(), (int) mWindowSize.y());
    }

    public ElementRenderer3 startObjectRendering() {
        mEntityRenderer.startUse(mMainCamera);
        return mEntityRenderer;
    }

    public ElementRenderer2 startHudRendering() {
        mHudRenderer.startUse(mWindowSize);
        return mHudRenderer;
    }

    @Override
    public void close() throws Exception {
        mShader.close();
        mHudShader.close();
    }
}
