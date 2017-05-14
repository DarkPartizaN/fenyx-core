package com.fenyx.geom;

import com.fenyx.utils.MathUtils;

public abstract class Shape {

    public float x;
    public float y;
    public float w;
    public float h;
    protected Point[] points;
    private int point_count;

    public abstract void reset();

    public void addPoint(float x, float y) {
        if (this.points == null)
            return;

        this.points[(this.point_count++)] = new Point(x, y);
    }

    public Point[] getPoints() {
        return this.points;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.w = width;
        this.h = height;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.w;
    }

    public float getHeight() {
        return this.h;
    }

    public float getMinX() {
        float min_x = this.points[0].x;
        for (Point p : this.points)
            min_x = MathUtils.min(min_x, p.x);

        return min_x;
    }

    public float getMinY() {
        float min_y = this.points[0].y;
        for (Point p : this.points) 
            min_y = MathUtils.min(min_y, p.y);

        return min_y;
    }

    public float getMaxX() {
        float max_x = this.points[0].x;
        for (Point p : this.points)
            max_x = MathUtils.max(max_x, p.x);

        return max_x;
    }

    public float getMaxY() {
        float max_y = this.points[0].y;
        for (Point p : this.points)
            max_y = MathUtils.max(max_y, p.y);

        return max_y;
    }

    public Point getSize() {
        float maxx;
        float minx = maxx = getMinX();
        float maxy;
        float miny = maxy = getMinY();

        for (Point p : this.points) {
            maxx = MathUtils.max(maxx, p.x);
            maxy = MathUtils.max(maxy, p.y);
        }

        return new Point(maxx - minx, maxy - miny);
    }

    public static final boolean intersects(Shape a, Shape b) {
        Vector2 normal = new Vector2();

        for (int i = 0; i < a.points.length; i++) {
            int j = (i + 1) % a.points.length;

            Point p1 = a.points[i];
            Point p2 = a.points[j];

            normal.x = p2.y - p1.y;
            normal.y = p1.x - p2.x;
            normal = normal.normalize();

            float minA = 0;
            float maxA = 0;

            for (Point p : a.points) {
                float projected = normal.x * p.x + normal.y * p.y;

                if (minA == 0 || projected < minA)
                    minA = projected;
                if (maxA == 0 || projected > maxA)
                    maxA = projected;
            }

            float minB = 0;
            float maxB = 0;

            for (Point p : b.points) {
                float projected = normal.x * p.x + normal.y * p.y;

                if (minB == 0 || projected < minB)
                    minB = projected;
                if (maxB == 0 || projected > maxB)
                    maxB = projected;
            }

            if (maxA < minB || maxB < minA)
                return false;
        }

        return true;
    }
}
