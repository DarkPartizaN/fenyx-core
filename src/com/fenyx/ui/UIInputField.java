package com.fenyx.ui;

import com.fenyx.input.Input;
import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;
import com.fenyx.utils.StringUtils;

public class UIInputField extends UIText {

    public static final int UNIVERSAL = 0;
    public static final int NUMERIC = 1;
    public int type;
    private int x_shift;

    public UIInputField(int type) {
        setColor(Color.black);
        setBackgroundColor(Color.white);
        setSize(0, UIFontManager.getDefault().getHeight());
        setEventDelay(100);

        this.enabled = false;

        this.type = type;
    }

    public void onClick() {
        setEnabled(!this.enabled);
    }

    public void onKeyPressed() {
        if (Input.currentKey() == 0) {
            if (!isFocused())
                setEnabled(false);
            return;
        }

        if (this.enabled) {
            char c = Input.getInput();

            if (c == '\n') {
                onClick();
                return;
            }

            if (c == '\b') {
                if (this.text.length() > 0) {
                    setText(this.text.substring(0, this.text.length() - 1));
                    if (this.x_shift < 0)
                        this.x_shift = ((int) (this.x_shift + this.font.charWidth(this.text.charAt(0))));
                }
                return;
            }

            if ((this.type == 1) && ((c < '0') || (c > '9'))) {
                return;
            }
            setText(StringUtils.concat(new Object[]{this.text, Character.valueOf(Input.getInput())}));
            if (this.font.stringWidth(this.text) > this.width)
                this.x_shift = ((int) (this.x_shift - this.font.charWidth(this.text.charAt(0))));
        }
    }

    public void onDraw() {
        if (this.enabled) {
            RenderAPI.drawRect(this.x, this.y, this.width, this.height, Color.blue.mix(Color.white));
            RenderAPI.drawRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2, Color.blue.mix(Color.white, 0.8F));
            RenderAPI.drawStraight(this.x + this.font.stringWidth(this.text) + this.x_shift, this.y + 3, 0, this.height - 6, Color.black);
        }
        RenderAPI.drawString(this.text, this.font, this.x + this.x_shift, this.y, this.color);
    }

    public void setText(String text) {
        this.text = text;
    }
}
