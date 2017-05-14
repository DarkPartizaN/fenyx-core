package com.fenyx.ui;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;

public class UIPanel extends UIItem {

    protected boolean resize_background;
    protected UIImage background;
    protected UICursor cursor;
    protected UIFont font = UIFontManager.getDefault();

    public void setFont(UIFont font) {
        this.font = font;
    }

    public final void setCursor(UICursor cursor) {
        this.cursor = cursor;
    }

    public final void setCursorRecursive(UICursor cursor) {
        this.cursor = cursor;

        for (UI ui : getElements())
            ((UIPanel) ui).setCursor(cursor);
    }

    public final void resetCursor() {
        this.cursor = null;
    }

    public final UICursor getCursor() {
        return this.cursor;
    }

    public final void setBackground(UIImage background) {
        this.background = background;
    }

    public final UIImage getBackground() {
        return this.background;
    }

    public final void resizeBackground(boolean resize) {
        this.resize_background = resize;
    }

    public final void draw() {
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
        int tmp_y;
        int tmp_x;
        if (this.background != null) {
            if (!this.resize_background) {
                for (tmp_y = this.y; tmp_y < this.height; tmp_y += this.background.getHeight()) {
                    for (tmp_x = this.x; tmp_x < this.width; tmp_x += this.background.getWidth())
                        RenderAPI.drawImage(this.background.getTexture(), tmp_x, tmp_y);
                }
            } else {
                RenderAPI.drawImage(this.background.getTexture(), this.x, this.y, this.width, this.height);
            }
        }
        for (UI e : this.elements.get_all()) {
            if ((e != null)
                    && (e.isVisible()))
                e.draw();
        }
        onDraw();

        RenderAPI.resetClip();

        if (this.cursor != null) {
            if (this.cursor.isEnabled())
                this.cursor.update();
            if (this.cursor.isVisible()) {
                this.cursor.draw();
            }
        }
        if (UIManager.draw_bounds) {
            RenderAPI.drawRect(this.x, this.y, this.width, this.height, Color.green);
            RenderAPI.drawRect(this.clip_x, this.clip_y, this.clip_w, this.clip_h, Color.red);
        }
    }
}