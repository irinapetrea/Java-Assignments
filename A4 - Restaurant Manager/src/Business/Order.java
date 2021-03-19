package Business;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private final int tableNumber;
    private final int orderID;
    private final Date date;

    public Order(int tableNumber, int orderID, Date date) {
        this.tableNumber = tableNumber;
        this.orderID = orderID;
        this.date = date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return tableNumber == order.tableNumber &&
                orderID == order.orderID &&
                date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber, orderID, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "tableNumber=" + tableNumber +
                ", orderID=" + orderID +
                ", date=" + date +
                '}';
    }
}
