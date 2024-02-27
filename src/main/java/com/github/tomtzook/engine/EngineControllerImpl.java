package com.github.tomtzook.engine;

public class EngineControllerImpl implements EngineController {

    private final Window mWindow;
    private final Input mInput;
    private boolean mIsCloseRequested;

    public EngineControllerImpl(Window window, Input input) {
        mWindow = window;
        mInput = input;
        mIsCloseRequested = false;
    }

    public boolean isCloseRequested() {
        if (Thread.interrupted()) {
            mIsCloseRequested = true;
            Thread.currentThread().interrupt();
        }
        if (mWindow.shouldClose()) {
            mIsCloseRequested = true;
        }

        return mIsCloseRequested;
    }

    @Override
    public Window getWindow() {
        return mWindow;
    }

    @Override
    public Input getInput() {
        return mInput;
    }

    @Override
    public void requestClose() {
        mIsCloseRequested = true;
    }
}
