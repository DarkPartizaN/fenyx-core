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
import java.util.Iterator;

public class UIFontManager {

    private static final HashMap<String, UIFont> cached_fonts = new HashMap();
    private static UIFont default_font = null;

    public static final int FLAG_PLAIN = 0;
    public static final int FLAG_BOLD = 1;
    private static final HashMap<Integer, String> key_table = new HashMap() {
    };

    public static UIFont getDefault() {
        if (default_font == null) {
            default_font = createFont("default", ResourceUtils.loadTTF("", 12), key_table);
        }

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

    public static UIFont createFont(String name, Font font, HashMap key_table) {
        UIFont tmp = new UIFont();
        tmp.setKeyTable(key_table);

        Graphics2D graphics = new BufferedImage(1, 1, 2).createGraphics();
        graphics.setFont(font);

        tmp.font_metrics = graphics.getFontMetrics();

        WritableRaster raster = Raster.createInterleavedRaster(0, (int) tmp.getFontImageWidth(), (int) tmp.getFontImageHeight(), 4, null);
        BufferedImage source_image = new BufferedImage(AWTImage.alphaColorModel, raster, false, null);

        Graphics2D g = source_image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.white);

        for (Iterator localIterator = tmp.getKeyTable().keySet().iterator(); localIterator.hasNext();) {
            int i = ((Integer) localIterator.next());
            g.drawString((String) tmp.getKeyTable().get(i), 0, tmp.font_metrics.getMaxAscent() + tmp.getHeight() * i);
        }
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
