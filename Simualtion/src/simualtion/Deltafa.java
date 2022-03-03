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
class Deltafa extends Plant{
   
    public Deltafa(String name, int health) {
        super(name, health);
    }    

    @Override
    boolean isAlive(){
        System.out.println("eletereje a " +this.name+" -nak: "+this.health);
    return this.health > 0;
    }
    
    @Override
    void effectOfRadiations(int radiationGrade) {
        if(radiationGrade<0){
            this.health+=4;
        } else if(radiationGrade >0){
            this.health-=3;
        }else {
            this.health-=1;
        }
    }

    @Override
    int aDayPassed() {
        if(this.health>1 && this.health <5){
            return -4;
        }else if(this.health<10 && this.health >5){
            return -1;
        }else return 0;
    }
}