package com.fenyx.api;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import com.fenyx.input.Input;

/**
 *
 * @author KiQDominaN
 */
final class EngineWindow {

    private long window_handle;

    public EngineWindow(String title, int x, int y, int width, int height, boolean fullscreen) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window_handle = glfwCreateWindow(width, height, title, (fullscreen) ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (window_handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window_handle, new KeyCallback());
        glfwSetCursorPosCallback(window_handle, new CursorCallback());

        glfwSetWindowPos(window_handle, x, y);
        glfwMakeContextCurrent(window_handle);
        glfwSwapInterval(0);

        if (GL.createCapabilities() == null)
            throw new RuntimeException("Failed to create GLCapabilities");

        ScreenConfig.setupScreen(width, height, fullscreen);
    }

    public void display() {
        glfwShowWindow(window_handle);

        while (!glfwWindowShouldClose(window_handle)) {
            glfwSwapBuffers(window_handle);
            glfwPollEvents();
        }

        glfwFreeCallbacks(window_handle);
        glfwDestroyWindow(window_handle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private class KeyCallback extends GLFWKeyCallback {

        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);

            if (action == GLFW_PRESS) Input.pressKey(key);
            if (action == GLFW_RELEASE) Input.resetKey(key);
        }
    }

    private class CursorCallback extends GLFWCursorPosCallback {

        public void invoke(long window, double xpos, double ypos) {
            Input.updateMousePos((int) xpos, (int) ypos);
        }
    }
}