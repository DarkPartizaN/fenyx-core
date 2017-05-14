package com.fenyx.geom;

import com.fenyx.utils.MathUtils;



public class Circle
  extends Shape
{
  private final int radius;
  private final float delta;
  
  public Circle(float centerx, float centery, int radius, int precize)
  {
    this.x = centerx;
    this.y = centery;
    this.radius = radius;
    this.delta = (360.0F / precize);
    
    this.points = new Point[precize];
    
    double angle = 0.0D;
    for (int i = 0; i < precize; i++) {
      this.points[i] = new Point(this.x + radius * MathUtils.cos(angle), this.y - radius * MathUtils.sin(angle));
      
      angle += this.delta;
    }
  }
  
  public void reset() {
    double angle = 0.0D;
    for (Point p : this.points) {
      p.x = (this.x + this.radius * MathUtils.cos(angle));
      p.y = (this.y - this.radius * MathUtils.sin(angle));
      
      angle += this.delta;
    }
  }
}


/* Location:              F:\Users\пользователь\Documents\NetBeansProjects\AfterEngine2\2.6\AfterEngine-core\dist\AfterEngine-core.jar!\com\afterengine\geom\Circle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */