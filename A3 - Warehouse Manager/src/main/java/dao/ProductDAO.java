package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Product-specific update method that updates the quantity of a given name product.
     *
     * @param name     the name of the product whose quantity is to be updated
     * @param quantity the new quantity value
     */
    @SuppressWarnings("unchecked")
    public void updateQuantity(String name, int quantity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        String query = "UPDATE product SET quantity = ? WHERE name = ?";

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, name);

            result = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }
}
