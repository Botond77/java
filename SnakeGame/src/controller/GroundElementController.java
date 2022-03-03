/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Random;
import model.Apple;
import model.GroundElement;
import model.Rock;
import model.Snake;

/**
 *
 * @author Botond
 */
public class GroundElementController {
    
    private final  ArrayList<GroundElement> snake;
    private final ArrayList<GroundElement> rock;
    private final GroundElement apple;
    private int size ;
    
    public void setSize(int size )              {this.size=size;}
    public int size()                          {return size; }
    public ArrayList<GroundElement> getSnake() {return snake;}
    public ArrayList<GroundElement> getRock()  {return rock;}
    public GroundElement getApple()            {return apple;}
    
    public GroundElementController(){
        
        snake = new ArrayList < > ();
        rock = new ArrayList < > ();
        apple= new Apple(0,0,10);
    }
     
     public void initDefaults (int x, int y,int difficult){
       
        rock.clear();
        snake.clear();
        initRocks(0,difficult);
        snake.add(new Snake(x,y,10)); 
        createApple();     
   
    } 
     public void initRocks (int x, int difficult){
        
        int a = new Random().nextInt(size / 10 - 1);
        int b = new Random().nextInt(size / 10 - 1);
        
        for(int i=0; i< rock.size();i++){  
            if(rock.get(i).getX()==a && rock.get(i).getY()==b)
                initRocks(x+1,difficult);
        }
        
       
        rock.add(new Rock(a,b,10));
        
        if(x<difficult*3 -1)
            initRocks(x+1,difficult);
    }
     
     public Boolean IsAppleEaten(int x, int y) {
     
        return this.apple.getX() == x && this.apple.getY() == y;
    }
     
     public void incrementSnake (int x , int y){
        
        snake.add(new Snake(x,y,10));
    
    }
     
    public boolean checkCollisionWithWall(int x, int y) {
        return (x >= 0 && y >= 0) && (x < size/10 && y < size/10);
    }
     
     public boolean checkCollisionWithRock(int x, int y) {

        for (GroundElement r : rock) {
            if (r.getX() == x && r.getY() == y) {
                return true;
            }
        }
        
        return false;
    }
     
     public boolean checkCollisioneWithSelf(int x, int y) {

    if (snake.size() > 4) {
            int i = 0;
            while (i < snake.size() - 1) {
                if (snake.get(snake.size() - 1) != null) {

                    if (snake.get((int) snake.size() - 1).getX() == snake.get(i).getX()
                            && snake.get(snake.size() - 1).getY() == snake.get(i).getY()) {
                        return true;
                    }
                }
                i++;
            }
        }
        return false;
    }
      public boolean createApple(){
    
        int x = new Random().nextInt(size/20);
        int y = new Random().nextInt(size/20);
    
          for (GroundElement r : rock) {
              if (r.getX() == x && r.getY() == y) {
                  createApple();
                  return false;
              }
          }

          for (int i = 0; i < snake.size(); i++) {
              if (snake.get(i).getX() == x && snake.get(i).getY() == y) {
                  createApple();
                  return false;
              }

          }
          
          apple.setX(x);
          apple.setY(y);
          return true;
        
    }
    
}
