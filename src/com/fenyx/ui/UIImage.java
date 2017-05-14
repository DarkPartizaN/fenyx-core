package com.fenyx.ui;

import com.fenyx.render.RenderAPI;
import com.fenyx.render.Texture;
import com.fenyx.render.TextureManager;
import com.fenyx.utils.AWTImage;

public final class UIImage extends UIItem {

    private Texture texture;

    public UIImage(AWTImage image) {
        this.texture = TextureManager.createTexture(image);
        setSize(this.texture.width, this.texture.height);
    }

    public UIImage(Texture image) {
        this.texture = image;
        setSize(this.texture.width, this.texture.height);
    }

    public void onDraw() {
        if (this.texture != null)
            RenderAPI.drawImage(this.texture, this.x, this.y, this.width, this.height);
    }

    public void setImage(AWTImage image) {
        TextureManager.updateTexture(this.texture, image);
    }

    public void setTexture(Texture image) {
        this.texture = image;
        setSize(image.width, image.height);
    }

    public Texture getTexture() {
        return this.texture;
    }
}
