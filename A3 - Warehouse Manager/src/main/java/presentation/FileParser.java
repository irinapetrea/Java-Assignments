package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;
import model.WarehouseOrder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is used to parse command files containing specific commands, according to the
 * requirements of the WarehouseManagement project.
 */

public class FileParser {
    private static final ClientBLL clientBLL = new ClientBLL();
    private static final OrderBLL orderBLL = new OrderBLL();
    private static final ProductBLL productBLL = new ProductBLL();

    /**
     * Extracts lines (commands) from a given file and parses them.
     *
     * @param infile path to a .txt file with one command per line
     */

    public static void parse(String infile) {
        File input = new File(infile);
        try {
            Scanner reader = new Scanner(input);
            String command;
            while (reader.hasNextLine()) {
                command = reader.nextLine();
                parseCommand(command);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the command passed as {@code String}, calling the specific BLL methods.
     * This function also handles pdf generation method calls.
     *
     * @param command string consisting of the command to be parsed
     */

    public static void parseCommand(String command) {
        if (command.toLowerCase().equals("report client")) {
            FileGenerator.reportClient(clientBLL.reportClient());
        } else if (command.toLowerCase().equals("report order")) {
            FileGenerator.reportOrder(orderBLL.reportOrder());
        } else if (command.toLowerCase().equals("report product")) {
            FileGenerator.reportProduct(productBLL.reportProduct());
        } else {
            // split after :
            String[] cmd1 = command.split(":");
            if (cmd1[0].trim().toLowerCase().equals("insert client")) {
                String[] cmd = cmd1[1].trim().split(",");
                Client client = new Client(cmd[0].trim(), cmd[1].trim());
                clientBLL.insertClient(client);
            } else if (cmd1[0].trim().toLowerCase().equals("insert product")) {
                String[] cmd = cmd1[1].trim().split(",");
                Product product = new Product(cmd[0].trim(), Integer.parseInt(cmd[1].trim()), Double.parseDouble(cmd[2].trim()));
                productBLL.insertProduct(product);
            } else if (cmd1[0].trim().toLowerCase().equals("order")) {
                String[] cmd = cmd1[1].trim().split(",");
                WarehouseOrder order = new WarehouseOrder(cmd[0].trim(), cmd[1].trim(), Integer.parseInt(cmd[2].trim()));
                if (orderBLL.insertOrder(order) == -1) FileGenerator.reportUnderStock(order);
                else FileGenerator.reportOrder(order);
            } else if (cmd1[0].trim().toLowerCase().equals("delete client")) {
                String[] cmd = cmd1[1].trim().split(",");
                clientBLL.deleteClient(cmd[0].trim());
            } else if (cmd1[0].trim().toLowerCase().equals("delete product")) {
                String[] cmd = cmd1[1].trim().split(",");
                productBLL.deleteProduct(cmd[0]);
            }
        }
    }


}
