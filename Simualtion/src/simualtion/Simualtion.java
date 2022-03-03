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
public class Simualtion {

     public static void main(String[] args) throws FileNotFoundException
  {     
    String input ;    input="Mindeletbenmarad.txt";
    try{
     FileReader fr = new FileReader(input);
     Planet planet= new Planet(fr.getDays());
     planet.simulation(fr.getPlants());
     if(planet.getSurvivals().length()!=0){
        System.out.println("Tulelok :"+ planet.getSurvivals());
    }
    }catch(Exception e ){
        System.out.println("An unexpected error occured");
    }
  } 
  
}