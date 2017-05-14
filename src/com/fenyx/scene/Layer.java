package com.fenyx.scene;

import com.fenyx.api.EngineAPI;
import com.fenyx.api.EngineTimer;

public final class Layer {

    private SceneObjectPool objects;
    private SceneObjectPool visible_objects;
    private String name;

    public Layer() {
        this.objects = new SceneObjectPool();
        this.visible_objects = new SceneObjectPool();
        this.name = "";
    }

    public Layer(String name) {
        this();
        this.name = name;
    }

    public void addObject(SceneObject obj) {
        obj.imBirth();
        objects.add(obj);

        for (SceneObject p : obj.getChilds().get_all())
            addObject(p);

        obj.processCollisions();
    }

    public void removeObject(SceneObject obj) {
        obj.destroy();

        for (SceneObject c : obj.collides.get_all()) {
            c.collides.remove(obj);
            c.touched.remove(obj);
        }

        this.objects.remove(obj);
    }

    public SceneObjectPool getObjects() {
        return this.objects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public SceneObjectPool getVisible() {
        Camera camera = EngineAPI.getCurrentScene().getCamera();

        this.visible_objects.clear();

        for (SceneObject e : getObjects().get_all()) {
            if ((e.isVisible()) && (((e.getBBOX().getMaxX() >= camera.getX())
                    && (e.getBBOX().getMinX() <= camera.getX() + camera.getWidth())
                    && (e.getBBOX().getMaxY() >= camera.getY())
                    && (e.getBBOX().getMinY() <= camera.getY() + camera.getHeight())) || (e.always_visible))) {
                this.visible_objects.add(e);
            }
        }

        return this.visible_objects;
    }

    public void update() {
        for (SceneObject obj : getObjects().get_all()) {
            if ((EngineTimer.time == obj.getKillTime()) || (obj.shouldRemove())) {
                removeObject(obj);
            } else {
                obj.calcLifetime();
                obj.update();

                if (obj.check_bounds) {
                    if (obj.getBBOX().getMinX() < 0.0F)
                        obj.setPosition(obj.getBBOX().getSize().x / 2.0F, obj.getY());
                    if (obj.getBBOX().getMinY() < 0.0F)
                        obj.setPosition(obj.getX(), obj.getBBOX().getSize().y / 2.0F);
                    if (obj.getBBOX().getMinX() + obj.getBBOX().getSize().x >= EngineAPI.getCurrentScene().getWidth())
                        obj.setPosition(EngineAPI.getCurrentScene().getWidth() - obj.getBBOX().getSize().x / 2.0F, obj.getY());
                    if (obj.getBBOX().getMinY() + obj.getBBOX().getSize().y >= EngineAPI.getCurrentScene().getHeight()) {
                        obj.setPosition(obj.getX(), EngineAPI.getCurrentScene().getHeight() - obj.getBBOX().getSize().y / 2.0F);
                    }
                }
                if (obj.hasRenderObject())
                    obj.refreshRenderObject();
            }
        }
    }
}
