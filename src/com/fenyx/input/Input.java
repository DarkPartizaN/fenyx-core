package com.fenyx.input;

import java.util.HashMap;

/**
 *
 * @author KiQDominaN
 */
public class Input {

    public static final int KEY_NONE = -666;
    public static final int KEY_ANY = -999;

    private static final HashMap<Integer, Long> key_chain = new HashMap<>(1); //Pressed buttons will be stored her
    private static int current_key;

    public static boolean show_system_cursor = true;
    private static int mouse_pos_x, mouse_pos_y;
    private static int old_mouse_pos_x, old_mouse_pos_y;

    private static char current_char; //Current input

    public static void pressKey(int key) {
        current_key = key;
        key_chain.put(key, System.currentTimeMillis());
    }

    public static void resetKey(int key) {
        key_chain.remove(key);
    }

    public static boolean isKeyPressed(int key) {
        if (key == KEY_ANY)
            return !key_chain.isEmpty();
        return (key_chain.containsKey(key));
    }

    public static int currentKey() {
        if (key_chain.isEmpty())
            return KEY_NONE;
        return current_key;
    }

    public static long keyTimeMs(int key) {
        if (isKeyPressed(key))
            return (System.currentTimeMillis() - key_chain.get(key));
        return 0;
    }

    public static float keyTime(int key) {
        if (isKeyPressed(key))
            return (keyTimeMs(key) / 1000f);
        return 0;
    }

    public static void resetKeys() {
        key_chain.clear();
    }

    public static void inputChar(char c) {
        current_char = c;
    }

    public static char getInput() {
        return current_char;
    }

    public static void resetInput() {
        current_char = 0;
    }

    public static void updateMousePos(int x, int y) {
        old_mouse_pos_x = mouse_pos_x;
        old_mouse_pos_y = mouse_pos_y;

        mouse_pos_x = x;
        mouse_pos_y = y;
    }

    public static boolean mouseMoved() {
        return ((old_mouse_pos_x != mouse_pos_x) || (old_mouse_pos_y != mouse_pos_y));
    }

    public static int getMouseX() {
        return (int) mouse_pos_x;
    }

    public static int getMouseY() {
        return (int) mouse_pos_y;
    }

//    public static boolean mouseInRect(Rect rect) {
//        return (mouse_pos_x > rect.x && mouse_pos_x + 8 < rect.x + rect.w && mouse_pos_y > rect.y && mouse_pos_y + 8 < rect.y + rect.h);
//    }

    public static void showSystemCursor(boolean show) {
        show_system_cursor = show;
    }
}