package com.fenyx.api;

/**
 *
 * @author KiQDominaN
 */
public final class EngineTimer {

    public static final int DEFAULT_TIME_SPEED = 100;
    public static int time_speed = 100;
    private static long last_time = System.currentTimeMillis();
    public static long time;

    public static void tick() {
        if (time_speed <= 0) return;

        long currentTime = System.currentTimeMillis();

        if (currentTime - last_time > 1000 / time_speed) {
            long delta = (long) (time_speed / 1000f * EngineAPI.getFrametime());
            if (delta <= 1) delta = 1;

            time += delta;
            last_time = currentTime;
        }
    }
}
