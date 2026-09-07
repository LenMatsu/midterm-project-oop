import java.util.Scanner;

public class Validators {

    private static final String CANCEL_SYMBOL = "/";

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;
        boolean valid = false;

        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            valid = !input.isEmpty();
            if (!valid) {
                System.out.println("This field cannot be empty. Try again.");
            }
        } while (!valid);

        return input;
    }

    public static String readId(Scanner scanner, String prompt) {
        String input;
        boolean valid = false;

        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            valid = input.matches("[a-zA-Z0-9]+");
            if (!valid) {
                System.out.println("ID must be alphanumeric and non-empty. Try again.");
            }
        } while (!valid);

        return input;
    }

    public static int readPositiveInt(Scanner scanner, String prompt) {
        int value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Quantity must be greater than 0. Try again.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        } while (!valid);

        return value;
    }

    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        int value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            try {
                value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Quantity cannot be negative. Try again.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        } while (!valid);

        return value;
    }

    public static double readPositiveDouble(Scanner scanner, String prompt) {
        double value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Price must be greater than 0. Try again.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number. Try again.");
            }
        } while (!valid);

        return value;
    }

    public static String readChoice(Scanner scanner, String prompt, String option1, String option2) {
        String result = null;
        boolean valid = false;

        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equals(CANCEL_SYMBOL)) {
                throw new CancelException();
            }

            if (input.equalsIgnoreCase(option1) || input.equals("1")) {
                result = option1;
                valid = true;
            } else if (input.equalsIgnoreCase(option2) || input.equals("2")) {
                result = option2;
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter '1' for " + option1 + " or '2' for " + option2 + ".");
            }
        } while (!valid);

        return result;
    }
}