package com.fenyx.ui;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;

public class UICheckBox extends UIItem {

    private boolean checked;

    public UICheckBox() {
        setSize(16, 16);
        setColor(Color.white);
        setBackgroundColor(Color.gray);
        setEventDelay(70);

        this.checked = false;
    }

    public void onDraw() {
        RenderAPI.drawRect(this.x, this.y, this.width, this.height, this.background_color.mix(Color.white));

        if (this.checked)
            RenderAPI.fillRect(this.x + 2, this.y + 2, this.width - 4, this.height - 4, this.color);
    }

    public final void onClick() {
        this.checked = (!this.checked);
    }

    public boolean isChecked() {
        return this.checked;
    }
}
