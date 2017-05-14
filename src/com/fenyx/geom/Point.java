package com.fenyx.geom;

public class Point {

    public float x, y;

    public Point() {
        this.x = (this.y = 0.0F);
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this(point.x, point.y);
    }
}
