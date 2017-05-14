package com.fenyx.api;

/**
 *
 * @author KiQDominaN
 */
public final class EngineAPI {

    private static EngineState currentState;

    //FPS
    private static int fps;
    private static int tmpFps;
    private static long fpsUpdate;
    private static long lastFrametime = System.currentTimeMillis();
    private static float frametime;

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
        } else tmpFps += 1;

        frametime = (float) (currentFrametime - lastFrametime) / 10f;
        lastFrametime = currentFrametime;
    }

    public static void renderFrame() {
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
}