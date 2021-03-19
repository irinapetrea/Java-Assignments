package Business;

import Data.TextFileWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @invariant name != null and menuItems != null and observer != null
 */
public class Restaurant extends Observable implements IRestaurantProcessing, Serializable {
    private final String name;
    private Map<String, MenuItem> menuItems;
    private Map<Order, List<MenuItem>> orderListMap;
    private List<Order> orders;
    private static int orderID = 0;
    private final Observer observer;

    public Restaurant(String name, Observer observer) {
        this.name = name;
        menuItems = new HashMap<>();
        orders = new ArrayList<>();
        orderListMap = new HashMap<>();
        this.observer = observer;
        assert name != null && this.observer != null;
    }

    @Override
    public void createMenuItem(String name, float price) {
        assert name != null && Float.compare(price, 0.0f) > 0;
        menuItems.put(name, new BaseProduct(name, price));
    }

    @Override
    public void createMenuItem(String name) {
        assert name != null;
        menuItems.put(name, new CompositeProduct(name));
    }

    public void createMenuItem(MenuItem item) {
        assert item != null;
        menuItems.put(item.getName(), item);
        assert isWellFormed();
    }

    @Override
    public void addItemComponent(String item, String componentName) {
        assert item != null && componentName != null;
        assert menuItems.get(componentName) != null && menuItems.get(item) != null;
        MenuItem composite = menuItems.get(item);
        ((CompositeProduct) composite).addComponent(menuItems.get(componentName));
        assert ((CompositeProduct)menuItems.get(item)).contains(menuItems.get(componentName));
    }

    @Override
    public void removeItemComponent(String item, String componentName) {
        assert item != null && componentName != null;
        MenuItem compositeProduct = menuItems.get(item);
        MenuItem component = menuItems.get(componentName);
        ((CompositeProduct) compositeProduct).getComponents().remove(component);
        assert !((CompositeProduct)menuItems.get(item)).contains(menuItems.get(componentName));
    }

    @Override
    public void editMenuItem(String item, String name, float price) {
        assert item != null && name != null && Float.compare(price, 0.0f) >=0;
        MenuItem menuItem = menuItems.get(item);
        if (Float.compare(price, 0.0f) != 0) {
            ((BaseProduct) menuItem).setPrice(price);
        }
        menuItems.remove(menuItem.getName());
        menuItem.setName(name);
        createMenuItem(menuItem);
        assert isWellFormed();
    }

    @Override
    public void deleteMenuItem(String name) {
        assert name != null;
        MenuItem removed = menuItems.remove(name);
        MenuItem item;

        Iterator<Map.Entry<String, MenuItem>> entryIterator = menuItems.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, MenuItem> entry = entryIterator.next();
            item = entry.getValue();
            if (item instanceof CompositeProduct) {
                if (((CompositeProduct) item).contains(removed)) {
                    entryIterator.remove();
                }
            }
        }
        assert isWellFormed();
    }

    @Override
    public void createOrder(int tableNumber, Date date) {
        assert tableNumber > 0 && date != null;
        Order order = new Order(tableNumber, orderID, date);
        orderID++;
        orders.add(order);
        orderListMap.put(order, new ArrayList<>());
    }

    @Override
    public void addOrderItem(int orderID, String item) {
        assert orderID >=0 && orderID < orders.size() && menuItems.get(item) != null;
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                MenuItem menuItem = menuItems.get(item);
                if (menuItem instanceof CompositeProduct) notifyObserver(menuItem);
                orderListMap.get(order).add(menuItem);
                return;
            }
        }
    }

    @Override
    public float computeOrderPrice(int orderID) {
        assert orderID >=0 && orderID < orders.size();
        float price = 0;
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                for (MenuItem menuItem : orderListMap.get(order)) {
                    price += menuItem.computePrice();
                }
                return price;
            }
        }
        return -1;
    }

    /**
     * Method tests whether the menu structure remains well formed, meaning each menu item is
     * stored in the hashmap as a String, MenuItem pair with String.equals(MenuItem.getName()) == 0
     * @return true if the class is well formed
     */
    private boolean isWellFormed() {
        for (String key : menuItems.keySet()) {
            if (!key.equals(menuItems.get(key).getName())) return false;
        }
        return true;
    }

    public Map<String, MenuItem> getMenuItems() {
        return menuItems;
    }

    public Map<Order, List<MenuItem>> getOrderListMap() {
        return orderListMap;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getName() {
        return name;
    }

    @Override
    public void generateBill(int orderID) throws IOException {
        Order order = orders.get(orderID);
        TextFileWriter.generateOrderBill(order, orderListMap.get(order), computeOrderPrice(orderID));
    }

    @Override
    public void notifyObserver(MenuItem menuItem) {
        observer.update(this, menuItem);
    }
}
