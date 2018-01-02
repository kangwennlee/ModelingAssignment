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

public class Acceptance {

    static int Z0, Zcurrent, a, c, m;
    static int numOfRandomVariates, numOfRandomNumbersUsed;
    static int[] poissonVariates;
    static double lambda;

    public static void main(String[] args) {
        //initialize(z0,a,c,m,numberOfRandomNumber,lamda)
        initialize(5, 137, 1, 256, 400, 1);
//        for (int i=0; i<poissonVariates.length;i++){ //generate number of variates that follows Poisson distribution
//            poissonVariates[i] = generateRandomVariates();
//        }
//        System.out.print("Generate random variates: ");
//        printArray(poissonVariates);
//        System.out.println("Total random numbers used: " + numOfRandomNumbersUsed);
        validateLCG();
        double[] randomNumber = generateRandomNumbersLCGDouble();
        RandomnessTest.chiSquareTest(randomNumber);
        RandomnessTest.autoCorrelationTest(randomNumber);
        //printArray(randomNumber);
    }

    public static void initialize(int z0, int a1, int c1, int m1, int numOfRandomVariates1, double lamda) {
        Z0 = z0;
        Zcurrent = Z0;
        a = a1;
        c = c1;
        m = m1;
        numOfRandomVariates = numOfRandomVariates1;
        numOfRandomNumbersUsed = 0;
        poissonVariates = new int[numOfRandomVariates];
        lambda = lamda;
    }

    public static LinkedList<Integer> generateTime() {
        LinkedList<Integer> q = new LinkedList<>();
        validateLCG();
        for (int i = 0; i < numOfRandomVariates; i++) { //generate number of variates that follows Poisson distribution
            q.add(generateRandomVariates());
        }
        double[] randNumber = generateRandomNumbersLCGDouble();
        RandomnessTest.chiSquareTest(randNumber);
        RandomnessTest.autoCorrelationTest(randNumber);
        return q;
    }

    public static LinkedList<Double> generateRandomNumber() {
        LinkedList<Double> q = new LinkedList<>();
        validateLCG();
        double[] randNumber = generateRandomNumbersLCGDouble();
        for (int i = 0; i < numOfRandomVariates; i++) { //generate number of variates that follows Poisson distribution
            q.add(randNumber[i]);
        }
        RandomnessTest.chiSquareTest(randNumber);
        RandomnessTest.autoCorrelationTest(randNumber);
        return q;
    }

    public static int generateRandomVariates() {
        //Step 1: set n = 0; P = 1;
        int n = 0;
        double P = 1;

        while (true) {
            //Step 2: get next random numbers from LCG, P = P*R
            numOfRandomNumbersUsed++;
            double rand = (double) Zcurrent / m;
            Zcurrent = generateNextRandomNumbersLCG();
            P = P * rand;

            //Step 3:
            if (P < Math.exp(-lambda)) {
                return n;
            } else {
                n++;
            }
        }

    }

    public static int generateNextRandomNumbersLCG() {
        return (a * Zcurrent + c) % m; //update new Z 
    }

    public static double[] generateRandomNumbersLCGDouble() {
        int[] randomNumbersInteger = new int[numOfRandomVariates];
        double[] randomNumbers = new double[numOfRandomVariates];
        randomNumbersInteger[0] = Z0;
        for (int i = 1; i < randomNumbersInteger.length; i++) {
            randomNumbersInteger[i] = ((a * randomNumbersInteger[i - 1] + c) % m);
        }
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = ((double) randomNumbersInteger[i]) / (double) m;
        }
        //printArray(randomNumbers);
        return randomNumbers;
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return (n * factorial(n - 1));
        }
    }

    public static void printArray(int[] numbers) {
        System.out.print("[");
        for (int i = 0; i < (numbers.length - 1); i++) {
            System.out.print(numbers[i] + ",");
        }
        System.out.print(numbers[numbers.length - 1] + "]\n");
    }

    public static void printArray(double[] numbers) {
        System.out.print("[");
        for (int i = 0; i < (numbers.length - 1); i++) {
            System.out.print(format(numbers[i]) + ",");
        }
        System.out.print(format(numbers[numbers.length - 1]) + "]\n");
    }

    public static String format(double val) {
        return String.format("%1$.2f", val);
    }

    public static void validateLCG() {
        int primeNumber = primeNumber();

        if (GCD(m, c) == 1 && m % 4 == 0 && (a - 1) % 4 == 0 && (a - 1) % primeNumber == 0) {
            System.out.println("full period!");
        } else {
            System.out.println("not full period!");
        }
    }

    public static int GCD(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }
        return GCD(num2, num1 % num2);
    }

    public static int primeNumber() {
        int i = 0;
        int num = 0;
        int y = 0;
        int primeNumbers[] = new int[150];

        for (i = 1; i <= 100; i++) {
            int counter = 0;
            for (num = i; num >= 1; num--) {
                if (i % num == 0) {
                    counter = counter + 1;
                }
            }
            if (counter == 2) {
                //Appended the Prime number to the String
                primeNumbers[y] = i;
                y++;
            }
        }

        for (int z = 0; z < primeNumbers.length; z++) {
            if (m % primeNumbers[z] == 0) {
                return primeNumbers[z];
            } else {
                return 0;
            }
        }
        return 0;
    }
}
