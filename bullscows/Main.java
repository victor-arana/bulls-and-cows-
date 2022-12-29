package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        byte secretCodeLength = scanner.nextByte();
        String secretCode = generatePseudoRandomNumber(secretCodeLength);
        System.out.printf("Secret code: %s", secretCode);
       // String secret = "9305";

        //String guess = scanner.next();
        
       // byte[] grade = calculateGrade(guess, secret);
       // System.out.println(showGrade(grade[0], grade[1], secret));

        testSuiteGeneratePseudoRandomNumber();

        //testSuiteGuesses();
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
        do {
            String number = String.valueOf(System.nanoTime());
            number = (new StringBuilder(number)).reverse().toString();
            // Exclude 0 from first position
            if (number.startsWith("0")){
                number = number.substring(1, number.length());
            }
            // Build the pseudo random number excluding repeated digits
            for(int i = 0; i < number.length(); i++) {
                String c = String.valueOf(number.charAt(i));
                // Append only new characters
                sb.append(sb.indexOf(c) == -1 ? c : "");
                // Stop if we reached  the desired length
                if (sb.length() >= len) break;
            }
        } while (sb.length() < len);
        return sb.toString();
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
