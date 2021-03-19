package bll;

import dao.AbstractDAO;
import dao.OrderDAO;
import model.Product;
import model.WarehouseOrder;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that implements the business logic for the Order table/model
 */
public class OrderBLL {
    private final AbstractDAO<WarehouseOrder> dao;

    public OrderBLL() {
        dao = new OrderDAO();
    }

    public WarehouseOrder findOrderByID(int id) {
        WarehouseOrder order = dao.findByField(id, "id");
        if (order == null) {
            throw new NoSuchElementException("No order with the id " + id + " was found.");
        }
        return order;
    }

    /**
     * An order is only inserted if it is valid, meaning that the client does not ask for more products
     * than there are in stock.
     *
     * @param order the order to be validated and inserted in the database
     * @return 0 if the order has been completed(inserted) successfully, -1 if it is invalid
     */
    public int insertOrder(WarehouseOrder order) {
        Product product = (new ProductBLL()).findProductByName(order.getProductName());
        int difference = product.getQuantity() - order.getQuantity();
        if (difference < 0) {
            return -1;
        }
        double price = product.getPrice() * order.getQuantity();
        order.setTotal(price);
        (new ProductBLL()).updateQuantity(product.getName(), difference);
        dao.insert(order);
        return 0;
    }

    public void deleteOrder(WarehouseOrder order) {
        dao.delete(order.getId(), "id");
    }

    public void deleteOrder(int id) {
        dao.delete(id, "id");
    }

    public void deleteClientOrders(String clientName) {
        dao.delete(clientName, "clientName");
    }

    public void deleteAll() {
        dao.delete(0, "deleted");
        wipe();
    }

    public void wipe() {
        dao.wipe();
    }

    public List<WarehouseOrder> reportOrder() {
        return dao.findAll();
    }
}
