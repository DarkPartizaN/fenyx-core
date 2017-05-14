package com.fenyx.ui;

import com.fenyx.render.Color;

public class UIButton extends UIPanel {

    protected UIText label;

    public UIButton() {
        resizeBackground(true);
        setEventDelay(100);

        this.label = new UIText();

        add(this.label);
    }

    public void setFont(UIFont font) {
        this.label.setFont(font);
    }

    public void changeText(String text) {
        this.label.setText(text);
    }

    public void setText(String text) {
        this.label.setText(text);

        setTextPosition(0, 0);
        setSize(this.label.getWidth(), this.label.getHeight());
    }

    public void setTextColor(Color c) {
        this.label.setColor(c);
    }

    public void setTextPosition(int x, int y) {
        this.label.setPosition(x, y);
    }

    public UIText getText() {
        return this.label;
    }

    public void centerText() {
        setTextPosition(this.width / 2 - this.label.getWidth() / 2, this.height / 2 - this.label.getHeight() / 2);
    }
}
