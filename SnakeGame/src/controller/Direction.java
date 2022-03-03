/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.KeyEvent;

/**
 *
 * @author Botond
 */
public class Direction {

    private int x, y;
    private boolean left = false, right = false, up = false, down = false;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(int kk) {

        if (kk == KeyEvent.VK_D && !left) {
            up = false;
            down = false;
            right = true;
            this.x = 1;
            this.y = 0;
        }
        if (kk == KeyEvent.VK_A && !right) {
            up = false;
            down = false;
            left = true;
            this.x = -1;
            this.y = 0;
        }
        if (kk == KeyEvent.VK_W && !down) {
            left = false;
            right = false;
            up = true;
            this.x = 0;
            this.y = -1;
        }
        if (kk == KeyEvent.VK_S && !up) {
            left = false;
            right = false;
            down = true;
            this.x = 0;
            this.y = 1;
        }
    }

    public void setDefaultDirection() {
        this.down = false;
        this.up = false;
        this.right = false;
        this.left = false;
        x=0;y=0;
    }

}
