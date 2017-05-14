package com.fenyx.render;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

public final class Texture {

    public int id;
    public int width;
    public int height;
    private TextureFormat format;
    public ByteBuffer raw;

    public Texture() {
        format = TextureFormat.getDefault();
    }

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setFormat(TextureFormat format) {
        this.format = format;
    }

    public void compile() {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, format.wrapping);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, format.wrapping);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, format.filtration);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, format.filtration);

        if (this.raw == null) {
            glTexImage2D(GL_TEXTURE_2D, 0, format.internal_format, width, height, 0, format.format, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, format.internal_format, width, height, 0, format.format, GL_UNSIGNED_BYTE, raw);
        }
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
