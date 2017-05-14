package com.fenyx.utils;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author KiQDominaN
 */
public class MathUtils {

    private static final Random RND = new Random();

    public static int random_int(int min, int max) {
        return min + RND.nextInt(1 + max - min);
    }

    public static float random_float(float min, float max) {
        return min + RND.nextFloat() * (1.0F + max - min);
    }

    public static double random_double(double min, double max) {
        return min + RND.nextDouble() * (1.0D + max - min);
    }

    public static long random_long(long min, long max) {
        return min + Math.abs(RND.nextLong()) % (1L + max - min);
    }

    public static int random_color() {
        int r = random_int(0, 255);
        int g = random_int(0, 255);
        int b = random_int(0, 255);

        return 0xFF000000 | r << 16 | g << 8 | b;
    }

    public static float sin(double angle) {
        return (float) Math.sin(Math.toRadians(angle));
    }

    public static float cos(double angle) {
        return (float) Math.cos(Math.toRadians(angle));
    }

    public static float tan(double d) {
        return (float) Math.tan(d);
    }

    public static float sqrt(double d) {
        return (float) Math.sqrt(d);
    }

    public static float angle(float x, float y) {
        return (float) Math.toDegrees(Math.atan2(x, y));
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    public static double min(double a, double b) {
        return a < b ? a : b;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    public static int bound(int a, int b, int c) {
        int r = b;
        if (b < a)
            r = a;
        if (b > c) {
            r = c;
        }
        return r;
    }

    public static float bound(float a, float b, float c) {
        float r = b;
        if (b < a)
            r = a;
        if (b > c) {
            r = c;
        }
        return r;
    }

    static int pow(int a, int n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 1) {
            return pow(a, n - 1) * a;
        }
        int b = pow(a, n / 2);
        return b * b;
    }

    static float pow(float a, float n) {
        if (n == 0.0F) {
            return 1.0F;
        }
        if (n % 2.0F == 1.0F) {
            return pow(a, n - 1.0F) * a;
        }
        float b = pow(a, n / 2.0F);
        return b * b;
    }

    static long pow(long a, long n) {
        if (n == 0L) {
            return 1L;
        }
        if (n % 2L == 1L) {
            return pow(a, n - 1L) * a;
        }
        long b = pow(a, n / 2L);
        return b * b;
    }

    public static void sort(int[] array) {
        Arrays.sort(array);
    }

    public static void sort(float[] array) {
        Arrays.sort(array);
    }
}
