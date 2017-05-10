package com.fenyx.api;

/**
 *
 * @author KiQDominaN
 */
public final class EngineAPI {

    private static EngineState currentState;

    //FPS
    private static int fps;
    private static int tmp_fps;
    private static long fps_update;
    private static long last_frametime = System.currentTimeMillis();
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
        long currentFrameTime = System.currentTimeMillis();

        EngineTimer.tick();
        //Input.updateMouseWorldPos();

        if ((currentState != null)
                && (currentState.isActive())) {
            currentState.process();
        }

        if (currentFrameTime - fps_update >= 1000L) {
            fps_update = currentFrameTime;
            fps = tmp_fps;
            tmp_fps = 0;
        } else {
            tmp_fps += 1;
        }

        frametime = (float) (currentFrameTime - last_frametime) / 10.0F;
        last_frametime = currentFrameTime;
    }

    public static void renderFrame() {
    }

    //FPS
    public static int getFps() {
        return fps;
    }

    public static int getTMPFps() {
        return tmp_fps;
    }

    public static float getFrametime() {
        return frametime;
    }
}
