package bullscows;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String code = generateCode(scanner);
        System.out.println("Secret code is: " + code);

        System.out.println("Okay, let's start a game!");
        int turnCounter = 1;
        do {
            turnCounter(turnCounter);
            String guess = scanner.next();
            assert code != null;
            int[] counterBC = Grade(guess, code);
            if (counterBC[0] == guess.length()) {
                printGrade(counterBC);
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else {
                printGrade(counterBC);
                turnCounter++;
            }
        } while(true);
    }

    private static String generateCode(Scanner scanner) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        int userCodeLength;
        int userUsedSymbols;
        do {
            userCodeLength = getCodeLength(scanner);
            if (checkCodeLength(userCodeLength)) {
                userUsedSymbols = getSymbolUsed(scanner);
            } else {return null;}
            if (checkUsedSymbols(userUsedSymbols,userCodeLength)) {
                StringBuilder codeSet = createCodeSet(userUsedSymbols);
                System.out.println(codeSet);
                for (int i = 0; i < userCodeLength; ) {
                    int pseudo = random.nextInt(userUsedSymbols);
                    String codeChar = String.valueOf(codeSet.toString().charAt(pseudo));
                    if (!code.toString().contains(codeChar)) {
                        code.append(codeChar);
                        ++i;
                    }
                }
                break;
            }
        } while (true);

        printStartingMessage(userCodeLength, userUsedSymbols);

        return String.valueOf(code);
    }

    private static StringBuilder createCodeSet(int number) {
        StringBuilder setForCode = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");
        if (number <= 10) {
            setForCode.delete(10, 36);
        } else if (number < 36) {
            setForCode.delete(number, 36);
        }
        return setForCode;
    }

    private static String getUserInput(Scanner scanner) {
        return scanner.nextLine();
    }

    private static int getCodeLength(Scanner scanner) {
        System.out.println("Input the length of the secret code:");
        var s = getUserInput(scanner);
        int num = 0;
        try {
            num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn 't a valid number.", s);
            System.exit(1);
        }
        return num;
    }

    private static boolean checkCodeLength(int num){
        if (num > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols .%n", num);
            System.exit(1);
        }
        return true;
    }

    private static int getSymbolUsed(Scanner scanner){
        System.out.println("Input the number of possible symbols in the code:");
        var s = getUserInput(scanner);
        int sym = 0;
        try {
            sym = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", s);
            System.exit(1);
        }
        return sym;
    }

    private static boolean checkUsedSymbols(int sym, int len){
        if (sym < len) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n",len,sym);
            System.exit(1);
        } else if (sym > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(1);
        }
        return true;
    }

    private static void printStartingMessage(int a,int b) {
        String s1 = "*".repeat(a);
        String s2;
        char c = (char) (97 + b - 1 - 10);
        if (b > 11){
            s2 = ", " + 'a' + "-" + c;
        }else {
            s2 = ", a";
        }
        System.out.printf("The secret is prepared: %s (0-9%s).",s1,s2);
    }

    private static void turnCounter(int counter) {
        System.out.printf("Turn %d:%n",counter);
    }

    private static int[] Grade(String guess,String code) {
        char[] inputArr = guess.toCharArray();
        char[] secCodeArr = code.toCharArray();
        int bullCounter = 0;
        int cowCounter = 0;
        for (int i = 0; i < guess.length();i++){
            for (int j =0; j < code.length();j++){
                if (inputArr[i] == secCodeArr[j] && i == j) {
                    bullCounter++;
                }
                if (inputArr[i] == secCodeArr[j] && i != j) {
                    cowCounter++;
                }
            }
        }
        return new int[]{bullCounter, cowCounter};
    }

    public static void printGrade(int[] counterBC){
        String grade = null;
        int bulls = counterBC[0];
        int cows = counterBC[1];
        String bu = bulls > 1 ? "bulls" : "bull";
        String co = cows > 1 ? "cows" : "cow";

        if (bulls == 0 && cows == 0) {
            grade = "Grade: None";
        } else if (bulls >= 1 && cows == 0) {
            grade = String.format("Grade: %d %s", bulls, bu);

        } else if (bulls == 0 && cows >= 1) {
            grade = String.format("Grade: %d %s", cows, co);

        } else if (bulls >= 1 && cows >= 1) {
            grade = String.format("Grade: %d %s and %d %s", bulls, bu, cows, co);
        }

        System.out.println(grade);
    }
}
