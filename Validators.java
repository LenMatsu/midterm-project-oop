import java.util.Scanner;

public class Validators {

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("This field cannot be empty. Try again.");
                continue;
            }

            return input;
        }
    }

    public static String readId(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-Z0-9]+")) {
                System.out.println("ID must be alphanumeric and non-empty. Try again.");
                continue;
            }

            return input;
        }
    }

    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Quantity cannot be negative. Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        }
    }

    public static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Price must be greater than 0. Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number. Try again.");
            }
        }
    }

    public static String readChoice(Scanner scanner, String prompt, String option1, String option2) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(option1)) {
                return option1;
            } else if (input.equalsIgnoreCase(option2)) {
                return option2;
            } else {
                System.out.println("Invalid input. Please enter '" + option1 + "' or '" + option2 + "'.");
            }
        }
    }
}