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
class Puffancs extends Plant{
    public Puffancs(String name, int health) {
        super(name, health);
    }
    @Override
    boolean isAlive(){
         System.out.println("eletereje a " +this.name+" -nak :"+this.health);
        return this.health > 0 && this.health<10;
    }

    @Override
    void effectOfRadiations(int radiationGrade) {
        if(radiationGrade<0){
        //delta sugarzas
            this.health-=2;
        } else if(radiationGrade >0){
           //Alfa sugarzas
            this.health+=2;
        }else {
            //sugarzasmentes nap
            this.health-=1;
        }
    }
    
    @Override
    int aDayPassed() {
        return 10;
    }
    
}
