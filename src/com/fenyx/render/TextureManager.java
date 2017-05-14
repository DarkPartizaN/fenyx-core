package com.fenyx.render;

import com.fenyx.utils.AWTImage;
import java.util.HashMap;

public class TextureManager {

    private static final HashMap<String, Texture> cached_textures = new HashMap();

    public static Texture createTexture(int width, int height) {
        return createTexture(width, height, TextureFormat.getDefault());
    }

    public static Texture createTexture(int width, int height, boolean alpha) {
        return createTexture(width, height, TextureFormat.getDefaultAlpha());
    }

    public static Texture createTexture(int width, int height, TextureFormat format) {
        Texture tmp = new Texture();
        tmp.width = width;
        tmp.height = height;
        tmp.setFormat(format);
        tmp.compile();

        return tmp;
    }

    public static Texture createTexture(AWTImage image) {
        if (!cached_textures.containsKey(image.name)) {
            Texture tmp = new Texture();
            tmp.width = image.width;
            tmp.height = image.height;
            tmp.raw = image.raw;

            if (image.has_alpha) {
                tmp.setFormat(TextureFormat.getDefaultAlpha());
            } else {
                tmp.setFormat(TextureFormat.getDefault());
            }
            tmp.compile();

            cached_textures.put(image.name, tmp);
        }

        return (Texture) cached_textures.get(image.name);
    }

    public static void updateTexture(Texture t, AWTImage image) {
        t.raw = image.raw;
        t.compile();
    }

    public static Texture getTexture(String name) {
        if (cached_textures.containsKey(name)) {
            return (Texture) cached_textures.get(name);
        }
        return getTexture("NULL");
    }
}
