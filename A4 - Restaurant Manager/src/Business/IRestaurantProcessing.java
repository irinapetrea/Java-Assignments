package Business;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRestaurantProcessing {
    /**
     * Creates and adds a new MenuItem, which will be instance of a BaseProduct since the method takes
     * a String and a float.
     * @param name name of the new item to be added
     * @param price price of the new item to be added
     * @pre name != null, price greater than 0
     */
    void createMenuItem(String name, float price);

    /**
     * Creates and adds a new MenuItem, which will be instance of CompositeProduct since the method only takes a String.
     * @param name name of the menuItem to be created
     * @pre name != null
     */
    void createMenuItem(String name);

    /**
     * Adds a MenuItem to the composition of an existing MenuItem which is an instance of CompositeProduct
     * @param item name of the composite item
     * @param componentName the name of the component to be added
     * @pre item != null and componentName != null
     * @pre menuItems.get(componentName) != null and menuItems.get(item) != null
     * @post menuItems.get(item).contains(menuItems.get(componentName))
     */
    void addItemComponent(String item, String componentName);

    /**
     * Removes a component from the composition of an item
     * @param item composite item from where the component will be removed
     * @param componentName name of the component to be removed from the item given as first parameter
     * @pre item != null and componentName != null
     * @post !menuItems.get(item).contains(menuItems.get(componentName))
     */
    void removeItemComponent(String item, String componentName);

    /**
     * Modifies the parameters of a menuItem. Price can be changed as well for the BaseProducts.
     * @param item name of the item which will be updated
     * @param name the new name of the item
     * @param price the new price of the item
     * @pre item != null and name != null and price greater than or equal to 0
     */
    void editMenuItem(String item, String name, float price);

    /**
     * Deletes a menu item from the list, as well as every composite product that contains this item.
     * @param name name of the item to be deleted from the menu
     * @pre name != null
     * @post for all menuItem : menuItems.values(), !menuItem.contains(menuItems.get(name))
     */
    void deleteMenuItem(String name);

    /**
     * Creates an order and adds it to the order list. Also creates a Map entry for the new order
     * which will be populated with MenuItems
     * @param tableNumber number of the table associated with the order
     * @param date date when the order was picked up
     * @pre tableNumber greater than 0 and date != null
     */

    void createOrder(int tableNumber, Date date);

    /**
     * Adds the MenuItem with the name item to the Order with the ID order.
     * @param orderID id of the order to which the item will be added
     * @param item name of the item which will be added to the order
     * @pre orderID greater than 0 and orderID less than orders.size() and menuItems.get(item) != null
     */

    void addOrderItem(int orderID, String item);

    /**
     * Computes the price for the order with the ID given as parameter.
     * @param orderID id of the order for which price will be computed
     * @return bill price for the order with orderID given as parameter
     * @pre orderID less than orders.size()
     */
    float computeOrderPrice(int orderID);

    /**
     * Generates a txt bill of the order with the ID given as parameter.
     * @param orderID id of the order for which bill will be generated
     * @pre orderID less than orders.size()
     * @throws IOException on failure
     */
    void generateBill(int orderID) throws IOException;
    void notifyObserver(MenuItem menuItem);

    Map<String, MenuItem> getMenuItems();
    Map<Order, List<MenuItem>> getOrderListMap();
    List<Order> getOrders();

    String getName();
}
