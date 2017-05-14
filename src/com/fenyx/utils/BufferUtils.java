package com.fenyx.utils;

import java.nio.*;

public class BufferUtils {

    public static ByteBuffer createByteBuffer(int size) {
        ByteBuffer buff = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());

        return buff;
    }

    public static IntBuffer createIntBuffer(int size) {
        IntBuffer buff = createByteBuffer(size * 4).asIntBuffer();

        return buff;
    }

    public static LongBuffer createLongBuffer(int size) {
        LongBuffer buff = createByteBuffer(size * 4).asLongBuffer();

        return buff;
    }

    public static FloatBuffer createFloatBuffer(int size) {
        FloatBuffer buff = createByteBuffer(size * 4).asFloatBuffer();

        return buff;
    }

    public static DoubleBuffer createDoubleBuffer(int size) {
        DoubleBuffer buff = createByteBuffer(size * 4).asDoubleBuffer();

        return buff;
    }
}
