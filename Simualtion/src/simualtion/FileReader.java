/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simualtion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Botond
 */
public class FileReader {
    
    private String days;
    private ArrayList <Plant> plants = new ArrayList<>();

    public String getDays() {
        return days;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }
    
    public FileReader(String input){
       try {
          Scanner sc = new Scanner(new File(input));
          while(sc.hasNextLine()){
            String temp2 =sc.nextLine();
            String s[]=temp2.split(" ",0);
        
            String name ="";
            int health;
                for (String a : s){
          
                    if(s.length>=3){name =s[1];
                    health=Integer.valueOf(s[2]);
                    }
                        
                        }
                        switch(name){
                        case  "d" :
                        plants.add(new Deltafa(s[0],Integer.valueOf(s[2])));
                        break;
                        case  "a" :
                        plants.add(new Puffancs(s[0],Integer.valueOf(s[2])));
                        break;
                        case  "p" :
                        plants.add(new Parabokor(s[0],Integer.valueOf(s[2])));
                        break;
                 
                        }
                        if(s.length==1)
                        days=s[0];
        
            }
      
       } catch (FileNotFoundException ex) {
           System.out.println("File not found exception");
       }
   }
   
}
