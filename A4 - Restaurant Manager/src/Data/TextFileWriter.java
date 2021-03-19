package Data;

import Business.MenuItem;
import Business.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TextFileWriter {
    private static final boolean append = false;

    public static void generateOrderBill(Order order, List<MenuItem> items, float price) throws IOException {
        File bill = new File("bill" + order.getOrderID() + ".txt");
        bill.createNewFile();

        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("%s%n", order.toString());
        for(MenuItem menuItem : items) {
            printWriter.printf("%s%n", menuItem.getName() + " " + menuItem.computePrice());
        }
        printWriter.printf("%s%n", "Total " + price);
        printWriter.close();
    }
}
