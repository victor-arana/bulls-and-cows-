package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
//        Scanner scanner = new Scanner(System.in);
        String s = "9305"; // secret
//      String g = scanner.next(); // guess
        String g = "1234";
        
        int bulls = 0;
        int cows = 0; 
        for(int i = 0; i < g.length(); i++) {
            if ( s.contains(g.substring(i,i+1)) && s.charAt(i) == g.charAt(i)) {
                bulls++; 
            } else if (s.contains(g.substring(i,i+1))){
                cows++;
            } 
            System.out.printf("secret: %s   guess: %s   bulls: %d   cows: %d%n", s, g, bulls, cows); 
        }

        



/* 
        System.out.println("The secret code is prepared: ****.");
        int cows = 0;
        int bulls = 0;
        for (int i = 1; i <= 7; i++) {
            System.out.printf("%nTurn %d. Answer:%n", i);
            // The nextInt(9000) method generates a random integer between 0
            // and 8999, and then adding 1000 to it gives us a random integer
            // between 1000 and 9999.
            Random random = new Random();
            int randomInt = random.nextInt(9000) + 1000;
            boolean isBull = random.nextBoolean();
            boolean isCow = random.nextBoolean();
            if (isBull) {
                bulls++;
            }
            if (isCow) {
                cows++;
            }
            System.out.println(randomInt);
            System.out.println("Grade: " + getGrade(bulls, cows));
        }
        System.out.println("Congrats! The secret code is 9305");
*/
    }

    private static String getGrade(int bulls, int cows) {
        if (bulls > 1 && cows > 1) {
            return String.format("%d bull and %d cow.%n",bulls, cows);
        } else if (bulls > 1) {
            return String.format("%d bull.%n", bulls);
        } else if (cows > 1) {
            return String.format("%d cow.%n", cows);
        } else {
            return "";
        }
    }
}
