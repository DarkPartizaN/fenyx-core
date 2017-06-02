package com.fenyx.api;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.fenyx.input.Input;

/**
 *
 * @author KiQDominaN
 */
final class EngineWindow {

    private long window_handle;
    private boolean show_system_cursor = true;

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
        glfwSetCharCallback(window_handle, new CharCallback());
        glfwSetCursorPosCallback(window_handle, new CursorCallback());
        glfwSetMouseButtonCallback(window_handle, new MouseButtonsCallback());

        glfwMakeContextCurrent(window_handle);
        glfwSwapInterval(0);

        if (GL.createCapabilities() == null)
            throw new RuntimeException("Failed to create GLCapabilities");

        ScreenConfig.setupScreen(width, height, fullscreen);

        glfwSetWindowPos(window_handle, x, y);
    }

    void display() {
        glfwShowWindow(window_handle);

        while (!glfwWindowShouldClose(window_handle)) {
            if (show_system_cursor != Input.show_system_cursor) {
                if (!Input.show_system_cursor) {
                    GLFW.glfwSetInputMode(window_handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
                } else {
                    GLFW.glfwSetInputMode(window_handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                }
                show_system_cursor = Input.show_system_cursor;
            }

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            EngineAPI.updateFrame();
            EngineAPI.renderFrame();

            glfwSwapBuffers(window_handle);
            glfwPollEvents();
        }

        glfwFreeCallbacks(window_handle);

        glfwDestroyWindow(window_handle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

        System.gc();
    }

    private class KeyCallback extends GLFWKeyCallback {

        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);

            if (action == GLFW_PRESS) {
                char c = characterForKeyCode(key);
                if (c != 0)
                    Input.inputChar(c);

                Input.pressKey(key);
            }

            if (action == GLFW_RELEASE)
                Input.resetKey(key);
        }
    }

    private char characterForKeyCode(int key) {
        switch (key) {
            case 259:
                return '\b';
            case 258:
                return '\t';
            case 261:
                return '';
            case 257:
                return '\n';
        }
        return '\000';
    }

    private final class CharCallback extends GLFWCharCallback {

        public void invoke(long window, int codepoint) {
            Input.inputChar((char) codepoint);
        }
    }

    private final class MouseButtonsCallback extends GLFWMouseButtonCallback {

        public void invoke(long window, int button, int action, int mods) {
            if ((action == 1) || (action == 2))
                Input.pressKey(button);
            if (action == 0)
                Input.resetKey(button);
        }
    }

    private class CursorCallback extends GLFWCursorPosCallback {

        public void invoke(long window, double xpos, double ypos) {
            Input.updateMousePos((int) xpos, (int) ypos);
        }
    }
}
