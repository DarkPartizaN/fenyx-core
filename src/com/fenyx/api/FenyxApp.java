package com.fenyx.api;

/**
 *
 * @author KiQDominaN
 */
public abstract class FenyxApp {

    private EngineWindow window;

    public abstract void startApp(LaunchParams params);
    public abstract void destroyApp();

    public void createWindow(String title, int x, int y, int width, int height, boolean fullscreen) {
        window = new EngineWindow(title, x, y, width, height, fullscreen);
    }

    public void displayWindow() {
        window.display();
    }
}