/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.Database;
import db.HighScore;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import view.Screen;

/**
 *
 * @author Botond
 */
public class Game extends KeyAdapter implements ActionListener {
    
   
    private final GroundElementController gController;
    private final Screen screen;
    private final Direction direction;
    private final Database database;
    private final Timer timer;
    private final int tileSize=500 ;
    private int sizeOfSnake=3;
    
    private boolean running=false;
    private float speed;
    private int difficult;
    private int x , y ;
    private int delay ;
    private GameID gameId;
    
    private boolean isBetterHighScore = false;
    
    
    public GroundElementController getGroundController ()                {return this.gController;}
    public Screen getScreen()                                            {return screen;}
    public int getDelay()                                                {return delay;}
    public int getTileSize()                                             {return tileSize; }
    public Direction getDirection()                                      {return direction;}
    public boolean isRunning()                                           {return  running;}
    public ArrayList<HighScore> getHighScores()                          {return database.getHighScores();}
    
    public Game(){
        
        gController = new GroundElementController();
        direction= new Direction(0,0);
        screen= new Screen(this);
        timer = new Timer(delay,this);
        database = new Database();
        
    }
    public void loadLevel(GameID gameId){
        
        this.gameId = gameId;
        delay = 100 - 3 * (int) gameId.difficult;
        difficult = gameId.difficult;
        direction.setDefaultDirection();
        sizeOfSnake = 3;
        x = tileSize / 20;
        y = tileSize / 20;
        running = true;

        gController.setSize(tileSize);
        gController.initDefaults(x, y, difficult);
        

        isBetterHighScore = false;
        screen.refresh();
        screen.repaint();
        
        timer.setDelay(delay);
        timer.start();
    }
   
     public void timerStop()                     
     {
         running=false;
     }
     
    public void move(){
     
       if (gController.IsAppleEaten(x, y)) {
           if(!gController.createApple())
               gController.createApple();
            
            sizeOfSnake++;
            delay -=1;
            timer.setDelay(delay);
            timer.stop();
            timer.start();
        }  
        x += direction.getX();
        y += direction.getY();
       gController.incrementSnake(x, y);
            
        if(gController.checkCollisionWithRock(x, y) || gController.checkCollisioneWithSelf(x, y) || !gController.checkCollisionWithWall(x, y)){
             
            String name = null;
            if (name == null) {
                name = screen.getPlayerName();
                if (name !=null &&!name.equals("")  ) {

                    gameId.name = name;
                }

                int steps = sizeOfSnake - 3;
                isBetterHighScore = database.storeHighScore(gameId, steps);
                timer.stop();

            } else {
                running = false;
               
            }
        }   
         
        if (gController.getSnake().size() > sizeOfSnake) 
            gController.getSnake().remove(0);
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
                if (!isRunning()) return;
                int kk = e.getKeyCode();
                    direction.setDirection(kk);                  
   }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(running){
           move();   
           screen.repaint();
       }
    }

}
