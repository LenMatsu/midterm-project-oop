public abstract class Item {

    private String id;
    private String name;
    private int quantity;
    private double price;

    public Item(String id, String name, int quantity, double price) {
        setId(id);
        setName(name);
        setQuantity(quantity);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        if (id == null || !id.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("ID must be alphanumeric and non-empty.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    public abstract String getCategory();
}