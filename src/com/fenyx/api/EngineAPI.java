package com.fenyx.api;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;
import com.fenyx.render.Renderer;
import com.fenyx.scene.Scene;
import com.fenyx.scene.SceneManager;
import com.fenyx.ui.UIManager;

/**
 *
 * @author KiQDominaN
 */
public final class EngineAPI {

    private static EngineState currentState;
    private static Renderer currentRenderer;

    //FPS
    private static int fps;
    private static int tmpFps;
    private static long fpsUpdate;
    private static long lastFrametime = System.currentTimeMillis();
    private static float frametime;

    //Scene
    private static final SceneManager sceneManager = new SceneManager();

    //======================================
    //This class represents engine functions
    //with safe access from game library
    //======================================
    //States
    public static void setState(EngineState state) {
        currentState = state;
    }

    public static EngineState getState() {
        return currentState;
    }

    //Frame
    public static void updateFrame() {
        long currentFrametime = System.currentTimeMillis();

        EngineTimer.tick();
        //Input.updateMouseWorldPos();

        if (currentState != null && currentState.isActive()) currentState.process();

        if (currentFrametime - fpsUpdate >= 1000) {
            fpsUpdate = currentFrametime;
            fps = tmpFps;
            tmpFps = 0;
        } else
            tmpFps++;

        frametime = (float) (currentFrametime - lastFrametime) / 10f;
        lastFrametime = currentFrametime;
    }

    public static void renderFrame() {
        if (currentRenderer != null) {
            currentRenderer.prerender();
            currentRenderer.render();
            currentRenderer.postrender();
        }

        if (EngineDefines.DEFINE_UI_ENABLED) {
            UIManager.update();
            UIManager.draw();
        }

        if (EngineDefines.DEFINE_SHOW_FPS) {
            RenderAPI.setOrtho(ScreenConfig.screen_width, ScreenConfig.screen_height, true);
            RenderAPI.drawString("FPS:" + getFps(), 2, 2, Color.green);
        }
    }

    //FPS
    public static int getFps() {
        return fps;
    }

    public static int getTMPFps() {
        return tmpFps;
    }

    public static float getFrametime() {
        return frametime;
    }

    //================================
    //Renderer
    //================================
    public static void setRenderer(Renderer render) {
        currentRenderer = render;
    }

    public static Renderer getRenderer() {
        return currentRenderer;
    }

    //SCENE
    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    public static void addScene(Scene scene) {
        if (sceneManager == null)
            return;
        sceneManager.add(scene);
    }

    public static void removeScene(Scene scene) {
        if (sceneManager == null)
            return;
        sceneManager.remove(scene);
    }

    public static void removeScene(int i) {
        if (sceneManager == null)
            return;
        sceneManager.remove(i);
    }

    public static void removeScene(String name) {
        if (sceneManager == null)
            return;
        sceneManager.remove(name);
    }

    public static void setCurrentScene(Scene scene) {
        if (sceneManager == null)
            return;
        sceneManager.setCurrent(scene);
    }

    public static Scene getCurrentScene() {
        if (sceneManager == null) {
            return null;
        }
        return sceneManager.getCurrent();
    }

    public static boolean hasCurrentScene() {
        if (sceneManager == null)
            return false;
        return sceneManager.hasCurrent();
    }
}
