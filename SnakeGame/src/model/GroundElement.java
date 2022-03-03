/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics;

/**
 *
 * @author Botond
 */
public abstract class GroundElement {
    protected int x, y, tileSize;
    
      public GroundElement(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.tileSize= size;
    }
      
    public final void setX(int x)     {this.x=x;}
    public final void setY(int y)     {this.y=y;}
    public final int getX()           {return this.x;}   
    public final int getY()           { return this.y;}
    
    public abstract  void draw(Graphics g);
}
