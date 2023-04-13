package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rating = readRatings(askUserName());
        boolean gameContinue = true;
        String[] gameOptions = getGameRules();
        do {

            String userInput = scanner.next();
            if (checkInput(gameOptions, userInput)) {
                int user = Arrays.asList(gameOptions).indexOf(userInput);
                int[] winCondition = new int[gameOptions.length / 2];
                generateWinConditionList(user, gameOptions, winCondition);
                rating = rating + determineWinner(user, computerChoice(gameOptions), gameOptions, winCondition);
            } else if (userInput.equals("!exit")) {
                System.out.println("Bye!");
                gameContinue = false;
            } else if (userInput.equals("!rating")) {
                printRating(rating);
            } else {
                System.out.println("Invalid input");
            }
        } while (gameContinue);
    }

    public static int computerChoice(String[] gameOptions) {
        Random random = new Random();
        return random.nextInt(gameOptions.length);
    }

    public static int determineWinner(int user, int computerChoice, String[] gameOptions, int[] winCondition) {
        if (user == computerChoice) {
            System.out.println("There is a draw " + gameOptions[user]);
            return 50;
        } else if (checkArray(winCondition, computerChoice)) {
            System.out.printf("Sorry, but the computer chose %s%n", gameOptions[computerChoice]);
            return 0;
        } else {
            System.out.printf("Well done. The computer chose %s and failed%n", gameOptions[computerChoice]);
            return 100;
        }
    }

    public static void generateWinConditionList(int user, String[] gameOptions, int[] winConditionList) {
        int steps = 0;
        int value = user;
        for (int i = 0; i < gameOptions.length / 2; i++) {
            if (value < gameOptions.length) {
                value++;
                winConditionList[steps] = value;
            } else if (value == gameOptions.length) {
                value = 0;
                winConditionList[steps] = value;

            } else {
                value++;
                winConditionList[steps] = value;
            }
            steps++;


        }

    }

    public static boolean checkInput(String[] arr, String num) {
        for (String element : arr) {
            if (element.equals(num)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkArray(int[] arr, int num) {
        for (int element : arr) {
            if (element == num) {
                return true;
            }
        }
        return false;
    }

    public static int readRatings(String user) {
        File file = new File(".\\rating.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String listUser = scanner.next();
                int rating = scanner.nextInt();
                if (listUser.equals(user)) {
                    return rating;
                }
            }
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("No file found ");
            return 0;
        }
    }

    public static String askUserName() {
        System.out.println("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.next();
        System.out.println("Hello, " + userName);
        return userName;
    }

    public static void printRating(int rating) {
        System.out.println("Your rating: " + rating);
    }

    public static String[] getGameRules() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("")) {
            System.out.println("Okay, let's start");
            return new String[]{"rock", "paper", "scissors"};

        } else {
            System.out.println("Okay, let's start");
            return input.split(",");
        }

    }
}


