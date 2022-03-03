/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Objects;

/**
 *
 * @author Botond
 */
public class GameID {
    public final int difficult; 
    public  String name;
  
    
    public GameID(String name, int difficult) {
        this.difficult = difficult;
        this.name=name;
    }

     @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + this.difficult;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameID other = (GameID) obj;
       
        if (this.difficult!= other.difficult) {
            return false;
        }
        if (!Objects.equals(this.difficult, other.difficult)) {
            return false;
        }
          return true;
    }
   
}
