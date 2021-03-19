package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements CRUD methods for instances of the Model classes and their corresponding elements
 * in the database, through reflection. The SQL queries are also generated dynamically through reflection.
 *
 * @param <T> is the type of the Model class - Client, Product, WarehouseOrder
 */

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) (pt).getActualTypeArguments()[0];
    }

    /**
     * Finds the first object that meets the condition fieldName = field in the database, in the corresponding table.
     *
     * @param field     is the value used for filtering the data
     * @param fieldName is the name of the table column where {@code field} will be looked for
     * @return the first occurrence of the object of type T in its corresponding database table, null if
     * there is no occurrence
     */

    public T findByField(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(fieldName);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            resultSet = preparedStatement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (objects.isEmpty()) return null;
            return objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Inserts the given item into the corresponding table in the database.
     * Ignores static fields.
     *
     * @param item is the item to be inserted into the database
     */

    public void insert(T item) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        String query = createInsertQuery(type.getDeclaredFields());
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            int index = 1;

            for (Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) continue;
                field.setAccessible(true);
                preparedStatement.setObject(index, field.get(item));
                index++;
            }
            result = preparedStatement.executeUpdate();

        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Retrieves all objects in the corresponding table of the type T that have not been marked as deleted.
     *
     * @return a list of all non-deleted objects in the database table
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE deleted = 0";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Marks all rows where fieldName = field as deleted.
     *
     * @param field     the value to be tested against all the values in the fieldName column
     * @param fieldName name of the specific column in the database table
     */

    public void delete(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        String query = createDeleteQuery(fieldName);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            result = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Permanently deletes (using DELETE) all the rows marked as deleted.
     */

    public void wipe() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE deleted = 1";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Generates a single-field test query that also skips all rows marked as deleted.
     *
     * @param fieldName is the field/column name to be tested.
     * @return t
     */
    private String createSelectQuery(String fieldName) {
        String query = "SELECT * FROM " +
                type.getSimpleName() +
                " WHERE " + fieldName + "=? and deleted = 0";
        return query;
    }

    /**
     * Forms a single-field test query that updates the deleted column to 1 (true).
     *
     * @param fieldName is the field/column name to be tested.
     * @return the generated delete query
     */
    private String createDeleteQuery(String fieldName) {
        return "UPDATE " + type.getSimpleName() + " SET deleted = 1 WHERE " + fieldName + "=?";
    }

    /**
     * Forms a T-specific insert query for all the values in the table.
     *
     * @param fields contains all of Ts fields
     * @return the generated insert query
     */
    private String createInsertQuery(Field[] fields) {
        StringBuilder sb = new StringBuilder().append("INSERT INTO ").append(type.getSimpleName()).append("(");
        int noStatics = 0;
        for (int i = 0; i < fields.length - 1; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                noStatics++;
                continue;
            }
            sb.append(fields[i].getName()).append(",");
        }
        sb.append(fields[fields.length - 1].getName()).append(") VALUES (");
        sb.append("?,".repeat(fields.length - 1 - noStatics));
        sb.append("?)");
        return sb.toString();
    }

    /**
     * Converts a {@code ResultSet} into a {@code List} of T-type objects
     *
     * @param resultSet is the ResultSet to be converted
     * @return {@code List} of T-type objects obtained from the ResultSet
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) continue;
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException | IntrospectionException | InvocationTargetException | InstantiationException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
