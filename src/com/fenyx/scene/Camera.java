package com.fenyx.scene;

import com.fenyx.api.EngineAPI;
import com.fenyx.api.ScreenConfig;
import com.fenyx.geom.Rect;
import com.fenyx.geom.Vector2;
import com.fenyx.utils.MathUtils;

public class Camera extends SceneObject {

    private Rect viewport;
    public float aspect = ScreenConfig.screen_aspect;
    public float fov = 90f;
    public float znear = 1f;
    public float zfar = 1000f;
    protected boolean freeze = false;

    public Camera() {
        viewport = new Rect(0, 0, ScreenConfig.screen_width, ScreenConfig.screen_height);
        type = DYNAMIC;
    }

    public Camera(Rect viewport) {
        this.viewport = viewport;
        type = DYNAMIC;
    }

    public void freeze(boolean on) {
        freeze = on;
    }

    public void setViewport(Rect viewport) {
        this.viewport = viewport;
    }

    public void setViewport(float x, float y, float w, float h) {
        viewport.x = x;
        viewport.y = y;
        viewport.w = w;
        viewport.h = h;
    }

    public void init() {
    }

    public void update() {
        if (freeze) return;

        if (hasTarget()) {
            float delta_x = target.getX() - getWidth() / 2.0F - getX();
            Vector2 tmp_vec = position;
            tmp_vec.x = ((float) (tmp_vec.x + (delta_x / 12.0F + target.current_speed.x * 1.2F + MathUtils.cos(target.angle) * 3.5D) * speed * EngineAPI.getFrametime()));

            float delta_y = target.getY() - getHeight() / 2.0F - getY();
            Vector2 tmp_vec2 = position;
            tmp_vec2.y = ((float) (tmp_vec2.y + (delta_y / 12.0F + target.current_speed.y * 1.2F + MathUtils.sin(target.angle) * 3.5D) * speed * EngineAPI.getFrametime()));
        }
    }

    public void destroy() {
    }

    public float getWidth() {
        return viewport.w;
    }

    public float getHeight() {
        return viewport.h;
    }
}
