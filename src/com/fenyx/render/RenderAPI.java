package com.fenyx.render;

import com.fenyx.geom.Matrix;
import com.fenyx.geom.Rect;
import com.fenyx.scene.Camera;
import com.fenyx.ui.UIFont;
import com.fenyx.ui.UIFontManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class RenderAPI {

    public static final int R_POINTS = 0;
    public static final int R_LINES = 1;
    public static final int R_LINE_LOOP = 2;
    public static final int R_TRI = 4;
    public static final int R_TRI_FAN = 6;
    public static final int R_QUAD = 7;
    public static final int R_POLY = 9;
    private static final Rect viewport = new Rect();
    private static final Rect scissor = new Rect();
    private static final Matrix projection = new Matrix();
    private static Matrix modelview = new Matrix();
    private static Matrix camMatrix;

    public static void init() {
        GL11.glEnable(3089);
    }

    public static void setViewport(int x, int y, int w, int h) {
        viewport.x = x;
        viewport.y = y;
        viewport.w = w;
        viewport.h = h;

        GL11.glViewport(x, y, w, h);
    }

    public static void setOrtho(int width, int height, boolean upside) {
        float l = -(width / 2.0F);
        float r = -l;
        float b = -(height / 2.0F);
        float t = -b;
        float zn = -1.0F;
        float zf = 1.0F;
        float flip = upside ? 2.0F : -2.0F;

        float[] m = new float[16];

        m[0] = (2.0F / (r - l));
        m[1] = 0.0F;
        m[2] = 0.0F;
        m[3] = 0.0F;

        m[4] = 0.0F;
        m[5] = (flip / (b - t));
        m[6] = 0.0F;
        m[7] = 0.0F;

        m[8] = 0.0F;
        m[9] = 0.0F;
        m[10] = (2.0F / (zf - zn));
        m[11] = 0.0F;

        m[12] = ((l + r) / (l - r) + l * m[0]);
        m[13] = ((t + b) / (b - t) + b * m[5]);
        m[14] = ((zf + zn) / (zn - zf));
        m[15] = 1.0F;

        projection.set(m);

        modelview.reset();

        GL11.glMatrixMode(5889);
        GL11.glLoadMatrixf(projection.gl_mat());

        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
    }

    public static void setOrtho(int width, int height) {
        float l = -(width / 2.0F);
        float r = -l;
        float b = -(height / 2.0F);
        float t = -b;
        float zn = -1.0F;
        float zf = 1.0F;

        float[] m = new float[16];

        m[0] = (2.0F / (r - l));
        m[1] = 0.0F;
        m[2] = 0.0F;
        m[3] = 0.0F;

        m[4] = 0.0F;
        m[5] = (-2.0F / (b - t));
        m[6] = 0.0F;
        m[7] = 0.0F;

        m[8] = 0.0F;
        m[9] = 0.0F;
        m[10] = (2.0F / (zf - zn));
        m[11] = 0.0F;

        m[12] = ((l + r) / (l - r) + l * m[0]);
        m[13] = ((t + b) / (b - t) + b * m[5]);
        m[14] = ((zf + zn) / (zn - zf));
        m[15] = 1.0F;

        projection.set(m);

        modelview.reset();

        GL11.glMatrixMode(5889);
        GL11.glLoadMatrixf(projection.gl_mat());

        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
    }

    public static void setTranslation(float x, float y) {
        if (modelview.is_identity) {
            if (camMatrix != null) {
                modelview = Matrix.mul(modelview, camMatrix);
            } else
                modelview = Matrix.translate(x, y);
        } else {
            modelview = Matrix.mul(modelview, Matrix.translate(x, y));
        }

        GL11.glMatrixMode(5888);
        GL11.glLoadMatrixf(modelview.gl_mat());
    }

    public static void setRotation(float angle) {
        if (modelview.is_identity) {
            modelview = Matrix.rotate(angle);
        } else {
            modelview = Matrix.mul(modelview, Matrix.rotate(angle));
        }

        GL11.glMatrixMode(5888);
        GL11.glLoadMatrixf(modelview.gl_mat());
    }

    public static void setCamera(Camera camera) {
        camMatrix = Matrix.translate(-camera.getX(), -camera.getY());
    }

    public static void resetView() {
        modelview.reset();

        GL11.glMatrixMode(5888);
        GL11.glLoadMatrixf(modelview.gl_mat());
    }

    public static void clipRect(int x, int y, int w, int h) {
        y = (int) (viewport.h - y - h);

        scissor.x = x;
        scissor.y = y;
        scissor.w = w;
        scissor.h = h;

        GL11.glScissor(x, y, w, h);
    }

    public static void resetClip() {
        clipRect(0, 0, (int) viewport.w, (int) viewport.h);
    }

    public static Rect getScissor() {
        return scissor;
    }

    public static void setClearColor(Color c) {
        GL11.glClearColor(c.r, c.g, c.b, c.a);
    }

    public static void clear() {
        GL11.glClear(16640);
    }

    public static void setColor(Color c) {
        if (c != null)
            GL11.glColor4f(c.r, c.g, c.b, c.a);
    }

    public static void setBlending(BlendingMode mode) {
        if (mode != null) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(mode.src, mode.dst);
        } else {
            GL11.glDisable(3042);
        }
    }

    public static void setActiveTexture(int tex) {
        GL13.glActiveTexture(33984 + tex);
    }

    public static void setTexture(Texture tex) {
        if (tex != null) {
            GL11.glEnable(3553);
            GL11.glBindTexture(3553, tex.id);
        } else {
            GL11.glBindTexture(3553, 0);
            GL11.glDisable(3553);
        }
    }

    public static void setMultiTexture(int pos, Texture tex) {
        if (tex != null) {
            GL13.glActiveTexture(33984 + pos);
            GL11.glEnable(3553);
            GL11.glBindTexture(3553, tex.id);
        } else {
            GL13.glActiveTexture(33984 + pos);
            GL11.glDisable(3553);
        }
    }

    public static void begin(int mode) {
        GL11.glBegin(mode);
    }

    public static void end() {
    }

    public static void texCoord(float u, float v) {
        GL11.glTexCoord2f(u, v);
    }

    public static void multiTexCoords(int tex, float u, float v) {
        GL13.glMultiTexCoord2f(33984 + tex, u, v);
    }

    public static void vertexCoord(float x, float y) {
        GL11.glVertex2f(x, y);
    }

    public static void drawPoint(int x, int y, Color color) {
        setTexture(null);
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(color);

        GL11.glBegin(0);

        GL11.glVertex2f(x + 0.5F, y + 0.5F);

        GL11.glEnd();
    }

    public static void setPointSize(int size) {
        GL11.glPointSize(size);
    }

    public static void drawStraight(int x1, int y1, int x2, int y2, Color color) {
        drawLine(x1, y1, x1 + x2, y1 + y2, color);
    }

    public static void drawLine(int x1, int y1, int x2, int y2, Color color) {
        setTexture(null);
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(color);

        GL11.glBegin(1);

        GL11.glVertex2f(x1 + 0.5F, y1 + 0.5F);
        GL11.glVertex2f(x2, y2);

        GL11.glEnd();
    }

    public static void drawRect(int x, int y, int w, int h, Color color) {
        setTexture(null);
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(color);

        GL11.glBegin(2);

        GL11.glVertex2f(x + 0.5F, y + 0.5F);
        GL11.glVertex2f(x + w, y + 0.5F);
        GL11.glVertex2f(x + w, y + h);
        GL11.glVertex2f(x + 0.5F, y + h);

        GL11.glEnd();
    }

    public static void fillRect(int x, int y, int w, int h, Color color) {
        setTexture(null);
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(color);

        GL11.glBegin(9);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + w, y);
        GL11.glVertex2f(x + w, y + h);
        GL11.glVertex2f(x, y + h);

        GL11.glEnd();
    }

    public static void drawImage(Texture tex, int x, int y, int w, int h, float alpha) {
        setTexture(tex);
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(new Color(1.0F, 1.0F, 1.0F, alpha));

        GL11.glBegin(7);

        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(x + w, y);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(x + w, y + h);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(x, y + h);

        GL11.glEnd();
    }

    public static void drawImage(Texture tex, int x, int y, int w, int h) {
        drawImage(tex, x, y, w, h, 1.0F);
    }

    public static void drawImage(Texture tex, int x, int y) {
        drawImage(tex, x, y, tex.width, tex.height);
    }

    public static void drawString(String s, UIFont font, int x, int y, Color color) {
        if ((s == null) || (s.length() <= 0)) {
            return;
        }
        setTexture(font.getFontImage());
        setBlending(BlendingMode.BLEND_ALPHA);
        setColor(color);

        GL11.glBegin(7);

        for (char c : s.toCharArray()) {
            if (c == '\n') {
                y += font.getHeight();
            }
            float width = font.charWidth(c);
            float height = font.getHeight();
            float u = 1.0F / font.getFontImageWidth() * font.getCharX(c);
            float v = 1.0F / font.getFontImageHeight() * font.getCharY(c);
            float u2 = u + 1.0F / font.getFontImageWidth() * width;
            float v2 = v + 1.0F / font.getFontImageHeight() * height;

            GL11.glTexCoord2f(u, v);
            GL11.glVertex2f(x, y);

            GL11.glTexCoord2f(u2, v);
            GL11.glVertex2f(x + width, y);

            GL11.glTexCoord2f(u2, v2);
            GL11.glVertex2f(x + width, y + height);

            GL11.glTexCoord2f(u, v2);
            GL11.glVertex2f(x, y + height);

            x = (int) (x + width);
        }

        GL11.glEnd();
    }

    public static void drawString(String s, int x, int y, Color color) {
        drawString(s, UIFontManager.getDefault(), x, y, color);
    }

    public static void renderObject(RenderObject object) {
        object.update();

        resetView();
        setTranslation(-object.pivot.x, -object.pivot.y);
        setRotation(object.angle);
        setTranslation(object.position.x + object.pivot.x, object.position.y + object.pivot.y);

        if (object.material != null) {
            object.material.bind();
        }

        object.prerender();
        object.render();
        object.postrender();

        if (object.material != null) {
            object.material.unbind();
        }

        resetView();
    }
}
