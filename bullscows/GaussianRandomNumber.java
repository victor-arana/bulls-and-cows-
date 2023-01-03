package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GaussianRandomNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int K = scanner.nextInt();
        int N = scanner.nextInt();
        double M = scanner.nextDouble();

        System.out.printf("K: %d, N: %d, M: %f%n", K, N, M);

        boolean lessOrEqual = false;
        do {
            double[] gaussianRandomNumbers = generateGaussianRandomNumbers(K++, N);
            System.out.println(Arrays.toString(gaussianRandomNumbers));
            lessOrEqual = setIsLessOrEqual(gaussianRandomNumbers, M);
        } while(!lessOrEqual);

        System.out.printf("K: %d, N: %d, M: %f%n", K-1, N, M);

    }

    private static double[] generateGaussianRandomNumbers(int K, int N) {
        double[] gaussianRandomNumbers = new double[N];
        Random random = new Random(K);
        for (int i = 0; i < gaussianRandomNumbers.length; i++) {
            gaussianRandomNumbers[i] = random.nextGaussian();
        }
        return gaussianRandomNumbers;
    }

    private static boolean setIsLessOrEqual(double[] gaussianRandomNumbers, double M) {
        boolean setIsLessOrEqual = true;
        for (int i = 0; i < gaussianRandomNumbers.length; i++) {
            if (gaussianRandomNumbers[i] > M) {
                setIsLessOrEqual = false;
                break;
            }
        }
        return setIsLessOrEqual;
    }
}
