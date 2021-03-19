package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Product;
import model.WarehouseOrder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * This class' static methods are used to generate pdf files for the specific requirements of
 * the WarehouseManagement application.
 * The iText library is used for pdf generation.
 */

public class FileGenerator {
    private static int noClientReports = 1;
    private static int noProductReports = 1;
    private static int noOrderReports = 1;


    /**
     * Generates a pdf file with a bill for the successful order, in a list form.
     *
     * @param order is the valid order
     */
    public static void reportOrder(WarehouseOrder order) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("order" + order.getId() + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Order bill:", font);
        com.itextpdf.text.List list = new com.itextpdf.text.List();
        list.add(new ListItem("Client: " + order.getClientName(), font));
        list.add(new ListItem("Product: " + order.getProductName(), font));
        list.add(new ListItem("Quantity: " + order.getQuantity(), font));
        list.add(new ListItem("Total: $" + order.getTotal(), font));

        try {
            document.add(chunk);
            document.add(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * This function generates an out of stock bill for the failed order.
     *
     * @param order is the invalid order
     */

    public static void reportUnderStock(WarehouseOrder order) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("order" + order.getId() + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Order failed. Not enough items in stock.", font);

        try {
            document.add(chunk);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Generates a pdf file containing data from all the clients in the database, in a tabular form.
     *
     * @param clients the {@code List} of clients in the database
     */

    public static void reportClient(List<Client> clients) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("client_report" + noClientReports + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        noClientReports++;
        document.open();
        PdfPTable table = new PdfPTable(2);
        table.addCell("Name");
        table.addCell("Address");
        for (Client client : clients) {
            table.addCell(client.getName());
            table.addCell(client.getAddress());
        }

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Generates a pdf file containing data from all the products in the database, in a tabular form.
     *
     * @param products the {@code List} of products in the database
     */

    public static void reportProduct(List<Product> products) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("product_report" + noProductReports + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        noProductReports++;
        document.open();
        PdfPTable table = new PdfPTable(3);
        table.addCell("Name");
        table.addCell("Quantity");
        table.addCell("Price");
        for (Product product : products) {
            table.addCell(product.getName());
            table.addCell(product.getQuantity() + "");
            table.addCell(product.getPrice() + "");
        }

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Generates a pdf file containing data from all the orders in the database, in a tabular form.
     *
     * @param orders the {@code List} of orders in the database
     */

    public static void reportOrder(List<WarehouseOrder> orders) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("order_report" + noOrderReports + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        noOrderReports++;
        document.open();
        PdfPTable table = new PdfPTable(4);
        table.addCell("Client Name");
        table.addCell("Product Name");
        table.addCell("Quantity");
        table.addCell("Total");
        for (WarehouseOrder order : orders) {
            table.addCell(order.getClientName());
            table.addCell(order.getProductName());
            table.addCell(order.getQuantity() + "");
            table.addCell(order.getTotal() + "");
        }

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

}
