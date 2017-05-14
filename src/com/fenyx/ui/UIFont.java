package com.fenyx.ui;

import com.fenyx.render.Texture;

import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.Iterator;

public class UIFont {

    private HashMap<Integer, String> key_table;
    public Texture font_texture;
    public FontMetrics font_metrics;

    void setKeyTable(HashMap table) {
        this.key_table = table;
    }

    public HashMap<Integer, String> getKeyTable() {
        return this.key_table;
    }

    public float getCharX(char c) {
        String originStr = String.valueOf(c);

        for (String s : this.key_table.values()) {
            if (s.contains(originStr)) {
                originStr = s;
            }
        }
        return (float) this.font_metrics.getStringBounds(originStr.substring(0, originStr.indexOf(c)), null).getWidth();
    }

    public float getCharY(char c) {
        float line = 0.0F;

        for (Iterator localIterator = this.key_table.keySet().iterator(); localIterator.hasNext();) {
            int i = ((Integer) localIterator.next());
            if (((String) this.key_table.get(i)).contains(String.valueOf(c))) {
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
        if (s == null)
            return 0;
        return this.font_metrics.stringWidth(s);
    }

    public float getFontImageWidth() {
        float w = 0.0F;
        for (String s : this.key_table.values()) {
            float a = (float) this.font_metrics.getStringBounds(s, null).getWidth();
            if (a > w) {
                w = a;
            }
        }
        return w;
    }

    public float getFontImageHeight() {
        return this.key_table.keySet().size() * getHeight();
    }

    public Texture getFontTexture() {
        return this.font_texture;
    }
}
