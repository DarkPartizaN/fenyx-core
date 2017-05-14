package com.fenyx.render;

import com.fenyx.geom.Rect;
import com.fenyx.geom.Shape;
import com.fenyx.geom.Vector2;
import com.fenyx.scene.SceneObject;

public abstract class RenderObject {

    public SceneObject parent;
    public Vector2 position;
    public Vector2 pivot;
    public float angle;
    public Shape shape;
    protected Material material;

    public RenderObject() {
        position = new Vector2();
        pivot = new Vector2();
        shape = new Rect();

        material = new Material();
        material.color = Color.white;
    }

    public abstract void init();
    public abstract void update();
    public abstract void prerender();
    public abstract void render();
    public abstract void postrender();

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
