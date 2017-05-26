package com.fenyx.ui;

import com.fenyx.render.Texture;

import java.awt.FontMetrics;
import java.util.HashMap;

public class UIFont {

    private HashMap<Integer, String> key_table;
    public Texture font_texture;
    public FontMetrics font_metrics;

    void setKeyTable(HashMap<Integer, String> table) {
        key_table = table;
    }

    public HashMap<Integer, String> getKeyTable() {
        return key_table;
    }

    public float getCharX(char c) {
        String originStr = String.valueOf(c);

        for (String s : key_table.values()) {
            if (s.contains(originStr)) {
                originStr = s;
            }
        }
        return (float) font_metrics.getStringBounds(originStr.substring(0, originStr.indexOf(c)), null).getWidth();
    }

    public float getCharY(char c) {
        float line = 0f;

        for (int i : key_table.keySet()) {
            if (key_table.get(i).contains(String.valueOf(c))) {
                line = i;
            }
        }

        return getHeight() * line;
    }

    public int getHeight() {
        return this.font_metrics.getHeight();
    }

    public float charWidth(int c) {
        return this.font_metrics.charWidth(c);
    }

    public int stringWidth(String s) {
        if (s == null || s.isEmpty()) return 0;

        return this.font_metrics.stringWidth(s);
    }

    public float getFontImageWidth() {
        return font_texture.width;
    }

    public float getFontImageHeight() {
        return font_texture.height;
    }

    public Texture getFontImage() {
        return font_texture;
    }
}