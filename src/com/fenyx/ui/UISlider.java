package com.fenyx.ui;

import com.fenyx.input.Input;
import com.fenyx.render.RenderAPI;

public class UISlider
        extends UIItem {

    public float step = 1.0F;
    public float position;

    public void onDraw() {
        RenderAPI.drawStraight(this.x, this.y + this.height / 2, this.width, 1, this.color);
        RenderAPI.fillRect(this.x + (int) (this.position * this.step) - 4, this.y + this.height / 2 - 4, 8, 8, this.color);
    }

    public void setStep(float step) {
        this.step = step;
    }

    public void onClick() {
        float tmp_x = Input.getMouseX() - this.x;
        float tmp_y = Input.getMouseY() - this.y;

        this.position = (tmp_x / this.step);
    }
}
