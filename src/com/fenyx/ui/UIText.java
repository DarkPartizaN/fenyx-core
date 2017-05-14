package com.fenyx.ui;

import com.fenyx.render.RenderAPI;

public class UIText extends UIItem {

    protected UIFont font;
    protected String text;

    public UIText() {
        setFont(UIFontManager.getDefault());
        setSize(0, this.font.getHeight());

        this.text = "";
    }

    public UIText(String text) {
        this();
        this.text = text;

        setSize(this.font.stringWidth(text), this.font.getHeight());
    }

    public void onDraw() {
        RenderAPI.drawString(this.text, this.font, this.x, this.y, this.color);
    }

    public String getText() {
        return this.text;
    }

    public int getValue() {
        if (isInteger())
            return Integer.parseInt(this.text);
        return 0;
    }

    public boolean isInteger() {
        try {
            Integer.valueOf(this.text);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public void setText(String text) {
        this.text = text;
        setSize(this.font.stringWidth(text), this.font.getHeight());
    }

    public boolean isEmpty() {
        return (this.text == null) || (this.text.isEmpty());
    }

    public boolean equals(String s) {
        return s.equals(this.text);
    }

    public final void setFont(UIFont font) {
        this.font = font;
    }

    public final UIFont getFont() {
        return this.font;
    }
}
