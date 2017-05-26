package com.fenyx.utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public final class ResourceUtils {

    public static final String dir_root = new File(".").getAbsoluteFile().getParentFile().getAbsolutePath().concat("/");

    public static String dir_images = "res/gfx/";
    public static String dir_maps = "res/maps/";
    public static String dir_fonts = "res/fonts/";
    public static String dir_shaders = "res/shaders/";
    public static String dir_current = dir_root;

    private static final HashMap<String, AWTImage> cached_images = new HashMap();
    public static final AWTImage null_image = createNullImage();

    private static boolean use_root = true;

    public static void useRoot(boolean use) {
        use_root = use;
    }

    public static void setCurrentDir(String path) {
        dir_current = path;
    }

    public static boolean isFileExists(String path) {
        return new File(path).exists();
    }

    public static String[] listFiles(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
    }

    public static File createFile(String path) {
        File file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException localIOException) {
            }
        }
        return file;
    }

    public static byte[] loadRaw(String path) {
        byte[] bytes;
        InputStream is;

        try {
            is = new FileInputStream(path);
            bytes = new byte[is.available()];

            is.read(bytes);
            is.close();
        } catch (IOException e) {
            return null;
        }

        return bytes;
    }

    public static String loadFile(String file) {
        int c;
        InputStream is;
        String tmp = "";

        if (use_root)
            file = dir_root.concat(file);
        try {
            is = new FileInputStream(file);

            while ((c = is.read()) != -1) {
                tmp = tmp + (char) c;
            }
            is.close();
        } catch (IOException e) {
            System.out.println("Can't load file:" + file);
            return null;
        }

        return tmp;
    }

    public static String loadLines(String file) {
        String tmp = new String();

        if (use_root) file = dir_root.concat(file);

        try {
            try (InputStream is = new FileInputStream(file)) {
                Scanner scan = new Scanner(is);
                
                while (scan.hasNextLine()) {
                    tmp = tmp.concat(scan.nextLine()).concat("\n");
                }
            }
        } catch (IOException localIOException) {
        }

        return tmp;
    }

    public static String[] loadLinesArray(String file) {
        return StringUtils.splitString(loadLines(file), "\n");
    }

    private static AWTImage createNullImage() {
        BufferedImage img = new BufferedImage(32, 32, 1);
        Graphics2D g = img.createGraphics();

        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, 32, 32);

        g.setColor(java.awt.Color.PINK);
        for (int y = 0; y < 32; y += 16) {
            for (int x = 0; x < 32; x += 16) {
                g.fillRect(x, y, 8, 8);
                g.fillRect(x + 8, y + 8, 8, 8);
            }
        }

        return new AWTImage("NULL_IMAGE", img);
    }

    public static AWTImage loadAWTImage(String name) {
        if (!cached_images.containsKey(name)) {
            try {
                BufferedImage source_image = javax.imageio.ImageIO.read(new File(dir_root.concat(dir_images).concat(name)));
                AWTImage out = new AWTImage(name, source_image);

                cached_images.put(name, out);
            } catch (IOException ex) {
                return null_image;
            }
        }

        return (AWTImage) cached_images.get(name);
    }

    public static Font loadTTF(String font_name, int size) {
        try {
            return Font.createFont(0, new File(dir_root.concat(dir_fonts).concat(font_name).concat(".ttf"))).deriveFont(size);
        } catch (java.awt.FontFormatException | IOException ex) {
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            return gc.createCompatibleImage(1, 1).createGraphics().getFont().deriveFont(size);
        }
    }

    public static Font loadTTF(String font_name, int size, int flags) {
        try {
            return Font.createFont(0, new File(dir_root.concat(dir_fonts).concat(font_name).concat(".ttf"))).deriveFont(flags, size);
        } catch (java.awt.FontFormatException | IOException ex) {
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            return gc.createCompatibleImage(1, 1, 3).createGraphics().getFont().deriveFont(size);
        }
    }
}
