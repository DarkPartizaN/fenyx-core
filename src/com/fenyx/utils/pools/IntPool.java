package com.fenyx.utils.pools;

public class IntPool {

    private int[] e;
    private int[] e_tmp;
    private int size;

    private final int initialSize = 10;

    public IntPool() {
        this.e = new int[initialSize];
        this.e_tmp = new int[initialSize];
    }

    public IntPool(int initial) {
        if (initial > 0) {
            this.e = new int[initial];
            this.e_tmp = new int[initial];
        } else {
            this.e = new int[10];
            this.e_tmp = new int[10];
        }
    }

    public void put(int i) {
        this.size += 1;

        if (this.size > this.e.length) {
            int[] tmp = new int[this.size + 10];
            System.arraycopy(this.e, 0, tmp, 0, this.e.length);
            this.e = tmp;
        }

        this.e[(this.size - 1)] = i;
    }

    public void put(int i, int pos) {
        if (pos > this.size - 1) {
            put(i);
        } else
            this.e[pos] = i;
    }

    public int get(int i) {
        if (i > this.size - 1)
            return 64537;
        return this.e[i];
    }

    public int[] get_all() {
        this.e_tmp = new int[this.size];
        System.arraycopy(this.e, 0, this.e_tmp, 0, this.size);

        return this.e_tmp;
    }

    public int get_last() {
        return get(this.size - 1);
    }

    public int pop() {
        int t = get_last();

        int[] tmp = new int[this.e.length - 1];
        System.arraycopy(this.e, 0, tmp, 0, this.size - 1);
        this.e = tmp;

        this.size -= 1;

        return t;
    }

    public void remove(int i) {
        int[] tmp = new int[this.size - 1];

        System.arraycopy(this.e, 0, tmp, 0, i);
        System.arraycopy(this.e, i + 1, tmp, i, this.size - (i + 1));
        this.e = tmp;

        this.size -= 1;
    }

    public void clear() {
        this.e = new int[10];
        this.e_tmp = new int[10];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }
}
