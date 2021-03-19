package Business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem, Serializable {
    private String name;
    private List<MenuItem> components;

    public CompositeProduct(String name) {
        this.name = name;
        components = new ArrayList<>();
    }

    public void addComponent(MenuItem item) {
        components.add(item);
    }

    public void removeComponent(MenuItem item) {
        components.remove(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getComponents() {
        return components;
    }

    public MenuItem getItem(String name) {
        for(MenuItem item:components) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public boolean contains(MenuItem item) {
        boolean contains = false;
        for(MenuItem menuItem:components) {
            if(menuItem.equals(item)) return true;
            else {
                if(menuItem instanceof CompositeProduct) {
                    if(((CompositeProduct)menuItem).contains(item)) return true;
                }
            }
        }
        return false;
    }

    @Override
    public float computePrice() {
        float price = 0;
        for (MenuItem item : components) {
            price += item.computePrice();
        }
        return price;
    }
}
