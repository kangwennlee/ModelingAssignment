/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelingassignment;

/**
 *
 * @author Kangwenn
 */

import java.util.LinkedList;
import java.util.Queue;

public class Acceptance {

    static int Z0, Zcurrent, a, c, m;
    static int numOfRandomVariates, numOfRandomNumbersUsed;
    static int[] poissonVariates; 
    static double lambda;
    
    public static void main(String[] args) {
        initialize();
        for (int i=0; i<poissonVariates.length;i++){ //generate number of variates that follows Poisson distribution
            poissonVariates[i] = generateRandomVariates();
        }
        System.out.print("Generate random variates: ");
        printArray(poissonVariates);
        System.out.println("Total random numbers used: " + numOfRandomNumbersUsed);
    }
    
    public static void initialize(){
        Z0 = 7;
        Zcurrent = Z0;
        a = 5;
        c = 3;
        m = 16; 
        numOfRandomVariates = 20;
        numOfRandomNumbersUsed = 0;
        poissonVariates = new int[numOfRandomVariates];
        lambda = 1;
    }
    
    public static Queue<Integer> generateInterarrivalTime(int numOfRandomVariates){
        Queue<Integer> q = new LinkedList<>();
        Z0 = 7;
        Zcurrent = Z0;
        a = 20;
        c = 3;
        m = 19;
        numOfRandomNumbersUsed = 0;
        lambda = 0.91;
        for (int i=0; i<numOfRandomVariates;i++){ //generate number of variates that follows Poisson distribution
            q.add(generateRandomVariates());
        }
        return q;
    }
    
    public static Queue<Integer> generateWaitingTime(int numOfRandomVariates){
        Queue<Integer> q = new LinkedList<>();
        Z0 = 7;
        Zcurrent = Z0;
        a = 5;
        c = 3;
        m = 16;
        numOfRandomNumbersUsed = 0;
        lambda = 1.12;
        for (int i=0; i<numOfRandomVariates;i++){ //generate number of variates that follows Poisson distribution
            q.add(generateRandomVariates());
        }
        return q;
    }
    
    public static Queue<Integer> generateServiceTime(int numOfRandomVariates){
        Queue<Integer> q = new LinkedList<>();
        Z0 = 7;
        Zcurrent = Z0;
        a = 5;
        c = 3;
        m = 16;
        numOfRandomNumbersUsed = 0;
        lambda = 2.23;
        for (int i=0; i<numOfRandomVariates;i++){ //generate number of variates that follows Poisson distribution
            q.add(generateRandomVariates());
        }
        return q;
    }
    
    public static int generateRandomVariates(){
        //Step 1: set n = 0; P = 1;
        int n = 0;
        double P = 1;
        
        while(true){
            //Step 2: get next random numbers from LCG, P = P*R
            numOfRandomNumbersUsed++;
            double rand = (double)Zcurrent/m;
            Zcurrent = generateNextRandomNumbersLCG();
            P = P * rand;

            //Step 3:
            if(P < Math.exp(-lambda)){
                return n;
            }   
            else n++;
        }
        
    }
    
    public static int generateNextRandomNumbersLCG(){
        return (a * Zcurrent + c) % m; //update new Z 
    }
 
    public static int factorial(int n){    
        if (n == 0) return 1;    
        else return(n * factorial(n-1));    
    }
    
    public static void printArray(int[] numbers){
        System.out.print("[");
        for (int i=0;i<(numbers.length-1);i++)
            System.out.print( numbers[i] + ",");
        System.out.print(numbers[numbers.length-1] + "]\n");
    }
    
    public static String format(double val){
        return String.format("%1$.2f",val);
    }
    
}