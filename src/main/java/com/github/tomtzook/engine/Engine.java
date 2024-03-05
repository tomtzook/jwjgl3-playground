package com.github.tomtzook.engine;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.rendering.Renderer;
import com.github.tomtzook.rendering.Shader;
import com.github.tomtzook.util.AdditionalMath;
import com.github.tomtzook.util.Timer;
import com.jmath.matrices.Matrix;
import com.jmath.vectors.Vector2;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class Engine implements AutoCloseable {

    private static final double FPS = 60.0;

    private final Window mWindow;
    private final World mWorld;
    private final EngineControllerImpl mController;
    private final Renderer mRenderer;
    private final double mFrameTime;
    private boolean mShouldRun;

    public Engine(Window window, Input input) {
        mWindow = window;

        mWindow.show();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);

        mWorld = new World();
        mController = new EngineControllerImpl(window, input);

        Vector2 windowSize = mWindow.getSize();
        Matrix projection = AdditionalMath.perspectiveMatrix3d(
                60,
                windowSize.x() / windowSize.y(),
                0.01f,
                1000.0f
        );

        Camera camera = new Camera(
                projection,
                1,
                0.5
        );
        Shader shader = new Shader("shader");
        mRenderer = new Renderer(camera, shader);
        addEntity(camera);

        mFrameTime = 1.0 / FPS;
        mShouldRun = true;
    }

    public void addEntity(Entity entity) {
        mWorld.addEntity(entity);
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
                mRenderer.startRender();
                try {
                    mWorld.render(mRenderer);
                } finally {
                    mRenderer.endRender();
                }

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
    }
}
