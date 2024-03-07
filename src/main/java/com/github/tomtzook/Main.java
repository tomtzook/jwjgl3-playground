package com.github.tomtzook;

import com.castle.util.closeables.Closer;
import com.github.tomtzook.engine.Engine;
import com.github.tomtzook.engine.Input;
import com.github.tomtzook.engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    public static void main(String[] args) throws Exception {
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

        try (Closer closer = Closer.empty()) {
            Window window = Window.create(500, 500, "Test");
            closer.add(window);

            Input input = new Input(window);
            Engine engine = new Engine(window, input);
            closer.add(engine);

            TestEntity2 entity1 = new TestEntity2();
            entity1.getTransform().setPosition(new Vector3f(0, 0, -2));
            engine.addEntity(entity1);

            TestEntity2 entity2 = new TestEntity2();
            entity2.getTransform().setPosition(new Vector3f(4, 0, -1));
            engine.addEntity(entity2);

            TestHudElement1 testHudElement1 = new TestHudElement1();
            engine.addHudElement(testHudElement1);

            engine.run();
        } finally {
            glfwTerminate();
        }
    }
}
