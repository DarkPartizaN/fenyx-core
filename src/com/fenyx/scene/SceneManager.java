package com.fenyx.scene;


public class SceneManager
{
  private Scene[] scenes;
  
  private Scene[] s_tmp;
  
  private int size = 0;
  private final int initialSize = 10;
  private Scene current_scene;
  
  public SceneManager() {
    this.scenes = new Scene[10];
    this.s_tmp = new Scene[10];
  }
  
  public void add(Scene scene) {
    if (contains(scene)) { return;
    }
    this.size += 1;
    
    if (this.size > this.scenes.length - 1) {
      Scene[] tmp = new Scene[this.size + 10];
      System.arraycopy(this.scenes, 0, tmp, 0, this.scenes.length);
      this.scenes = tmp;
    }
    
    this.scenes[(this.size - 1)] = scene;
  }
  
  public Scene get(String name) {
    for (Scene s : this.scenes) if (s.name.equals(name)) return s;
    return null;
  }
  
  public Scene[] get_all() {
    if (this.s_tmp.length != this.size) {
      this.s_tmp = new Scene[this.size];
    }
    System.arraycopy(this.scenes, 0, this.s_tmp, 0, this.size);
    
    return this.s_tmp;
  }
  
  public void remove(int i) {
    if (is_empty()) { return;
    }
    this.scenes[i] = null;
    
    Scene[] tmp = new Scene[this.scenes.length - 1];
    System.arraycopy(this.scenes, 0, tmp, 0, i);
    System.arraycopy(this.scenes, i + 1, tmp, i, this.size - (i + 1));
    this.scenes = tmp;
    
    this.size -= 1;
  }
  
  public void remove(Scene scene) {
    if (is_empty()) { return;
    }
    int i = 0;
    
    for (Scene s : this.scenes) {
      if (s == scene) {
        remove(i);
        return;
      }
      
      i++;
    }
  }
  
  public void remove(String name) {
    if (is_empty()) { return;
    }
    int i = 0;
    
    for (Scene s : this.scenes) {
      if (name.equals(s.name)) {
        remove(i);
        return;
      }
      
      i++;
    }
  }
  
  public void clear() {
    this.scenes = new Scene[10];
    this.s_tmp = new Scene[10];
    this.size = 0;
  }
  
  public int size() {
    return this.size;
  }
  
  public boolean is_empty() {
    return this.size == 0;
  }
  
  public boolean contains(Scene scene) {
    for (Scene tmp : this.scenes) if (tmp == scene) return true;
    return false;
  }
  
  public boolean contains(String name) {
    for (Scene s : this.scenes) if (s.name.equals(name)) return true;
    return false;
  }
  
  public void setCurrent(Scene scene) {
    this.current_scene = scene;
  }
  
  public Scene getCurrent() {
    return this.current_scene;
  }
  
  public boolean hasCurrent() {
    return this.current_scene != null;
  }
}