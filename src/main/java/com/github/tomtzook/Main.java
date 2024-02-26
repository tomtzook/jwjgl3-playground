package com.github.tomtzook;

import com.github.tomtzook.engine.Engine;
import com.github.tomtzook.engine.Input;
import com.github.tomtzook.engine.Window;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public class Main {

    public static void main(String[] args) {
        if (!glfwInit()) {
            throw new Error("failed to init opengl");
        }

        //noinspection resource
        glfwSetErrorCallback(new GLFWErrorCallbackI() {
            @Override
            public void invoke(int error, long description) {
                System.out.println(GLFWErrorCallback.getDescription(description));
            }
        });

        Window window = Window.create(200, 200, "Test");
        Input input = new Input(window);
        Engine engine = new Engine(window, input);

        engine.addEntity(new TestEntity());

        engine.run();
    }
}
