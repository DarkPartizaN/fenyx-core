package com.fenyx.render;

public abstract class Renderer {

    public abstract void init();
    public abstract void prerender();
    public abstract void render();
    public abstract void postrender();
}
