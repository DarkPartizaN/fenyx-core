package com.fenyx.geom;

import com.fenyx.utils.MathUtils;

public class Vector2 extends Point {

    public static final Vector2 ZERO_VECTOR = new Vector2();

    public Vector2() {
        this.x = (this.y = 0.0F);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        this.x = (this.y = 0.0F);
    }

    public float length() {
        return MathUtils.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalize() {
        return new Vector2(this.x, this.y).div(length());
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    public Vector2 mul(float f) {
        return new Vector2(this.x * f, this.y * f);
    }

    public Vector2 div(float f) {
        return new Vector2(this.x / f, this.y / f);
    }

    public float dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2 invert() {
        return new Vector2(-this.x, -this.y);
    }

    public static float angle(Vector2 a, Vector2 b) {
        float x1 = a.x;
        float y1 = a.y;
        float x2 = b.x;
        float y2 = b.y;

        return (float) Math.toDegrees(Math.atan2(y1 - y2, x1 - x2));
    }
}