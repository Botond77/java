/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simualtion;

/**
 *
 * @author Botond
 */
public abstract class Plant{

   
   protected  int health;
   protected  String name;
   
    public int getHealth() {
        return health;
    }

    public Plant( String name , int health){
    this.name=name;
    this.health=health;
    }
   abstract void effectOfRadiations(int radiationGrade);
   abstract int aDayPassed();
   abstract boolean isAlive();
   
}
