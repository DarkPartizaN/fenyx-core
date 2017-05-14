package com.fenyx.ui;

import com.fenyx.api.EngineTimer;
import com.fenyx.api.ScreenConfig;
import com.fenyx.input.Input;

public abstract class UI {

    public static int LAYER_TOP = 999;
    public boolean visible;
    public boolean enabled;
    public boolean active;
    public boolean focused;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int layer;
    protected float angle;
    protected boolean fullscreen;
    private UI parent;
    protected UIItemPool elements;
    protected long last_idle;
    protected long idle_delay;
    protected long last_event;
    protected long event_delay;
    private long remove_delay;
    private long start_remove;
    private boolean should_remove = false;
    private boolean was_focused;

    public UI() {
        this.visible = true;
        this.active = true;
        this.enabled = true;

        this.elements = new UIItemPool();
        this.last_event = System.currentTimeMillis();
        this.last_idle = System.currentTimeMillis();
    }

    public abstract void draw();
    public abstract void onShow();
    public abstract void onMove();
    public abstract void onResize();
    public abstract void onEnable();
    public abstract void onIdle();
    public abstract void onFocus();
    public abstract void onFocusLost();
    public abstract void onMouseMove();
    public abstract void onClick();
    public abstract void onKeyPressed();
    public abstract void onKeyReleased();
    public abstract void onDraw();
    public abstract void onDisable();
    public abstract void onHide();
    public abstract void onDestroy();

    public final void add(UI e) {
        if (this.elements.contains(e)) {
            return;
        }
        e.parent = this;
        e.setPosition(this.x + e.x, this.y + e.y);

        this.elements.add(e);
    }

    public final void remove(UI e) {
        if (this.elements.is_empty())
            return;
        if (!this.elements.contains(e)) {
            return;
        }
        this.elements.remove(e);

        if (e.parent == this)
            e.parent = null;
        e.setPosition(e.x - this.x, e.y - this.y);
    }

    public final void removeLately(UI e, int delay) {
        e.start_remove = EngineTimer.time;
        e.remove_delay = delay;
        e.should_remove = true;
    }

    public final void setPosition(int x, int y) {
        int delta_x = x - getX();
        int delta_y = y - getY();

        this.x += delta_x;
        this.y += delta_y;

        onMove();

        for (UI e : this.elements.get_all())
            e.setPosition(e.getX() + delta_x, e.getY() + delta_y);
    }

    public final void centerElement() {
        if (this.parent == null) {
            setPosition(ScreenConfig.screen_width / 2 - this.width / 2, ScreenConfig.screen_height / 2 - this.height / 2);
        } else
            setPosition(this.parent.width / 2 - this.width / 2, this.parent.width / 2 - this.height / 2);
    }

    public final void setX(int x) {
        setPosition(x, this.y);
    }

    public final void setY(int y) {
        setPosition(this.x, y);
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final void setAngle(float angle) {
        this.angle = angle;
    }

    public final void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return this.layer;
    }

    public final void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;

        setPosition(0, 0);
        setSize(ScreenConfig.screen_width, ScreenConfig.screen_height);
    }

    public final boolean isFullscreen() {
        return this.fullscreen;
    }

    public final void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        onResize();
    }

    public final void setWidth(int width) {
        setSize(width, this.height);
    }

    public final void setHeight(int height) {
        setSize(this.width, height);
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getBoundWidth() {
        return this.x + this.width;
    }

    public final int getBoundHeight() {
        return this.y + this.height;
    }

    public final boolean hasParent() {
        return this.parent != null;
    }

    public final UI getParent() {
        return this.parent;
    }

    public UI[] getElements() {
        return this.elements.get_all();
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;

        if (visible) {
            onShow();
        } else
            onHide();
    }

    public final boolean isVisible() {
        return this.visible;
    }

    public final void setEnabled(boolean enable) {
        this.enabled = enable;

        if (enable) {
            onEnable();
        } else
            onDisable();
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public final void setActive(boolean active) {
        this.active = active;
    }

    public final boolean isActive() {
        return this.active;
    }

    public final boolean isFocused() {
        return this.focused;
    }

    public boolean canEvent() {
        if ((hasParent()) && ((!this.parent.isActive()) || (!this.parent.isVisible())))
            return false;
        if ((!isActive()) || (!isVisible())) {
            return false;
        }
        if (System.currentTimeMillis() - this.last_event > this.event_delay) {
            this.last_event = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public final void setEventDelay(int delay) {
        this.event_delay = delay;
    }

    public final void setIdleDelay(int delay) {
        this.idle_delay = delay;
    }

    private boolean canIdle() {
        if (System.currentTimeMillis() - this.last_idle > this.idle_delay) {
            this.last_idle = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public final void update() {
        if (canIdle()) {
            onIdle();
        }
        this.focused = ((Input.getMouseX() > this.x) && (Input.getMouseX() < this.x + this.width) && (Input.getMouseY() > this.y) && (Input.getMouseY() < this.y + this.height));

        if (canEvent()) {
            if (this.focused) {
                if (Input.isKeyPressed(0)) {
                    onClick();
                }
                if (!this.was_focused) {
                    this.was_focused = true;
                    onFocus();
                }
            } else if (this.was_focused) {
                this.was_focused = false;
                onFocusLost();
            }

            if (Input.isKeyPressed(64537))
                onKeyPressed();
            if (Input.mouseMoved()) {
                onMouseMove();
            }
        }
        for (UI e : this.elements.get_all()) {
            if (!e.should_remove) {
                e.update();
            } else if (EngineTimer.time == e.start_remove + e.remove_delay)
                remove(e);
        }
    }

    public final void open() {
        setVisible(true);
        setActive(true);
    }

    public final void close() {
        setVisible(false);
        setActive(false);
    }
}
