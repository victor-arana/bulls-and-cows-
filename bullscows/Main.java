package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        String s = "9305"; // secret
        String[] guesses = {"9305", "3059", "3590", "5930", "5039"};

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

    private static String showGrade(int bulls, int cows) {
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
