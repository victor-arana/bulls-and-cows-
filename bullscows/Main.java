package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame(){
        Scanner scanner = new Scanner(System.in);

        // Read secret code length
        System.out.println("Input the length of the secret code:");
        byte secretCodeLength = 0;
        String readSecretCode = scanner.next();
        try {
            secretCodeLength = Byte.parseByte(readSecretCode);
        } catch (NumberFormatException e){
            System.out.printf("Error: %s isn't a valid number.", readSecretCode);
            return;
        }

        // Read possible symbols
        System.out.println("Input the number of possible symbols in the code:");
        byte possibleSymbols = 0;
        String readPossibleSymbols = scanner.next();
        try {
             possibleSymbols = Byte.parseByte(readPossibleSymbols);
        } catch (NumberFormatException e) {
            System.out.printf("Error: %s isn't a valid number.", readPossibleSymbols);
            return;
        }

        String secret = generatePseudoRandomNumber(secretCodeLength, possibleSymbols);
        if(secret.equals("-1")) return;

        String maskedSecret = maskSecret(secret);
        System.out.printf("The secret is prepared: %s %s.%n", maskedSecret, formatRange(possibleSymbols));

        System.out.println("Okay, let's start a game!");
        String guess = "";
        byte turnCount = 0;
        do{
            System.out.printf("Turn %d:%n", ++turnCount);
            guess = scanner.next();
            byte[] grade = calculateGrade(guess, secret);
            System.out.println(buildGradeResponse(grade[0], grade[1], secret));
        } while (!guess.equals(secret));
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static String formatRange(byte symbols) {
        if (symbols == 0) {
            return "";
        }

        String formattedRange = "";

        int digits = symbols <= 10 ? symbols : 10; // from 0 to 9
        int letters = symbols - digits;
        if (letters > 0) {
            char finalLetter = (char) ('a' - 1 + letters);
            formattedRange = String.format("(0-9), a-%s", finalLetter);
        } else if (letters == 0){
            formattedRange = String.format("(0-9)");
        } else {
            formattedRange = String.format("(0-%d)", digits + letters);
        }

        return formattedRange;
    }

    private static String maskSecret(String secret) {
        String maskedSecret = "";
        for(int i = 0; i < secret.length(); i++){
            maskedSecret += "*";
        }
        return maskedSecret;
    }

    /**
     * Generates a pseudo-random number of a given a secret code length
     * and a number of possible symbols. The secret code has the following
     * characteristics:
     *
     * - Can contain unique digits from 0 to 9.
     * - Can contain lowercase latin characters from a-z
     *
     * @param len integer number between 0 and 9. If
     *            numberLength > 36 it returns -1.
     * @param symbols number of possible characters 
     * @return secretCode
     */
    private static String generatePseudoRandomNumber(byte len, byte symbols) {

        byte characters = (byte) (symbols > 10 ? symbols - 10 : 0);  ;
        // Validate length is in the range [0,36]
        if (!(symbols >= 0 && symbols <= 36)) {
             System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
             return "-1";
        } else if(len > symbols || len <= 0){
             System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", len, symbols);
             return "-1";
        } else {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < len) {
                boolean addDigit =  true;
                if(characters > 0){
                    addDigit = isNextCharDigit();
                }

                if (addDigit) {
                    sb.append(nextIntWithout(0,9,sb.toString()));
                } else { // nextChar is Letter
                    char initialChar = 'a';
                    char finalChar = (char) (initialChar + characters - 1);
                    char c = nextLetterInWithout(initialChar, finalChar, sb.toString());
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }

    private static int nextIntWithout(int a, int b, String exclusions) {
        int candidate = nextInt(a, b);
        while (exclusions.contains(String.valueOf(candidate))){
            candidate = nextInt(a, b);
        }
        return candidate;
    }

    private static char nextLetterInWithout(char initialChar, char finalChar, String exclusions) {
        char candidate = nextLetterIn(initialChar, finalChar);
        while(exclusions.contains(String.valueOf(candidate))) {
            candidate = nextLetterIn(initialChar, finalChar);
        }
        return candidate;
    }

    private static char nextLetterIn(char initialChar, char finalChar) {
        char c = (char) nextInt(initialChar, finalChar);
        return c;
    }

    private static boolean isNextCharDigit() {
        return new Random().nextBoolean();
    }


    private static int nextInt(int a, int b) {
        return new Random().nextInt(b - a + 1) + a;
    }

    /**
     * Calculates the number of bulls and cows for a given secret and guess.
     *
     * A bull is a correct letter in the correct position.
     * A cow is a correct letter in the wrong position.
     *
     * @param secret the secret string
     * @param guess the guess string
     * @return an array with the number of bulls in the first element and the number of cows in the second element
     */
    private static byte[] calculateGrade(String secret, String guess) {
        byte bulls = 0, cows = 0;
        // Iterate over each character in the guess string
        for(byte i = 0; i < guess.length(); i++) {
            // Check if the character at position i in the guess string is present in the secret string
            boolean guessCharInSecret = secret.contains(guess.substring(i, i + 1));

            // If the character is present in the secret string and is in the same position in both strings,
            // increment the number of bulls
            if (guessCharInSecret && guess.charAt(i) == secret.charAt(i)) {
                bulls++;

                // If the character is present in the secret string but is not in the same position,
                // increment the number of cows
            } else if (guessCharInSecret){
                cows++;
            }
        }

        // Return an array with the number of bulls in the first element and the number of cows in the second element
        byte[] grade =  new byte[]{bulls, cows};
        //Remove comment for debug purposes
        //System.out.printf("secret: %s   guess: %s   bulls: %d   cows: %d%n", secret, guess, grade[0], grade[1]);
        return grade;
    }

    /**
     * Builds a string representation of the grade for a given number of bulls and cows.
     *
     * @param bulls the number of bulls
     * @param cows the number of cows
     * @param secret the secret code
     * @return a string representation of the grade
     */
    private static String buildGradeResponse(int bulls, int cows, String secret) {
        String template = "", gradeResponse = "";

        // There are both bulls and cows
        if (bulls >= 1 && cows >= 1) {
            template = "Grade: %d bull(s) and %d cow(s).%n";
            gradeResponse = String.format(template, bulls, cows);
        }
        // There are only bulls
        else if (bulls >= 1) {
            template = "Grade: %d bull(s).%n";
            gradeResponse = String.format(template, bulls);
        }
        // There are only cows
        else if (cows >= 1) {
            template = "Grade: %d cow(s).%n";
            gradeResponse = String.format(template, cows);
        }
        // There are neither bulls nor cows
        else {
            template = "Grade: None.%n";
            gradeResponse = String.format(template);
        }
        return gradeResponse;
    }
}