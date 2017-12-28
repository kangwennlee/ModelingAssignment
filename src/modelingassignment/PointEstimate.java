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
        //First scenario
        double[] averageWaitingTime = {0.02, 0, 0, 0.005, 0.0025};
        double[] averageServiceTime = {2.3125, 2.2575, 2.3, 1.9825, 2.0725};
        double[] averageInterArrivalTime = {0.9, 0.8975, 0.8975, 0.9, 0.895};
        //Second scenario
        //double[] averageWaitingTime={0.0025,0,0,0.005,0.0025};
        //double[] averageServiceTime={2.0075,2.0325,2.0575,1.775,1.7275};
        //double[] averageInterArrivalTime={0.9,0.8975,0.8975,0.9,0.895};
        //Third scenario
        //double[] averageWaitingTime={0.07,0,0.0275,0.0025,0.0025};
        //double[] averageServiceTime={2.315,2.2575,2.3,1.9825,2.0725};
        //double[] averageInterArrivalTime={0.9,0.8975,0.8975,0.9,0.895};
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
