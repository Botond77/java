/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author Botond
 */
public class Rock extends GroundElement{

    public Rock(int x, int y, int size) {super(x,y,size);}

    
      @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
        g.setColor(Color.BLACK);
        g.fillRect(x * tileSize + 2, y * tileSize + 2, tileSize - 4, tileSize - 4);
    }

}
