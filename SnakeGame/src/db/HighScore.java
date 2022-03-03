/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Botond
 */
import java.util.Objects;
import controller.GameID;

public class HighScore {
    public final String name;
    public final int level;
    public final int steps;
    
    public HighScore(GameID gameID, int steps){
        this.name = gameID.name;
        this.level = gameID.difficult;
        this.steps = steps;
    }
    
    public HighScore(String difficulty, int level, int steps){
        this.name = difficulty;
        this.level = level;
        this.steps = steps;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + this.level;
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
        final HighScore other = (HighScore) obj;
        if (this.level != other.level) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }   

    @Override
    public String toString() {
        return name + "-" + level + ": " + steps;
    }
    
    
}

