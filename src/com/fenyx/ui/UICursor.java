package com.fenyx.ui;

import com.fenyx.input.Input;
import com.fenyx.render.RenderAPI;

public class UICursor
        extends UIItem {

    protected UIImage image;

    public void setImage(UIImage image) {
        this.image = image;
        if (image != null) {
            setSize(image.width, image.height);
        }
    }

    public void draw() {

        if (this.image != null) {
            RenderAPI.drawImage(this.image.getTexture(), this.x, this.y);
        } else
            onDraw();
    }

    public void onDraw() {
        RenderAPI.drawPoint(this.x, this.y, this.color);
        RenderAPI.drawLine(this.x, this.y, this.x, this.y + 10, this.color);
        RenderAPI.drawLine(this.x, this.y, this.x + 10, this.y, this.color);
        RenderAPI.drawLine(this.x, this.y, this.x + 10, this.y + 10, this.color);
    }

    public void onMouseMove() {
        this.x = Input.getMouseX();
        this.y = Input.getMouseY();
    }
}