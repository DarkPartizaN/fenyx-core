package com.fenyx.ui;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;

public class UIItem extends UI {

    protected Color color = UIScheme.BASE_COLOR;
    protected Color background_color = UIScheme.BASE_BACK_COLOR;
    protected int clip_x;

    public final void setColor(Color color) {
        this.color = color;
    }

    protected int clip_y;

    public final Color getColor() {
        return this.color;
    }

    public final void setBackgroundColor(Color color) {
        this.background_color = color;
    }

    public final Color getBackgroundColor() {
        return this.background_color;
    }

    protected int clip_w;
    protected int clip_h;

    public void draw() {
        if (!isVisible()) {
            return;
        }
        sort();

        this.clip_x = this.x;
        this.clip_y = this.y;
        this.clip_w = this.width;
        this.clip_h = this.height;

        if (hasParent()) {
            checkClipBounds();
        }
        RenderAPI.clipRect(this.clip_x, this.clip_y, this.clip_w, this.clip_h);
        RenderAPI.fillRect(this.x, this.y, this.width, this.height, this.background_color);

        for (UI e : this.elements.get_all()) {
            if ((e != null)
                    && (e.isVisible()))
                e.draw();
        }
        onDraw();

        RenderAPI.resetClip();

        if (UIManager.draw_bounds) {
            RenderAPI.drawRect(this.x, this.y, this.width, this.height, Color.green);
            RenderAPI.drawRect(this.clip_x, this.clip_y, this.clip_w, this.clip_h, Color.red);
        }
    }

    protected void checkClipBounds() {
        if (this.clip_x < getParent().x) {
            this.clip_x = getParent().x;
            this.clip_w -= getParent().x - this.x;
        }
        if (this.clip_y < getParent().y) {
            this.clip_y = getParent().y;
            this.clip_h -= getParent().y - this.y;
        }
        if (this.x + this.clip_w > getParent().getBoundWidth())
            this.clip_w = (getParent().getBoundWidth() - this.x);
        if (this.y + this.clip_h > getParent().getBoundHeight()) {
            this.clip_h = (getParent().getBoundHeight() - this.y);
        }
    }

    protected void sort() {
        for (int i = 0; i < this.elements.size(); i++) {
            for (int j = this.elements.size() - 2; j >= i; j--) {
                UI ui1 = this.elements.get(j);
                UI ui2 = this.elements.get(j + 1);

                if (ui1.layer > ui2.layer)
                    this.elements.swap(j, j + 1);
            }
        }
    }

    public void onIdle() {
    }

    public void onFocus() {
    }

    public void onFocusLost() {
    }

    public void onMouseMove() {
    }

    public void onClick() {
    }

    public void onKeyPressed() {
    }

    public void onKeyReleased() {
    }

    public void onShow() {
    }

    public void onHide() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onDestroy() {
    }

    public void onDraw() {
    }

    public void onMove() {
    }

    public void onResize() {
    }
}
