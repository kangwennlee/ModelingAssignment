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
public class RandomnessTest {
    
    public static void autoCorrelationTest(double[] randNumber){
        
    }

    //public static double[] randNumber = {0.34,0.90,0.25,0.89,0.87,0.44,0.12,0.21,0.46,0.67,0.83,0.76,0.79,0.64,0.70,0.81,0.94,0.74,0.22,0.74,0.96,0.99,0.77,0.67,0.56,0.41,0.52,0.73,0.99,0.02,0.47,0.30,0.17,0.82,0.56,0.05,0.45,0.31,0.78,0.05,0.79,0.71,0.23,0.19,0.82,0.93,0.65,0.37,0.39,0.42};
    public static void chiSquareTest(double[] randNumber) {
        int[] intervalCount = countOccurence(randNumber);
        double expectedOccurence = (double) randNumber.length / (double) intervalCount.length;
        double[] result = new double[intervalCount.length];
        double totalResult = 0.0;
        for (int i = 0; i < intervalCount.length; i++) {
            result[i] = Math.pow((intervalCount[i] - expectedOccurence), 2) / expectedOccurence;
            totalResult = totalResult + result[i];
        }
        double critical = getCriticalValue(intervalCount, 0.05);
        chi_square_test(randNumber, intervalCount.length, critical);
        if (totalResult < critical) {
            System.out.println("Accepted! Calculated value: " + totalResult + " Estimate value: " + critical);
        } else {
            System.out.println("Rejected! Calculated value: " + totalResult + " Estimate value: " + critical);
        }
    }

    public static int[] countOccurence(double[] randNumber) {
        int[] intervalCount = new int[10];
        for (int i = 0; i < intervalCount.length; i++) {
            intervalCount[i] = 0;
        }
        for (int i = 0; i < randNumber.length; i++) {
            if (randNumber[i] >= 0 && randNumber[i] < 0.1) {
                intervalCount[0]++;
            }
            if (randNumber[i] >= 0.1 && randNumber[i] < 0.2) {
                intervalCount[1]++;
            }
            if (randNumber[i] >= 0.2 && randNumber[i] < 0.3) {
                intervalCount[2]++;
            }
            if (randNumber[i] >= 0.3 && randNumber[i] < 0.4) {
                intervalCount[3]++;
            }
            if (randNumber[i] >= 0.4 && randNumber[i] < 0.5) {
                intervalCount[4]++;
            }
            if (randNumber[i] >= 0.5 && randNumber[i] < 0.6) {
                intervalCount[5]++;
            }
            if (randNumber[i] >= 0.6 && randNumber[i] < 0.7) {
                intervalCount[6]++;
            }
            if (randNumber[i] >= 0.7 && randNumber[i] < 0.8) {
                intervalCount[7]++;
            }
            if (randNumber[i] >= 0.8 && randNumber[i] < 0.9) {
                intervalCount[8]++;
            }
            if (randNumber[i] >= 0.9 && randNumber[i] <= 1.0) {
                intervalCount[9]++;
            }
        }
        return intervalCount;
    }

    public static double getCriticalValue(int[] intervalCount, double significanceLevel) {
        int numOfInterval = intervalCount.length - 1;
        if (numOfInterval == 1) {
            if (significanceLevel == 0.1) {
                return 2.71;
            } else if (significanceLevel == 0.05) {
                return 3.84;
            } else if (significanceLevel == 0.01) {
                return 6.63;
            }
        } else if (numOfInterval == 2) {
            if (significanceLevel == 0.1) {
                return 4.61;
            } else if (significanceLevel == 0.05) {
                return 5.99;
            } else if (significanceLevel == 0.01) {
                return 9.21;
            }
        } else if (numOfInterval == 3) {
            if (significanceLevel == 0.1) {
                return 6.25;
            } else if (significanceLevel == 0.05) {
                return 7.81;
            } else if (significanceLevel == 0.01) {
                return 11.34;
            }
        } else if (numOfInterval == 4) {
            if (significanceLevel == 0.1) {
                return 7.78;
            } else if (significanceLevel == 0.05) {
                return 9.49;
            } else if (significanceLevel == 0.01) {
                return 13.28;
            }
        } else if (numOfInterval == 5) {
            if (significanceLevel == 0.1) {
                return 9.2;
            } else if (significanceLevel == 0.05) {
                return 11.1;
            } else if (significanceLevel == 0.01) {
                return 15.1;
            }
        } else if (numOfInterval == 6) {
            if (significanceLevel == 0.1) {
                return 10.6;
            } else if (significanceLevel == 0.05) {
                return 12.6;
            } else if (significanceLevel == 0.01) {
                return 16.8;
            }
        } else if (numOfInterval == 7) {
            if (significanceLevel == 0.1) {
                return 12.0;
            } else if (significanceLevel == 0.05) {
                return 14.1;
            } else if (significanceLevel == 0.01) {
                return 18.5;
            }
        } else if (numOfInterval == 8) {
            if (significanceLevel == 0.1) {
                return 13.4;
            } else if (significanceLevel == 0.05) {
                return 15.5;
            } else if (significanceLevel == 0.01) {
                return 20.1;
            }
        } else if (numOfInterval == 9) {
            if (significanceLevel == 0.1) {
                return 14.7;
            } else if (significanceLevel == 0.05) {
                return 16.9;
            } else if (significanceLevel == 0.01) {
                return 21.7;
            }
        } else if (numOfInterval == 10) {
            if (significanceLevel == 0.1) {
                return 16.0;
            } else if (significanceLevel == 0.05) {
                return 18.3;
            } else if (significanceLevel == 0.01) {
                return 23.2;
            }
        } else if (numOfInterval == 20) {
            if (significanceLevel == 0.1) {
                return 28.4;
            } else if (significanceLevel == 0.05) {
                return 31.4;
            } else if (significanceLevel == 0.01) {
                return 37.6;
            }
        } else if (numOfInterval == 30) {
            if (significanceLevel == 0.1) {
                return 40.3;
            } else if (significanceLevel == 0.05) {
                return 43.8;
            } else if (significanceLevel == 0.01) {
                return 50.9;
            }
        } else if (numOfInterval == 40) {
            if (significanceLevel == 0.1) {
                return 51.8;
            } else if (significanceLevel == 0.05) {
                return 55.8;
            } else if (significanceLevel == 0.01) {
                return 63.7;
            }
        } else if (numOfInterval == 50) {
            if (significanceLevel == 0.1) {
                return 63.2;
            } else if (significanceLevel == 0.05) {
                return 67.5;
            } else if (significanceLevel == 0.01) {
                return 76.2;
            }
        }

        return 0; // to make sure no compilation error. will cause logic error if significance level is not 0.01, 0.05 or 0.10
    }

    /*
	 * Implements the chi square goodness of fit chiSquareTest for uniform distribution
	 * see e.g. Statistik II chapter 5.
	 * r: number of classes
	 * The chiSquareTest statistic is compared to "quantile".
	 * The user needs to enter the 1-gamma quantile of the chi-squared distribution
	 * with r-1 degrees of freedom in order to obtain the chiSquareTest level gamma.
     */
    public static void chi_square_test(double[] random_numbers, double r, double quantile) {
        double T = 0;
        double n = (double) random_numbers.length;

        for (int j = 1; j <= r; j++) {
            T = T + Math.pow(count_rnds_in_interval(random_numbers, ((double) (j - 1)) / r, ((double) j) / r) - n / r, 2) / (n / r);
        }
        System.out.println("T=" + T);
        if (T > quantile) {
            System.out.println("reject H0: equal class probabilities " + quantile);
        } else {
            System.out.println("do not reject H0: equal class probabilities " + quantile);
        }
    }
    //returns the number of pseudo-random numbers in  the interval (lower_bound,upper_bound]

    public static int count_rnds_in_interval(double[] random_numbers, double lower_bound, double upper_bound) {
        int count = 0;
        for (int i = 0; i < random_numbers.length; i++) {
            if (lower_bound < random_numbers[i] && random_numbers[i] <= upper_bound) {
                count++;
            }
        }
        return count;
    }
}
