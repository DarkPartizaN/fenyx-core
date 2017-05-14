package com.fenyx.render;

public class Material {

    public String name;
    public Color color;
    public Texture diffuse;
    public BlendingMode blending;
    public boolean colorOnly;

    public void bind() {
        RenderAPI.setBlending(blending);
        RenderAPI.setColor(color);

        if (diffuse != null) {
            RenderAPI.setActiveTexture(0);
            RenderAPI.setTexture(diffuse);

            colorOnly = false;
        } else {
            colorOnly = true;
        }
    }

    public void unbind() {
        if (diffuse != null) {
            RenderAPI.setActiveTexture(0);
            RenderAPI.setTexture(null);
        }

        RenderAPI.setColor(Color.white);
        RenderAPI.setBlending(null);
    }
}
