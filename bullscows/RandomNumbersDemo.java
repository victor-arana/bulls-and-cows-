package bullscows;

import java.util.Scanner;
import java.util.Random;

public class RandomNumbersDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int lower = scanner.nextInt();
        int upper = scanner.nextInt();
        Random random = new Random();

        int intervalLength = upper - lower + 1;

        System.out.println(random.nextInt(intervalLength) + lower);
        System.out.println(random.nextInt(intervalLength) + lower);
        System.out.println(random.nextInt(intervalLength) + lower);
        System.out.println(random.nextInt(intervalLength) + lower);
    }
}
