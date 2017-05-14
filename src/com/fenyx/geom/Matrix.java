package com.fenyx.geom;

import com.fenyx.utils.BufferUtils;
import com.fenyx.utils.MathUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public final class Matrix {

    private static final float[] identity = 
    {
     1.0F, 0.0F, 0.0F, 0.0F,
     0.0F, 1.0F, 0.0F, 0.0F,
     0.0F, 0.0F, 1.0F, 0.0F,
     0.0F, 0.0F, 0.0F, 1.0F
    };

    public float[] m = new float[16];
    private final FloatBuffer buff = BufferUtils.createFloatBuffer(16);
    public boolean is_identity = true;

    public Matrix() {
        reset();
    }

    public void set(float[] m) {
        System.arraycopy(m, 0, this.m, 0, m.length);
        if (Arrays.equals(m, identity))
            this.is_identity = true;
    }

    public void set(Matrix m) {
        set(m.m);
        this.is_identity = m.is_identity;
    }

    public float[] get() {
        return this.m;
    }

    public FloatBuffer gl_mat() {
        this.buff.clear();
        this.buff.put(this.m);
        this.buff.flip();

        return this.buff;
    }

    public void reset() {
        this.m[0] = 1.0F;
        this.m[1] = 0.0F;
        this.m[2] = 0.0F;
        this.m[3] = 0.0F;

        this.m[4] = 0.0F;
        this.m[5] = 1.0F;
        this.m[6] = 0.0F;
        this.m[7] = 0.0F;

        this.m[8] = 0.0F;
        this.m[9] = 0.0F;
        this.m[10] = 1.0F;
        this.m[11] = 0.0F;

        this.m[12] = 0.0F;
        this.m[13] = 0.0F;
        this.m[14] = 0.0F;
        this.m[15] = 1.0F;

        this.is_identity = true;
    }

    public Matrix get_translation() {
        return translate(this.m[12], this.m[13]);
    }

    public Matrix get_rotation() {
        Matrix mat_tmp = new Matrix();

        mat_tmp.m[0] = this.m[0];
        mat_tmp.m[1] = this.m[1];
        mat_tmp.m[4] = this.m[4];
        mat_tmp.m[5] = this.m[5];

        return mat_tmp;
    }

    public static Matrix translate(float x, float y) {
        Matrix mat_tmp = new Matrix();

        mat_tmp.m[12] = x;
        mat_tmp.m[13] = y;
        mat_tmp.m[14] = 0.0F;

        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    public static Matrix translate(float x, float y, float z) {
        Matrix mat_tmp = new Matrix();

        mat_tmp.m[12] = x;
        mat_tmp.m[13] = y;
        mat_tmp.m[14] = z;

        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    public static Matrix rotate(float angle) {
        Matrix mat_tmp = new Matrix();

        float sin = MathUtils.sin(angle);
        float cos = MathUtils.cos(angle);

        mat_tmp.m[0] = cos;
        mat_tmp.m[1] = (-sin);
        mat_tmp.m[4] = sin;
        mat_tmp.m[5] = cos;

        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    public static Matrix scale(float x, float y) {
        Matrix mat_tmp = new Matrix();

        mat_tmp.m[0] = x;
        mat_tmp.m[5] = y;
        mat_tmp.m[10] = 1.0F;

        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    public Matrix invert() {
        int size = 4;
        float[] mass = new float[16];
        float[] tmp = new float[16];

        System.arraycopy(this.m, 0, mass, 0, this.m.length);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                tmp[(i + j * size)] = 0.0F;
            tmp[(i + i * size)] = 1.0F;
        }

        for (int i = 0; i < size; i++) {
            float a = mass[(i + i * size)];
            for (int j = i + 1; j < size; j++) {
                float b = mass[(j + i * size)];
                for (int k = 0; k < size; k++) {
                    mass[(j + k * size)] = (mass[(i + k * size)] * b - mass[(j + k * size)] * a);
                    tmp[(j + k * size)] = (tmp[(i + k * size)] * b - tmp[(j + k * size)] * a);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                float sum = 0.0F;
                for (int k = size - 1; k > j; k--) {
                    sum += mass[(j + k * size)] * tmp[(k + i * size)];
                }
                if (mass[(j + j * size)] == 0.0F) {
                    return null;
                }
                tmp[(j + i * size)] = ((tmp[(j + i * size)] - sum) / mass[(j + j * size)]);
            }
        }

        Matrix mat_tmp = new Matrix();
        mat_tmp.set(tmp);

        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    private static final float[] f = new float[16];

    public static Matrix mul(Matrix m1, Matrix m2) {
        f[0] = (m1.m[0] * m2.m[0] + m1.m[1] * m2.m[4] + m1.m[2] * m2.m[8] + m1.m[3] * m2.m[12]);
        f[1] = (m1.m[0] * m2.m[1] + m1.m[1] * m2.m[5] + m1.m[2] * m2.m[9] + m1.m[3] * m2.m[13]);
        f[2] = (m1.m[0] * m2.m[2] + m1.m[1] * m2.m[6] + m1.m[2] * m2.m[10] + m1.m[3] * m2.m[14]);
        f[3] = (m1.m[0] * m2.m[3] + m1.m[1] * m2.m[7] + m1.m[2] * m2.m[11] + m1.m[3] * m2.m[15]);

        f[4] = (m1.m[4] * m2.m[0] + m1.m[5] * m2.m[4] + m1.m[6] * m2.m[8] + m1.m[7] * m2.m[12]);
        f[5] = (m1.m[4] * m2.m[1] + m1.m[5] * m2.m[5] + m1.m[6] * m2.m[9] + m1.m[7] * m2.m[13]);
        f[6] = (m1.m[4] * m2.m[2] + m1.m[5] * m2.m[6] + m1.m[6] * m2.m[10] + m1.m[7] * m2.m[14]);
        f[7] = (m1.m[4] * m2.m[3] + m1.m[5] * m2.m[7] + m1.m[6] * m2.m[11] + m1.m[7] * m2.m[15]);

        f[8] = (m1.m[8] * m2.m[0] + m1.m[9] * m2.m[4] + m1.m[10] * m2.m[8] + m1.m[11] * m2.m[12]);
        f[9] = (m1.m[8] * m2.m[1] + m1.m[9] * m2.m[5] + m1.m[10] * m2.m[9] + m1.m[11] * m2.m[13]);
        f[10] = (m1.m[8] * m2.m[2] + m1.m[9] * m2.m[6] + m1.m[10] * m2.m[10] + m1.m[11] * m2.m[14]);
        f[11] = (m1.m[8] * m2.m[3] + m1.m[9] * m2.m[7] + m1.m[10] * m2.m[11] + m1.m[11] * m2.m[15]);

        f[12] = (m1.m[12] * m2.m[0] + m1.m[13] * m2.m[4] + m1.m[14] * m2.m[8] + m1.m[15] * m2.m[12]);
        f[13] = (m1.m[12] * m2.m[1] + m1.m[13] * m2.m[5] + m1.m[14] * m2.m[9] + m1.m[15] * m2.m[13]);
        f[14] = (m1.m[12] * m2.m[2] + m1.m[13] * m2.m[6] + m1.m[14] * m2.m[10] + m1.m[15] * m2.m[14]);
        f[15] = (m1.m[12] * m2.m[3] + m1.m[13] * m2.m[7] + m1.m[14] * m2.m[11] + m1.m[15] * m2.m[15]);

        Matrix mat_tmp = new Matrix();
        mat_tmp.set(f);
        mat_tmp.is_identity = false;

        return mat_tmp;
    }

    public void transform(Shape s) {
        transform(s.getPoints());
    }

    public void transform(Point p) {
        float x = p.x;
        float y = p.y;

        p.x = (x * this.m[0] + y * this.m[1] + this.m[12]);
        p.y = (x * this.m[4] + y * this.m[5] + this.m[13]);
    }

    public void transform(Point[] points) {
        for (Point p : points) {
            float x = p.x;
            float y = p.y;

            p.x = (x * this.m[0] + y * this.m[1] + this.m[12]);
            p.y = (x * this.m[4] + y * this.m[5] + this.m[13]);
        }
    }

    public void transform(Vector2 v) {
        float x = v.x;
        float y = v.y;

        v.x = (x * this.m[0] + y * this.m[1] + this.m[12]);
        v.y = (x * this.m[4] + y * this.m[5] + this.m[13]);
    }

    public void set(float f, int column, int row) {
        this.m[(column * 4 + row)] = f;
        this.is_identity = false;
    }

    public float get(int column, int row) {
        return this.m[(column * 4 + row)];
    }
}


/* Location:              F:\Users\пользователь\Documents\NetBeansProjects\AfterEngine2\2.6\AfterEngine-core\dist\AfterEngine-core.jar!\com\afterengine\geom\Matrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
