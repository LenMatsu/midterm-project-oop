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
            double price = Validators.readNonNegativeDouble(scanner, "Enter price: ");

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
        while (true) {
            System.out.println("Select category:");
            System.out.println("1 - Clothing");
            System.out.println("2 - Electronics");
            System.out.println("3 - Entertainment");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                case "Clothing":
                case "clothing":
                    return "Clothing";
                case "2":
                case "Electronics":
                case "electronics":
                    return "Electronics";
                case "3":
                case "Entertainment":
                case "entertainment":
                    return "Entertainment";
                case "/":
                case "Back":
                case "back":
                case "Cancel":
                case "cancel":
                    return null;
                default:
                    System.out.println("Category " + input + " does not exist!");
            }
        }
    }

    private static String readUniqueId() {
        while (true) {
            String id = Validators.readId(scanner, "Enter ID: ");
            if (inventory.isIdTaken(id)) {
                System.out.println("This ID already exists. Please enter a different ID.");
                continue;
            }
            return id;
        }
    }

    private static void updateItem() {
        try {
            Item item = null;

            while (item == null) {
                String id = Validators.readId(scanner, "Enter ID: ");
                item = inventory.findItemById(id);

                if (item == null) {
                    System.out.println("Item not found!");
                    String retry = Validators.readChoice(scanner, "Try again or go back to menu? (1 - Retry | 2 - Menu): ", "Retry", "Menu");
                    if (retry.equalsIgnoreCase("Menu")) {
                        return;
                    }
                }
            }

            String field = Validators.readChoice(scanner, "Update Quantity or Price? (1 - Quantity | 2 - Price): ", "Quantity", "Price");

            if (field.equalsIgnoreCase("Quantity")) {
                int oldValue = item.getQuantity();
                int newValue = Validators.readPositiveInt(scanner, "Enter new quantity: ");
                item.setQuantity(newValue);
                System.out.println("Quantity of Item " + item.getName() + " is updated from " + oldValue + " to " + newValue);
            } else {
                double oldValue = item.getPrice();
                double newValue = Validators.readNonNegativeDouble(scanner, "Enter new price: ");
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
        Display.printTableHeader(false);
        for (Item item : categoryItems) {
            Display.printItemRow(item, false);
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
            String query = Validators.readNonEmptyString(scanner, "Enter ID or Name to search: ");
            List<Item> results = inventory.searchItems(query);

            if (results.isEmpty()) {
                System.out.println("Item not found!");
                return;
            }

            if (results.size() == 1) {
                System.out.println();
                System.out.println("Item found!");
                Display.printItemDetails(results.get(0));
            } else {
                System.out.println();
                System.out.println(results.size() + " items found:");
                Display.printItemTable(results);
            }
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