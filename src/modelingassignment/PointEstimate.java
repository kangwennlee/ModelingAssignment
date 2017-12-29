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
public class PointEstimate {

    public static void main(String[] args) {
        firstScenario();
        secondScenario();
        thirdScenario();
    }
    
    public static void firstScenario(){
        //First scenario
        double[] averageWaitingTime = {0.02046035806, 0, 0, 0.005540166205,0.002577319588};
        double[] averageServiceTime = {2.3657289,2.274559194,2.371134021,2.1966759,2.136597938};
        double[] averageInterArrivalTime = {0.9207161125,0.9042821159,0.925257732,0.9972299169,0.9226804124};
        calculatePointEstimate(averageWaitingTime);
        calculatePointEstimate(averageServiceTime);
        calculatePointEstimate(averageInterArrivalTime);
    }
    
    public static void secondScenario(){
        //Second scenario
        double[] averageWaitingTime={0.002557544757,0,0,0.005540166205,0.002577319588};
        double[] averageServiceTime={2.05370844,2.047858942,2.121134021,1.969529086,1.780927835};
        double[] averageInterArrivalTime={0.9207161125,0.9042821159,0.925257732,0.9972299169,0.9226804124};
        calculatePointEstimate(averageWaitingTime);
        calculatePointEstimate(averageServiceTime);
        calculatePointEstimate(averageInterArrivalTime);
    }
    
    public static void thirdScenario(){
        //Third scenario
        double[] averageWaitingTime={0.0716112532,0,0.01288659794,0.002770083102,0.002577319588};
        double[] averageServiceTime={2.3657289,2.274559194,2.371134021,2.1966759,2.136597938};
        double[] averageInterArrivalTime={0.9207161125,0.9042821159,0.925257732,0.9972299169,0.9226804124};
        calculatePointEstimate(averageWaitingTime);
        calculatePointEstimate(averageServiceTime);
        calculatePointEstimate(averageInterArrivalTime);
    }

    public static void calculatePointEstimate(double[] input) {
        double t = 2.132;
        double sampleMean = 0;
        double total_variance = 0;
        double pos_Answer = 0;
        double neg_Answer = 0;
        double total_input = 0;

        for (int i = 0; i < input.length; i++) {
            total_input = total_input + input[i];
        }
        sampleMean = total_input / input.length;
        for (int i = 0; i < input.length; i++) {
            total_variance = total_variance + Math.pow((input[i] - sampleMean), 2);
        }
        total_variance = total_variance / (input.length - 1);
        pos_Answer = sampleMean + t * Math.sqrt(total_variance / input.length);
        neg_Answer = sampleMean - t * Math.sqrt(total_variance / input.length);
        System.out.println("( " + pos_Answer + ", " + neg_Answer + " )");
    }
}
