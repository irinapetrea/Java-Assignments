package model;

public class Product {

    private String name;
    private int quantity;
    private double price;

    public Product() {
        this.name = null;
        this.quantity = 0;
        this.price = 0.0;
    }

    public Product(String name, int quantity, double price) {
        this();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return name + " " + quantity + " " + price;
    }
}
