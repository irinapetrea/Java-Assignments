package model;

public class WarehouseOrder {
    public static int currentID = 0;

    private int id;
    private int quantity;
    private double total;
    private String clientName;
    private String productName;

    public WarehouseOrder() {
        currentID++;
        this.id = currentID;
    }

    public WarehouseOrder(String clientName, String productName, int quantity) {
        this();
        this.quantity = quantity;
        this.clientName = clientName;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String toString() {
        return clientName + " " + productName + " " + quantity + "  $" + total;
    }
}
