package com.github.tomtzook.engine;

import com.github.tomtzook.util.Timer;

public class Engine {

    private static final double FPS = 60.0;

    private final World mWorld;
    private final EngineControllerImpl mController;
    private final double mFrameTime;
    private boolean mShouldRun;

    public Engine(Window window, Input input) {
        mWorld = new World();
        mController = new EngineControllerImpl(window, input);

        mFrameTime = 1.0 / FPS;
        mShouldRun = true;

        mWorld.addEntity(new WindowEntity());
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

                mWorld.update(mController, mFrameTime);
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
                mWorld.render();
                frames++;
            } else {
                Timer.delay(1);
            }
        }
    }
}
