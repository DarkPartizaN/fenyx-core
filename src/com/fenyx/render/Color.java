package com.fenyx.render;

public final class Color {

    public static final Color transparent = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    public static final Color black = new Color(0.0F, 0.0F, 0.0F, 1.0F);
    public static final Color white = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    public static final Color gray = new Color(0.5F, 0.5F, 0.5F, 1.0F);
    public static final Color dark_gray = new Color(0.2F, 0.2F, 0.2F, 1.0F);
    public static final Color red = new Color(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Color green = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    public static final Color blue = new Color(0.0F, 0.0F, 1.0F, 1.0F);
    public static final Color cyan = new Color(0.0F, 0.5F, 1.0F, 1.0F);
    public static final Color yellow = new Color(1.0F, 1.0F, 0.0F, 1.0F);
    public static final Color orange = new Color(1.0F, 0.5F, 0.0F, 1.0F);
    public static final Color violet = new Color(1.0F, 0.0F, 1.0F, 1.0F);
    public static final Color brown = new Color(0.5F, 0.25F, 0.0F, 1.0F);

    public float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int rgba) {
        this.r = ((0xFF & rgba >> 16) / 255.0F);
        this.g = ((0xFF & rgba >> 8) / 255.0F);
        this.b = ((0xFF & rgba) / 255.0F);
        this.a = ((0xFF & rgba >> 24) / 255.0F);
    }

    public void set(int rgba) {
        this.r = ((0xFF & rgba >> 16) / 255.0F);
        this.g = ((0xFF & rgba >> 8) / 255.0F);
        this.b = ((0xFF & rgba) / 255.0F);
        this.a = ((0xFF & rgba >> 24) / 255.0F);
    }

    public void set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int get() {
        return (int) (this.r * 255.0F) << 24 | (int) (this.g * 255.0F) << 16 | (int) (this.b * 255.0F) << 8 | (int) this.a * 255;
    }

    public Color mix(Color c) {
        return mix(c, 0.5F);
    }

    public Color mix(Color c, float percents) {
        return new Color(this.r * (1.0F - percents) + c.r * percents, this.g * (1.0F - percents) + c.g * percents, this.b * (1.0F - percents) + c.b * percents, this.a * (1.0F - percents) + c.a * percents);
    }

    public java.awt.Color getAWTColor() {
        return new java.awt.Color(this.r, this.g, this.b, this.a);
    }

    public Color invert() {
        return new Color(1.0F - this.r, 1.0F - this.g, 1.0F - this.b, 1.0F - this.a);
    }

    public Color invert_no_alpha() {
        return new Color(1.0F - this.r, 1.0F - this.g, 1.0F - this.b, this.a);
    }
}
