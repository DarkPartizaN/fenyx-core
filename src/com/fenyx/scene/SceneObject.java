package com.fenyx.scene;

import com.fenyx.api.EngineAPI;
import com.fenyx.api.EngineTimer;
import com.fenyx.geom.Circle;
import com.fenyx.geom.Matrix;
import com.fenyx.geom.Point;
import com.fenyx.geom.Rect;
import com.fenyx.geom.Shape;
import com.fenyx.geom.Vector2;
import com.fenyx.render.RenderObject;

public abstract class SceneObject {

    public static final int STATIC = 0;
    public static final int DYNAMIC = 1;
    public int type;
    protected Vector2 position;
    protected Vector2 old_position;
    protected Vector2 pivot;
    protected float angle;
    protected float speed;
    public Vector2 current_speed;
    protected Matrix transform;
    protected Rect bbox;
    protected Shape cbox;
    public boolean solid = true;
    public boolean touchable = true;
    public boolean trigger = false;
    public boolean check_bounds = true;

    protected String name;

    protected boolean visible;
    public boolean always_visible = false;
    protected boolean nomodel = true;

    protected RenderObject render_object;
    private long birthtime;
    private long lifetime;
    private long killtime;
    private boolean killme;
    protected SceneObject target;
    protected SceneObject parent;
    protected SceneObjectPool childs;
    protected SceneObjectPool collides;
    protected SceneObjectPool touched;

    public SceneObject() {
        position = new Vector2();
        old_position = new Vector2();
        pivot = new Vector2();
        current_speed = new Vector2();
        transform = new Matrix();
        bbox = new Rect(0.0F, 0.0F, 0.0F, 0.0F);
        cbox = new Rect(0.0F, 0.0F, 0.0F, 0.0F);
        collides = new SceneObjectPool();
        touched = new SceneObjectPool();
        childs = new SceneObjectPool();
    }

    public abstract void init();

    public abstract void update();

    public abstract void destroy();

    public final float getX() {
        return position.x;
    }

    public final float getY() {
        return position.y;
    }

    public final Vector2 getPosition() {
        return position;
    }

    public final void setPosition(float x, float y) {
        old_position = position;

        position.x = x;
        position.y = y;

        refreshAll();
    }

    private final Vector2 tmp_speed = new Vector2();

    public final void move(float x, float y) {
        if (type == STATIC)
            return;

        tmp_speed.x = x;
        tmp_speed.y = y;

        move(tmp_speed);
    }

    public final void move(Vector2 speed) {
        if (type == STATIC)
            return;

        old_position = position;

        if (EngineAPI.getFrametime() > 0)
            speed = speed.mul(EngineAPI.getFrametime());

        position = position.add(speed);

        refreshAll();
    }

    public boolean isMoving() {
        return current_speed.length() > 0;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public final void setAngle(float angle) {
        if (angle < -180.0F)
            angle = 180.0F;
        if (angle >= 180.0F)
            angle = -180.0F;

        this.angle = angle;

        refreshAll();
    }

    public final void setPivot(float x, float y) {
        pivot.x = x;
        pivot.y = y;
    }

    public final void setPivot(Vector2 point) {
        pivot = point;
    }

    public final Point getPivot() {
        return pivot;
    }

    public final float getAngle() {
        return angle;
    }

    public final void setBounds(float w, float h) {
        bbox = new Rect(0.0F, 0.0F, w, h);
    }

    public final Shape getBBOX() {
        return bbox;
    }

    public float getWidth() {
        return getBBOX().getWidth();
    }

    public float getHeight() {
        return getBBOX().getHeight();
    }

    public final void setCollisionRect(float x, float y, float w, float h) {
        cbox = new Rect(x - this.pivot.x, y - this.pivot.y, w, h);
    }

    public final void setCollisionCircle(float centerx, float centery, int radius, int precize) {
        cbox = new Circle(centerx, centery, radius, precize);
    }

    public final Shape getCBOX() {
        return cbox;
    }

    public final Matrix getMatrix() {
        return transform;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final boolean isVisible() {
        return visible;
    }

    public void print() {
    }

    public void setRenderObject(RenderObject ro) {
        render_object = ro;
        nomodel = false;

        ro.parent = this;
    }

    public RenderObject getRenderObject() {
        return render_object;
    }

    public final boolean hasRenderObject() {
        return (!nomodel) && (render_object != null);
    }

    public final void setTarget(SceneObject target) {
        this.target = target;
    }

    public final boolean hasTarget() {
        return target != null;
    }

    public final void setCurrentSpeed(Vector2 speed) {
        current_speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public final void refreshBBOX() {
        bbox.reset();

        transform = Matrix.translate(-this.pivot.x, -this.pivot.y);
        transform.transform(this.bbox);

        transform = Matrix.rotate(this.angle);
        transform.transform(this.bbox);

        transform = Matrix.translate(getX(), getY());
        transform.transform(this.bbox);
    }

    public final void refreshCBOX() {
        cbox.reset();

        transform = Matrix.rotate(this.angle);
        transform.transform(this.cbox);

        transform = Matrix.translate(getX(), getY());
        transform.transform(this.cbox);
    }

    public final void refreshAll() {
        refreshBBOX();
        refreshCBOX();

        if (hasRenderObject()) {
            render_object.position = this.position;
            render_object.pivot = this.pivot;
            render_object.angle = this.angle;
        }
    }

    public void refreshRenderObject() {
        render_object.position = position;
        render_object.pivot = pivot;
        render_object.angle = angle;
    }

    public void imBirth() {
        birthtime = EngineTimer.time;
    }

    public long getBirthTime() {
        return birthtime;
    }

    public void setKillTime(long ms) {
        killtime = (EngineTimer.time + ms);
    }

    public void calcLifetime() {
        lifetime = (EngineTimer.time - this.birthtime);
    }

    public long getLifeTime() {
        return lifetime;
    }

    public long getKillTime() {
        return killtime;
    }

    public void markToRemove() {
        killme = true;
    }

    public boolean shouldRemove() {
        return killme;
    }

    public final void addChild(SceneObject obj) {
        obj.parent = this;
        childs.add(obj);
    }

    public final SceneObjectPool getChilds() {
        return childs;
    }

    public final void addCollision(SceneObject e) {
        if ((e.trigger) || (e == this)) return;

        collides.add(e);
    }

    public final void removeCollision(SceneObject e) {
        collides.remove(e);
    }

    public final boolean hasCollisions() {
        return !collides.is_empty();
    }

    public final boolean canCollide() {
        return ((solid || trigger) && (cbox != null));
    }

    public final SceneObjectPool getCollides() {
        return collides;
    }

    public void processCollisions() {
    }

    public void touch(SceneObject obj) {
    }

    public void addTouch(SceneObject e) {
        if (e == this) return;

        touched.add(e);
    }

    public final void removeTouch(SceneObject e) {
        touched.remove(e);
    }

    public final boolean hasTouch() {
        return !touched.is_empty();
    }

    public final SceneObjectPool getTouched() {
        return touched;
    }

    public final boolean isTouchable() {
        return touchable;
    }
}
