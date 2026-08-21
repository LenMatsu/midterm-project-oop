import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Inventory inventory = new Inventory();

    public static void main(String[] args) {
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
        String category = Validators.readNonEmptyString(scanner, "Enter category: ");

        if (!Inventory.isValidCategory(category)) {
            System.out.println("Category " + category + " does not exist!");
            return;
        }

        String id = readUniqueId();
        String name = Validators.readNonEmptyString(scanner, "Enter name: ");
        int quantity = Validators.readNonNegativeInt(scanner, "Enter quantity: ");
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
        String id = Validators.readId(scanner, "Enter ID: ");
        Item item = inventory.findItemById(id);

        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        String field = Validators.readChoice(scanner, "Update Quantity or Price? ", "Quantity", "Price");

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
    }

    private static void removeItem() {
        String id = Validators.readId(scanner, "Enter ID: ");
        Item item = inventory.findItemById(id);

        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        inventory.removeItem(id);
        System.out.println("Item " + item.getName() + " has been removed from the inventory");
    }

    private static void displayItemsByCategory() {
        String category = Validators.readNonEmptyString(scanner, "Enter category: ");

        if (!Inventory.isValidCategory(category)) {
            System.out.println("Category " + category + " does not exist!");
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
        Display.printTableHeader(true);
        for (Item item : inventory.getAllItems()) {
            Display.printItemRow(item, true);
        }
    }

    private static void searchItem() {
        String id = Validators.readId(scanner, "Enter ID: ");
        Item item = inventory.findItemById(id);

        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println();
        System.out.println("Item found!");
        Display.printItemDetails(item);
    }

    private static void sortItems() {
        if (inventory.isEmpty()) {
            System.out.println("No items have been added yet.");
            return;
        }

        String sortBy = Validators.readChoice(scanner, "Sort by Quantity or Price? ", "Quantity", "Price");
        String order = Validators.readChoice(scanner, "Ascending or Descending? ", "Ascending", "Descending");

        List<Item> sortedItems = inventory.getSortedItems(sortBy, order);

        System.out.println();
        System.out.println("Sorted Items:");
        Display.printTableHeader(true);
        for (Item item : sortedItems) {
            Display.printItemRow(item, true);
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
        Display.printTableHeader(true);
        for (Item item : lowStockItems) {
            Display.printItemRow(item, true);
        }
    }
}