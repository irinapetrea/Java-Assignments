package bll;

import dao.AbstractDAO;
import dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that implements the business logic for the Product table/model
 */
public class ProductBLL {
    private final AbstractDAO<Product> dao;

    public ProductBLL() {
        dao = new ProductDAO();
    }

    public Product findProductByName(String name) {
        Product product = dao.findByField(name, "name");
        if (product == null) {
            throw new NoSuchElementException("No product with the name " + name + " was found.");
        }
        return product;
    }

    /**
     * Inserts a product into the corresponding table. If this product (name) already exists, the quantity is updated
     * to the sum of the existing product quantity and the new product quantity.
     *
     * @param product the product to be added
     */
    public void insertProduct(Product product) {
        Product p = dao.findByField(product.getName(), "name");
        if (p != null) {
            updateQuantity(product.getName(), product.getQuantity() + p.getQuantity());
        } else dao.insert(product);
    }

    public void updateQuantity(String name, int quantity) {
        ((ProductDAO) dao).updateQuantity(name, quantity);
    }

    public void deleteProduct(Product product) {
        dao.delete(product.getName(), "name");
    }

    public void deleteProduct(String name) {
        dao.delete(name, "name");
    }

    public void deleteAll() {
        dao.delete(0, "deleted");
        wipe();
    }

    public void wipe() {
        dao.wipe();
    }

    public List<Product> reportProduct() {
        return dao.findAll();
    }

}
