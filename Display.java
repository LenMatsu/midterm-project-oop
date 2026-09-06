import java.util.List;

public class Display {

    public static void printBanner() {
        System.out.println("=========================================");
        System.out.println("      INVENTORY MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("(Type / at any prompt to cancel and return to the main menu.)");
    }

    public static void printItemTable(List<Item> items) {
        int idWidth = 10;
        int nameWidth = 20;
        int qtyWidth = 10;
        int priceWidth = 10;
        int categoryWidth = 15;

        String border = "+" + "-".repeat(idWidth + 2)
                + "+" + "-".repeat(nameWidth + 2)
                + "+" + "-".repeat(qtyWidth + 2)
                + "+" + "-".repeat(priceWidth + 2)
                + "+" + "-".repeat(categoryWidth + 2)
                + "+";

        String rowFormat = "| %-" + idWidth + "s | %-" + nameWidth + "s | %" + qtyWidth
                + "s | %" + priceWidth + "s | %-" + categoryWidth + "s |%n";

        System.out.println(border);
        System.out.printf(rowFormat, "ID", "Name", "Quantity", "Price", "Category");
        System.out.println(border);

        for (Item item : items) {
            String quantity = String.valueOf(item.getQuantity());
            String price = String.format("%.2f", item.getPrice());
            System.out.printf(rowFormat, item.getId(), item.getName(), quantity, price, item.getCategory());
        }

        System.out.println(border);
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("Menu");
        System.out.println("1 - Add Item");
        System.out.println("2 - Update Item");
        System.out.println("3 - Remove Item");
        System.out.println("4 - Display Items by Category");
        System.out.println("5 - Display All Items");
        System.out.println("6 - Search Item");
        System.out.println("7 - Sort Items");
        System.out.println("8 - Display Low Stock Items");
        System.out.println("9 - Exit");
        System.out.print("Choose an option: ");
    }

    public static void printTableHeader(boolean includeCategory) {
        if (includeCategory) {
            System.out.printf("%-10s %-20s %-10s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
        } else {
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Quantity", "Price");
        }
    }

    public static void printItemRow(Item item, boolean includeCategory) {
        if (includeCategory) {
            System.out.printf("%-10s %-20s %-10d %-10.2f %-15s%n",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice(), item.getCategory());
        } else {
            System.out.printf("%-10s %-20s %-10d %-10.2f%n",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice());
        }
    }

    public static void printItemDetails(Item item) {
        System.out.println("ID: " + item.getId());
        System.out.println("Name: " + item.getName());
        System.out.println("Quantity: " + item.getQuantity());
        System.out.printf("Price: %.2f%n", item.getPrice());
        System.out.println("Category: " + item.getCategory());
    }
}