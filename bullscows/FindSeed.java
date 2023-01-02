package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class FindSeed {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int seedA = scanner.nextInt();
        int seedB = scanner.nextInt();

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        // This matrix stores a seed and its maximum
        int[][] seedMax = new int[seedB - seedA + 1][2];
        for(int i = 0; i < seedMax.length; i++) {
            seedMax[i][0] = seedA;
            seedMax[i][1] = getMax(generateRandomSequence(seedA++, N, K));
        }

        //System.out.println(Arrays.deepToString((seedMax)));

        int seedMin = seedMax[0][1];
        int seed = seedMax[0][0];
        for (int i = 0; i < seedMax.length; i++) {
            if (seedMin > seedMax[i][1]) {
                seedMin = seedMax[i][1];
                seed = seedMax[i][0];
            }
        }

        System.out.printf("Seed: %d,   Max: %d%n", seed, seedMin);

    }

    private static int getMax(int[] array) {
        // Asume the first element is the maximum
        int max = array[0];
        for (int i =1 ; i < array.length; i++ ) {
           if (array[i] > max) {
               max = array[i];
           }
        }
        return max;
    }

    private static int[] generateRandomSequence(int seed, int n, int k) {
        int[] randomSequence = new int[n];
        Random random = new Random(seed);
        for (int i = 0; i < n; i++){
            randomSequence[i] = random.nextInt(k);
        }
        return randomSequence;
    }

}