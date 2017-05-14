package com.fenyx.scene;



public class LayerManager
{
  private Layer[] l;
  
  private Layer[] l_tmp;
  
  private int size = 0;
  private final int initialSize = 10;
  
  public LayerManager() {
    this.l = new Layer[10];
    this.l_tmp = new Layer[10];
  }
  
  public LayerManager(int initial) {
    if (initial > 0) {
      this.l = new Layer[initial];
      this.l_tmp = new Layer[initial];
    } else {
      this.l = new Layer[10];
      this.l_tmp = new Layer[10];
    }
  }
  
  public void add(Layer layer) {
    if (contains(layer)) { return;
    }
    this.size += 1;
    
    if (this.size > this.l.length - 1) {
      Layer[] tmp = new Layer[this.size + 10];
      System.arraycopy(this.l, 0, tmp, 0, this.l.length);
      this.l = tmp;
    }
    
    this.l[(this.size - 1)] = layer;
  }
  
  public void put(Layer layer, int pos) {
    if (pos > this.size - 1) { add(layer);
    } else
      this.l[pos] = layer;
  }
  
  public void swap(int i, int i2) {
    Layer tmp = get(i);
    this.l[i] = this.l[i2];
    this.l[i2] = tmp;
  }
  
  public Layer get(int i) {
    if (i > this.size - 1) return null;
    return this.l[i];
  }
  
  public Layer get(String name) {
    for (Layer tmp : this.l) if (tmp.getName().equals(name))
        return tmp;
    return null;
  }
  
  public Layer[] get_all() {
    if (this.l_tmp.length != this.size) {
      this.l_tmp = new Layer[this.size];
    }
    System.arraycopy(this.l, 0, this.l_tmp, 0, this.size);
    
    return this.l_tmp;
  }
  
  public Layer get_last() {
    return get(this.size - 1);
  }
  
  public void remove(int i) {
    this.l[i] = null;
    
    Layer[] tmp = new Layer[this.l.length - 1];
    System.arraycopy(this.l, 0, tmp, 0, i);
    System.arraycopy(this.l, i + 1, tmp, i, this.size - (i + 1));
    this.l = tmp;
    
    this.size -= 1;
  }
  
  public void remove(Layer layer) {
    if (is_empty()) { return;
    }
    int i = 0;
    
    for (Layer a : this.l) {
      if (a == layer) {
        remove(i);
        return;
      }
      
      i++;
    }
  }
  
  public void clear() {
    this.l = new Layer[10];
    this.l_tmp = new Layer[10];
    this.size = 0;
  }
  
  public int size() {
    return this.size;
  }
  
  public boolean is_empty() {
    return this.size == 0;
  }
  
  public boolean contains(Layer layer) {
    for (Layer tmp : this.l) if (tmp == layer) return true;
    return false;
  }
  
  public boolean contains(String name) {
    for (Layer tmp : this.l) if (tmp.getName().equals(name)) return true;
    return false;
  }
}