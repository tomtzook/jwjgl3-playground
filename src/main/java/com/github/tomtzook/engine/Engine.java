package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.rendering.ElementRenderer2;
import com.github.tomtzook.rendering.ElementRenderer3;
import com.github.tomtzook.rendering.Renderer;
import com.github.tomtzook.rendering.Shader;
import com.github.tomtzook.util.Timer;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;

public class Engine implements AutoCloseable {

    private static final float FPS = 60.0f;

    private final Window mWindow;
    private final World mWorld;
    private final Hud mHud;
    private final EngineControllerImpl mController;
    private final Renderer mRenderer;
    private final float mFrameTime;
    private boolean mShouldRun;

    public Engine(Window window, Input input) {
        mWindow = window;

        mWindow.show();
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);

        mWorld = new World();
        mHud = new Hud();
        mController = new EngineControllerImpl(window, input);

        Camera camera = new Camera(
                30,
                mWindow.getSize(),
                0.01f,
                100.0f,
                1f,
                0.5f
        );
        Shader shader = new Shader("shader");
        Shader hudShader = new Shader("hud");
        mRenderer = new Renderer(window.getSize(), camera, shader, hudShader);
        addEntity(camera);

        mFrameTime = 1.0f / FPS;
        mShouldRun = true;
    }

    public void addEntity(Entity entity) {
        mWorld.addEntity(entity);
    }

    public void addHudElement(HudElement element) {
        mHud.addElement(element);
    }

    public void run() {
        double lastTime = Timer.currentTimeSeconds();
        double unprocessedTime = 0;

        int frames = 0;
        double frameCounter = 0;

        while (mShouldRun) {
            boolean render = false;

            double startTime = Timer.currentTimeSeconds();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;

            while (unprocessedTime >= mFrameTime) {
                render = true;
                unprocessedTime -= mFrameTime;

                update();

                if (mController.isCloseRequested()) {
                    mShouldRun = false;
                    break;
                }

                if(frameCounter >= 1.0){
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                render();

                frames++;

                mWindow.swapBuffers();
            } else {
                // TODO: IS NEEDED
                Timer.delay(1);
            }
        }
    }

    @Override
    public void close() throws Exception {
        Closer closer = Closer.empty();
        closer.add(mWorld);
        closer.add(mRenderer);

        closer.close();
    }

    private void update() {
        glfwPollEvents();

        mWorld.update(mController, mFrameTime);
        mHud.update(mController, mFrameTime);
    }

    private void render() {
        mRenderer.startRendering();


        ElementRenderer3 entityRenderer = mRenderer.startObjectRendering();
        try {
            mWorld.render(entityRenderer);
        } finally {
            entityRenderer.finish();
        }

        ElementRenderer2 hudRenderer = mRenderer.startHudRendering();
        try {
            mHud.render(hudRenderer);
        } finally {
            hudRenderer.finish();
        }
    }
}
