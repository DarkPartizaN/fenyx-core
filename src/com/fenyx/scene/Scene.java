package com.fenyx.scene;

import com.fenyx.api.ScreenConfig;
import com.fenyx.geom.Rect;
import com.fenyx.geom.Shape;




public class Scene
{
  protected Rect bounds;
  protected String name;
  protected Camera camera;
  protected LayerManager layer_manager;
  protected SceneObject actor;
  
  public Scene()
  {
    this.name = "";
    this.bounds = new Rect();
    this.layer_manager = new LayerManager();
    this.layer_manager.add(new Layer("default"));
    
    this.camera = new Camera(new Rect(0.0F, 0.0F, ScreenConfig.screen_width, ScreenConfig.screen_height));
    this.camera.setName("defaultCamera");
  }
  
  public void setBounds(float x, float y, float width, float height) {
    this.bounds.setPosition(x, y);
    this.bounds.setSize(width, height);
  }
  
  public Rect getBounds() {
    return this.bounds;
  }
  
  public float getWidth() {
    return this.bounds.w;
  }
  
  public float getHeight() {
    return this.bounds.h;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public Camera getCamera() {
    return this.camera;
  }
  
  public LayerManager getLayerManager() {
    return this.layer_manager;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  public SceneObject getObjectByName(String name) {
    for (Layer layer : this.layer_manager.get_all()) {
      for (SceneObject obj : layer.getObjects().get_all()) {
        if (obj.getName().equals(name))
          return obj;
      }
    }
    return null;
  }
  
  public Layer getDefaultLayer() {
    return this.layer_manager.get("default");
  }
  
  public void update()
  {
    if (this.camera != null) {
      this.camera.update();
      
      if (this.camera.check_bounds) {
        if (this.camera.getWidth() < this.bounds.w) {
          if (this.camera.getX() < this.bounds.x) this.camera.setPosition(this.bounds.x, this.camera.getY());
          if (this.camera.getX() + this.camera.getWidth() > this.bounds.w - this.bounds.x) this.camera.setPosition(this.bounds.w - this.bounds.x - this.camera.getWidth(), this.camera.getY());
        } else {
          if (this.camera.getX() > this.bounds.x) this.camera.setPosition(this.bounds.x, this.camera.getY());
          if (this.camera.getX() + this.camera.getWidth() < this.bounds.x + this.bounds.w) this.camera.setPosition(this.bounds.x + this.bounds.w - this.camera.getWidth(), this.camera.getY());
        }
        if (this.camera.getHeight() < this.bounds.h) {
          if (this.camera.getY() < this.bounds.y) this.camera.setPosition(this.camera.getX(), this.bounds.y);
          if (this.camera.getY() + this.camera.getHeight() > this.bounds.h - this.bounds.y) this.camera.setPosition(this.camera.getX(), this.bounds.h - this.bounds.y - this.camera.getHeight());
        } else {
          if (this.camera.getY() > this.bounds.y) this.camera.setPosition(this.camera.getX(), this.bounds.y);
          if (this.camera.getY() + this.camera.getHeight() < this.bounds.y + this.bounds.h) { this.camera.setPosition(this.camera.getX(), this.bounds.y + this.bounds.h - this.camera.getHeight());
          }
        }
      }
    }
    for (Layer layer : this.layer_manager.get_all()) { layer.update();
    }
    checkCollisions();
  }
  

  private final SceneObjectPool objects = new SceneObjectPool(128);
  

  private void checkCollisions()
  {
    this.objects.clear();
    for (Layer layer : this.layer_manager.get_all()) {
      for (SceneObject s : layer.getObjects().get_all())
        this.objects.add(s);
    }
    for (int i = 0; i < this.objects.size(); i++) {
      SceneObject obj1 = this.objects.get(i);
      
      for (int j = i + 1; j < this.objects.size(); j++) {
        SceneObject obj2 = this.objects.get(j);
        
        if (Shape.intersects(obj1.getBBOX(), obj2.getBBOX())) {
          if (obj2.isTouchable()) {
            obj1.addTouch(obj2);
            obj1.touch(obj2);
          }
          if (obj1.isTouchable()) {
            obj2.addTouch(obj1);
            obj2.touch(obj1);
          }
          
          if (Shape.intersects(obj1.getCBOX(), obj2.getCBOX())) {
            obj1.addCollision(obj2);
            obj2.addCollision(obj1);
          } else {
            obj1.removeCollision(obj2);
            obj2.removeCollision(obj1);
          }
        } else {
          obj1.removeTouch(obj2);
          obj2.removeTouch(obj1);
        }
      }
      
      obj1.processCollisions();
    }
  }
  
  public void setActor(SceneObject actor) {
    this.actor = actor;
  }
  
  public SceneObject getActor() {
    return this.actor;
  }
}