package com.fenyx.ui;

import com.fenyx.render.Color;

public class UIMenuElement extends UIButton {

    private Color old_color;

    public UIMenuElement() {
        setColor(Color.gray.mix(Color.black));
        setBackgroundColor(Color.gray);
    }

    public void onFocus() {
        this.old_color = this.background_color;

        setBackgroundColor(this.color);
    }

    public void onFocusLost() {
        setBackgroundColor(this.old_color);
    }
}
