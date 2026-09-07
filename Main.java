import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Inventory inventory = new Inventory();

    public static void main(String[] args) {
        Display.printBanner();

        boolean running = true;

        while (running) {
            Display.printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addItem();
                    break;
                case "2":
                    updateItem();
                    break;
                case "3":
                    removeItem();
                    break;
                case "4":
                    displayItemsByCategory();
                    break;
                case "5":
                    displayAllItems();
                    break;
                case "6":
                    searchItem();
                    break;
                case "7":
                    sortItems();
                    break;
                case "8":
                    displayLowStockItems();
                    break;
                case "9":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-9.");
            }
        }

        scanner.close();
    }

    private static void addItem() {
        try {
            String category = readCategoryChoice();

            if (category == null) {
                System.out.println("Returning to main menu.");
                return;
            }

            String id = readUniqueId();
            String name = Validators.readNonEmptyString(scanner, "Enter name: ");
            int quantity = Validators.readPositiveInt(scanner, "Enter quantity: ");
            double price = Validators.readPositiveDouble(scanner, "Enter price: ");

            Item item;
            if (category.equalsIgnoreCase("Clothing")) {
                item = new Clothing(id, name, quantity, price);
            } else if (category.equalsIgnoreCase("Electronics")) {
                item = new Electronics(id, name, quantity, price);
            } else {
                item = new Entertainment(id, name, quantity, price);
            }

            inventory.addItem(item);
            System.out.println("Item added successfully!");
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static String readCategoryChoice() {
        String category = null;
        boolean done = false;

        do {
            System.out.println("Select category:");
            System.out.println("1 - Clothing");
            System.out.println("2 - Electronics");
            System.out.println("3 - Entertainment");
            System.out.println("4 - Other (type manually)");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                case "Clothing":
                case "clothing":
                    category = "Clothing";
                    done = true;
                    break;
                case "2":
                case "Electronics":
                case "electronics":
                    category = "Electronics";
                    done = true;
                    break;
                case "3":
                case "Entertainment":
                case "entertainment":
                    category = "Entertainment";
                    done = true;
                    break;
                case "4":
                    String typed = Validators.readNonEmptyString(scanner, "Enter category: ");
                    if (Inventory.isValidCategory(typed)) {
                        category = normalizeCategory(typed);
                        done = true;
                    } else {
                        System.out.println("Category " + typed + " does not exist!");
                    }
                    break;
                case "/":
                case "Back":
                case "back":
                case "Cancel":
                case "cancel":
                    return null;
                default:
                    System.out.println("Category " + input + " does not exist!");
            }
        } while (!done);

        return category;
    }

    private static String normalizeCategory(String typed) {
        if (typed.equalsIgnoreCase("Clothing")) {
            return "Clothing";
        } else if (typed.equalsIgnoreCase("Electronics")) {
            return "Electronics";
        } else {
            return "Entertainment";
        }
    }

    private static String readUniqueId() {
        String id;
        boolean unique = false;

        do {
            id = Validators.readId(scanner, "Enter ID: ");
            unique = !inventory.isIdTaken(id);
            if (!unique) {
                System.out.println("This ID already exists. Please enter a different ID.");
            }
        } while (!unique);

        return id;
    }

    private static Item findItemByIdWithRetry() {
        Item item = null;

        while (item == null) {
            String id = Validators.readId(scanner, "Enter ID: ");
            item = inventory.findItemById(id);

            if (item == null) {
                System.out.println("Item not found!");
                String retry = Validators.readChoice(scanner, "Try again or go back to menu? (1 - Retry | 2 - Menu): ", "Retry", "Menu");
                if (retry.equalsIgnoreCase("Menu")) {
                    return null;
                }
            }
        }

        return item;
    }

    private static void updateItem() {
        try {
            Item item = findItemByIdWithRetry();

            if (item == null) {
                return;
            }

            String field = Validators.readChoice(scanner, "Update Quantity or Price? (1 - Quantity | 2 - Price): ", "Quantity", "Price");

            if (field.equalsIgnoreCase("Quantity")) {
                int oldValue = item.getQuantity();
                int newValue = Validators.readNonNegativeInt(scanner, "Enter new quantity: ");
                item.setQuantity(newValue);
                System.out.println("Quantity of Item " + item.getName() + " is updated from " + oldValue + " to " + newValue);
            } else {
                double oldValue = item.getPrice();
                double newValue = Validators.readPositiveDouble(scanner, "Enter new price: ");
                item.setPrice(newValue);
                System.out.printf("Price of Item %s is updated from %.2f to %.2f%n", item.getName(), oldValue, newValue);
            }
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static void removeItem() {
        try {
            String id = Validators.readId(scanner, "Enter ID: ");
            Item item = inventory.findItemById(id);

            if (item == null) {
                System.out.println("Item not found!");
                return;
            }

            inventory.removeItem(id);
            System.out.println("Item " + item.getName() + " has been removed from the inventory");
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static void displayItemsByCategory() {
        try {
            String category = readCategoryChoice();

            if (category == null) {
                System.out.println("Returning to main menu.");
                return;
            }

            List<Item> categoryItems = inventory.getItemsByCategory(category);

            if (categoryItems.isEmpty()) {
                System.out.println("No items found under " + category + ".");
                return;
            }

            System.out.println();
            System.out.println(category + " Items:");
            Display.printTableHeader();
            for (Item item : categoryItems) {
                Display.printItemRow(item);
            }
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static void displayAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items have been added yet.");
            return;
        }

        System.out.println();
        System.out.println("All Items:");
        Display.printItemTable(inventory.getAllItems());
    }

    private static void searchItem() {
        try {
            String id = Validators.readId(scanner, "Enter ID: ");
            Item item = inventory.findItemById(id);

            if (item == null) {
                System.out.println("Item not found!");
                return;
            }

            System.out.println();
            System.out.println("Item found!");
            Display.printItemDetails(item);
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static void sortItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items have been added yet.");
            return;
        }

        try {
            String sortBy = Validators.readChoice(scanner, "Sort by Quantity or Price? (1 - Quantity | 2 - Price): ", "Quantity", "Price");
            String order = Validators.readChoice(scanner, "Ascending or Descending? (1 - Ascending | 2 - Descending): ", "Ascending", "Descending");

            List<Item> sortedItems = inventory.getSortedItems(sortBy, order);

            System.out.println();
            System.out.println("Sorted Items:");
            Display.printItemTable(sortedItems);
        } catch (CancelException e) {
            System.out.println("Cancelled. Returning to main menu.");
        }
    }

    private static void displayLowStockItems() {
        List<Item> lowStockItems = inventory.getLowStockItems();

        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items.");
            return;
        }

        System.out.println();
        System.out.println("Low Stock Items:");
        Display.printItemTable(lowStockItems);
    }
}   