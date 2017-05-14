package com.fenyx.scene;

public class SceneObjectPool {

    private SceneObject[] e;
    private SceneObject[] e_tmp;

    private int size = 0;
    private final int initialSize = 10;

    public SceneObjectPool() {
        e = new SceneObject[initialSize];
        e_tmp = new SceneObject[initialSize];
    }

    public SceneObjectPool(int initial) {
        if (initial > 0) {
            e = new SceneObject[initial];
            e_tmp = new SceneObject[initial];
        } else {
            e = new SceneObject[initialSize];
            e_tmp = new SceneObject[initialSize];
        }
    }

    public void add(SceneObject object) {
        if (contains(object)) return;

        size += 1;

        if (size > e.length - 1) {
            SceneObject[] tmp = new SceneObject[size + 10];
            System.arraycopy(e, 0, tmp, 0, e.length);
            e = tmp;
        }

        e[(size - 1)] = object;
    }

    public void add(SceneObject[] entities) {
        for (SceneObject tmp : entities)
            add(tmp);
    }

    public void put(SceneObject object, int pos) {
        if (pos > size - 1) {
            add(object);
        } else
            e[pos] = object;
    }

    public void swap(int i, int i2) {
        SceneObject tmp = get(i);
        e[i] = e[i2];
        e[i2] = tmp;
    }

    public SceneObject get(int i) {
        if (i > size - 1)
            return null;
        return e[i];
    }

    public SceneObject[] get_all() {
        if (e_tmp.length != size) {
            e_tmp = new SceneObject[size];
        }
        System.arraycopy(e, 0, e_tmp, 0, size);

        return e_tmp;
    }

    public SceneObject get_last() {
        return get(size - 1);
    }

    public SceneObject pop() {
        SceneObject t = get_last();

        SceneObject[] tmp = new SceneObject[e.length - 1];
        System.arraycopy(e, 0, tmp, 0, size - 1);
        e = tmp;

        size -= 1;

        return t;
    }

    public void remove(int i) {
        e[i] = null;

        SceneObject[] tmp = new SceneObject[e.length - 1];
        System.arraycopy(e, 0, tmp, 0, i);
        System.arraycopy(e, i + 1, tmp, i, size - (i + 1));
        e = tmp;

        size -= 1;
    }

    public void remove(SceneObject object) {
        if (is_empty()) return;

        int i = 0;
        for (SceneObject a : e) {
            if (a == object) {
                remove(i);
                return;
            }

            i++;
        }
    }

    public void clear() {
        e = new SceneObject[10];
        e_tmp = new SceneObject[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean is_empty() {
        return size == 0;
    }

    public boolean contains(SceneObject object) {
        for (SceneObject tmp : e)
            if (tmp == object)
                return true;
        return false;
    }

    public void invert() {
        for (int i = 0; i < e.length; i++) {
            SceneObject temp = e[i];
            e[i] = e[(e.length - i - 1)];
            e[(e.length - i - 1)] = temp;
        }
    }
}
