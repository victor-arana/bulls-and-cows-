package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //testSuiteCalculateGrade();
        //startGame();
        while(true){
            generatePseudoRandomNumber((byte) 6);
        }
    }

    private static void startGame() {
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        byte secretCodeLength = scanner.nextByte();

        String secret = generatePseudoRandomNumber(secretCodeLength);
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

    /**
     * Generates a pseudo-random number of a given length  with
     * the following characteristics:
     * - Contains unique digits from 0 to 9.
     * - It can not start with the 0 digit
     *
     * @param len integer number between 0 and 9. If
     *            numberLength > 10 it returns -1.
     * @return secretCode
     */
    private static String generatePseudoRandomNumber(byte len) {
        // Validate length is in the range [0,9]
        if (!(len >= 0 && len <= 9)) return "-1";

        StringBuilder sb = new StringBuilder();
        sb.append(nextInt(1,10));
        while ( sb.length() < len ) {
            sb.append(nextIntWithout(0,10,sb.toString()));
        }
        return sb.toString();
    }

    private static int nextIntWithout(int a, int b, String exclusions) {
        int candidate = nextInt(a, b);
        while (exclusions.contains(String.valueOf(candidate))){
            candidate = nextInt(a, b);
        }
        return candidate;
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
            template = "Grade: %d bull(s) and %d cow(s). The secret code is %s.%n";
            gradeResponse = String.format(template, bulls, cows, secret);
        }
        // There are only bulls
        else if (bulls >= 1) {
            template = "Grade: %d bull(s). The secret code is %s.%n";
            gradeResponse = String.format(template, bulls, secret);
        }
        // There are only cows
        else if (cows >= 1) {
            template = "Grade: %d cow(s). The secret code is %s.%n";
            gradeResponse = String.format(template, cows, secret);
        }
        // There are neither bulls nor cows
        else {
            template = "Grade: None. The secret code is %s.%n";
            gradeResponse = String.format(template, secret);
        }
        return gradeResponse;
    }

    private static void testSuiteGeneratePseudoRandomNumber() {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String[] columns = (scanner.nextLine()).split(","); 
            byte length = Byte.parseByte(columns[0]);
            String expectedMessage = columns[1];
            //long actualValue = generatePseudoRandomNumber(length);
            showPseudoRandomNumber(length);
            //System.out.printf("Length: %d, expected: %s , actual: %d %n", length, expectedMessage, actualValue);
        }
    }

    private static void showPseudoRandomNumber(byte digits) {
        String template = "";
        String randomNumber = generatePseudoRandomNumber(digits);
        if (randomNumber.equals("-1")) {
            template = "Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n";
            System.out.printf(template, digits);
        } else {
            template = "The random secret number is %s.%n";
            System.out.printf(template, randomNumber);
        }
    }

    private static void testSuiteCalculateGrade() {
        String s = "9305"; // secret

        // Guesses for 4 bulls, 0 cows
        System.out.println("4 bulls, 0 cows:");
        testCalculateGrade(new String[]{"9305"}, s);

        // Guesses for 0 bulls, 4 cows
        System.out.println("0 bulls, 4 cows:");
        testCalculateGrade(new String[]{"3059", "3590", "5930", "5039"}, s);

        // Guesses for 3 bulls
        System.out.println("3 bulls:");
        testCalculateGrade(new String[]{"9306", "9385", "9505", "1305"}, s);

        // Guesses for 2 bulls, 2 cows
        System.out.println("2 bulls, 2 cows");
        testCalculateGrade(new String[]{"9350", "9035", "5309", "3905"}, s);

        // Guesses for 0 bulls, 2 cows
        System.out.println("0 bulls, 2 cows ");
        testCalculateGrade(new String[]{"1293", "5012", "3512", "5129"}, s);

        // Guesses for 0 bulls, 0 cows
        System.out.println("0 bulls, 0 cows ");
        testCalculateGrade(new String[]{"1246", "7184", "4862", "2718"}, s);

        // Repetitive digits
        System.out.println("Repetitive 0 bulls, 0 cows");
        testCalculateGrade(new String[]{"1111"}, s);

        System.out.println("Repetitive 1 bulls, 3 cows");
        testCalculateGrade(new String[]{"9999"}, s);

        System.out.println("Repetitive 2 bulls, 2 cows");
        testCalculateGrade(new String[]{"9955"}, s);

        System.out.println("Repetitive ");
    }

    private static void testCalculateGrade(String[] guesses, String s) {
        for (int i = 0; i < guesses.length; i++) {
            byte[] grade = calculateGrade(s,guesses[i]);
            System.out.printf("secret: %s   guess: %s   bulls: %d   cows: %d%n", s, guesses[i], grade[0], grade[1]);
        }
    }




}
