
package simualtion;
import java.util.ArrayList;


public class Planet
{ 

    private final String days;
    private String survivals="";
    protected  static int radiationGrade=0;
    
    public Planet(String days ){
        this.days=days;
   }

    public String getSurvivals() {
        return survivals;
    }
    void simulation (ArrayList <Plant> plants){
     for(int i=0 ;i < Integer.valueOf(days);i++){
        int newRadiationGrade=0;
        for(Plant a : plants){
            if(a.isAlive()){
            a.effectOfRadiations(this.radiationGrade);
            newRadiationGrade+= a.aDayPassed();
            }
            if(i==Integer.valueOf(days)-1 && a.isAlive()){
            survivals+= ""+a.name+" ";
            }
            
     }
        this.radiationGrade+=newRadiationGrade;
        System.out.println("Sugarzas merteke az " + i+ ". napon "+this.radiationGrade);
        }
     
    }
} 