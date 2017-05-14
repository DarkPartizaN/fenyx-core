package com.fenyx.utils;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AWTImage {

    public static final ColorModel alphaColorModel = new java.awt.image.ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 8}, true, false, 3, 0);
    public static final ColorModel simpleColorModel = new java.awt.image.ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 0}, false, false, 1, 0);

    public String name;
    public int width;
    public int height;
    private final BufferedImage target_image;
    private final Graphics2D target_graphics;
    public boolean has_alpha;
    public ByteBuffer raw;

    public AWTImage(String name, BufferedImage source_image) {
        this.name = name;
        this.width = source_image.getWidth();
        this.height = source_image.getHeight();

        if (source_image.getColorModel().hasAlpha()) {
            WritableRaster raster = Raster.createInterleavedRaster(0, source_image.getWidth(), source_image.getHeight(), 4, null);
            this.target_image = new BufferedImage(alphaColorModel, raster, false, null);

            this.has_alpha = true;
        } else {
            WritableRaster raster = Raster.createInterleavedRaster(0, source_image.getWidth(), source_image.getHeight(), 3, null);
            this.target_image = new BufferedImage(simpleColorModel, raster, false, null);

            this.has_alpha = false;
        }

        this.target_graphics = this.target_image.createGraphics();
        this.target_graphics.drawImage(source_image, 0, 0, null);

        byte[] data = ((DataBufferByte) this.target_image.getRaster().getDataBuffer()).getData();

        ByteBuffer imageData = BufferUtils.createByteBuffer(data.length);
        imageData.order(ByteOrder.nativeOrder());
        imageData.put(data, 0, data.length);
        imageData.flip();

        this.raw = imageData;
    }

    public void refreshImage(BufferedImage source_image) {
        this.target_graphics.drawImage(source_image, 0, 0, null);

        byte[] data = ((DataBufferByte) this.target_image.getRaster().getDataBuffer()).getData();

        ByteBuffer imageData = BufferUtils.createByteBuffer(data.length);
        imageData.order(ByteOrder.nativeOrder());
        imageData.put(data, 0, data.length);
        imageData.flip();

        this.raw = imageData;
    }
}
