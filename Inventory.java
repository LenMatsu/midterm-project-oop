import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Inventory {

    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public static boolean isValidCategory(String category) {
        return category.equalsIgnoreCase("Clothing")
                || category.equalsIgnoreCase("Electronics")
                || category.equalsIgnoreCase("Entertainment");
    }

    public boolean isIdTaken(String id) {
        return findItemById(id) != null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item findItemById(String id) {
        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(String id) {
        Item item = findItemById(id);
        if (item == null) {
            return false;
        }
        items.remove(item);
        return true;
    }

    public List<Item> getItemsByCategory(String category) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Item> getAllItems() {
        return items;
    }

    public List<Item> getSortedItems(String sortBy, String order) {
        List<Item> sorted = new ArrayList<>(items);
        Comparator<Item> comparator;

        if (sortBy.equalsIgnoreCase("Quantity")) {
            comparator = Comparator.comparingInt(Item::getQuantity);
        } else {
            comparator = Comparator.comparingDouble(Item::getPrice);
        }

        if (order.equalsIgnoreCase("Descending")) {
            comparator = comparator.reversed();
        }

        sorted.sort(comparator);
        return sorted;
    }

    public List<Item> getLowStockItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() <= 5) {
                result.add(item);
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}