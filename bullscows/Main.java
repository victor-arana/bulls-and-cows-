package bullscows;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String secret = "9305";

        Scanner scanner = new Scanner(System.in);
        String guess = scanner.next();
        
        byte[] grade = calculateGrade(guess, secret);
        System.out.println(showGrade(grade[0], grade[1], secret));

        //testSuiteGuesses();
    }

    private static void testSuiteGuesses() {
        String s = "9305"; // secret

        // Guesses for 4 bulls, 0 cows
        System.out.println("4 bulls, 0 cows:");
        testGuesses(new String[]{"9305"}, s);

        // Guesses for 0 bulls, 4 cows
        System.out.println("0 bulls, 4 cows:");
        testGuesses(new String[]{"3059", "3590", "5930", "5039"}, s);

        // Guesses for 3 bulls
        System.out.println("3 bulls:");
        testGuesses(new String[]{"9306", "9385", "9505", "1305"}, s);

        // Guesses for 2 bulls, 2 cows 
        System.out.println("2 bulls, 2 cows");
        testGuesses(new String[]{"9350", "9035", "5309", "3905"}, s);
 
        // Guesses for 0 bulls, 2 cows 
        System.out.println("0 bulls, 2 cows ");
        testGuesses(new String[]{"1293", "5012", "3512", "5129"}, s);
        
        // Guesses for 0 bulls, 0 cows 
        System.out.println("0 bulls, 0 cows ");
        testGuesses(new String[]{"1246", "7184", "4862", "2718"}, s);

        // Repetitive digits
        System.out.println("Repetitive 0 bulls, 0 cows");
        testGuesses(new String[]{"1111"}, s);

        System.out.println("Repetitive 1 bulls, 3 cows");
        testGuesses(new String[]{"9999"}, s);
        
        System.out.println("Repetitive 2 bulls, 2 cows");
        testGuesses(new String[]{"9955"}, s);
    }

    private static void testGuesses(String[] guesses, String s) {
        for (int i = 0; i < guesses.length; i++) {
            byte[] grade = calculateGrade(s,guesses[i]);
            System.out.printf("secret: %s   guess: %s   bulls: %d   cows: %d%n", s, guesses[i], grade[0], grade[1]); 
        }
    }

    /*
     * Returns a array of integers as follows:
     * grade[0]: number of bulls
     * grade[1]: number of cows
     */ 
    private static byte[] calculateGrade(String secret, String guess) {
        String s = secret;
        String g = guess; 
        byte[] grade = new byte[2];
        for(byte i = 0; i < g.length(); i++) {
            if ( s.contains(g.substring(i,i+1)) && s.charAt(i) == g.charAt(i)) {
                grade[0]++; // bulls
            } else if (s.contains(g.substring(i,i+1))){
                grade[1]++; // cows
            } 
        }
        //Remove comment for debug purposes
        //System.out.printf("secret: %s   guess: %s   bulls: %d   cows: %d%n", s, g, grade[0], grade[1]); 
        return grade;
    }

    private static String showGrade(int bulls, int cows, String secret) {
        String message = "";
        if (bulls >= 1 && cows >= 1) {
            message = "Grade: %d bull(s) and %d cow(s). The secret code is %s.%n";
            return String.format(message, bulls, cows, secret);
        } else if (bulls >= 1) {
            message = "Grade: %d bull(s). The secret code is %s.%n";
            return String.format(message, bulls, secret);
        } else if (cows >= 1) {
            message = "Grade: %d cow(s). The secret code is %s.%n";
            return String.format(message, cows, secret);
        } else {
            message = "Grade: None. The secret code is %s.%n";
            return String.format(message, secret);
        }
    }
}
