package com.fenyx.geom;



public class Rect
  extends Shape
{
  public Rect()
  {
    this.points = new Point[4];
    for (int i = 0; i < 4; i++) addPoint(0.0F, 0.0F);
  }
  
  public Rect(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    
    this.points = new Point[4];
    
    addPoint(x, y);
    addPoint(x + w, y);
    addPoint(x + w, y + h);
    addPoint(x, y + h);
  }
  
  public void reset() {
    this.points[0].x = this.x;
    this.points[0].y = this.y;
    
    this.points[1].x = (this.x + this.w);
    this.points[1].y = this.y;
    
    this.points[2].x = (this.x + this.w);
    this.points[2].y = (this.y + this.h);
    
    this.points[3].x = this.x;
    this.points[3].y = (this.y + this.h);
  }
}


/* Location:              F:\Users\пользователь\Documents\NetBeansProjects\AfterEngine2\2.6\AfterEngine-core\dist\AfterEngine-core.jar!\com\afterengine\geom\Rect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */