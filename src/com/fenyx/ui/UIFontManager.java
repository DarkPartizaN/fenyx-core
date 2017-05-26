package com.fenyx.ui;

import com.fenyx.render.Texture;
import com.fenyx.render.TextureFormat;
import com.fenyx.utils.AWTImage;
import com.fenyx.utils.BufferUtils;
import com.fenyx.utils.ResourceUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class UIFontManager {

    private static final HashMap<String, UIFont> cached_fonts = new HashMap();
    private static UIFont default_font = null;

    public static final int FLAG_PLAIN = 0;
    public static final int FLAG_BOLD = 1;

    //Avaliable symbols
    public static final HashMap<Integer, String> key_table = new HashMap<Integer, String>() {
        {
            put(0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            put(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());
            put(2, "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
            put(3, "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toLowerCase());
            put(4, "0123456789");
            put(5, " $+-*/=%\"'#@&_(),.;:?!\\|<>[]§`^~");
        }
    };

    public static UIFont getDefault() {
        if (default_font == null)
            default_font = createFont("default", ResourceUtils.loadTTF("", 12), key_table);

        return default_font;
    }

    public static UIFont getDefault(int size) {
        return createFont("default", ResourceUtils.loadTTF("", size), key_table);
    }

    public static UIFont getDefault(int size, int flags) {
        return createFont("default", ResourceUtils.loadTTF("", size), key_table);
    }

    public static UIFont createFont(String name, Font font) {
        return createFont(name, font, key_table);
    }

    public static UIFont createFont(String name, Font font, HashMap<Integer, String> key_table) {
        UIFont tmp = new UIFont();
        tmp.setKeyTable(key_table);

        Graphics2D graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        graphics.setFont(font);

        tmp.font_metrics = graphics.getFontMetrics();

        //Set size
        float w = 0f;

        for (String s : key_table.values()) {
            float a = (float) tmp.font_metrics.getStringBounds(s, null).getWidth();
            if (a > w) w = a;
        }

        float h = key_table.keySet().size() * tmp.font_metrics.getHeight();

        WritableRaster raster = Raster.createInterleavedRaster(0, (int) w, (int) h, 4, null);
        BufferedImage source_image = new BufferedImage(AWTImage.alphaColorModel, raster, false, null);

        Graphics2D g = source_image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.white);

        for (int i : tmp.getKeyTable().keySet())
            g.drawString((String) tmp.getKeyTable().get(i), 0, tmp.font_metrics.getMaxAscent() + tmp.getHeight() * i);

        byte[] data = ((DataBufferByte) source_image.getRaster().getDataBuffer()).getData();

        ByteBuffer imageData = BufferUtils.createByteBuffer(data.length);
        imageData.put(data, 0, data.length);
        imageData.flip();

        tmp.font_texture = new Texture();
        tmp.font_texture.width = source_image.getWidth();
        tmp.font_texture.height = source_image.getHeight();
        tmp.font_texture.raw = imageData;
        tmp.font_texture.setFormat(TextureFormat.getDefaultAlpha());
        tmp.font_texture.compile();

        cached_fonts.put(name, tmp);

        return tmp;
    }
}
