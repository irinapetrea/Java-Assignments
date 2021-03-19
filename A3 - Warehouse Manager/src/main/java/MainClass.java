import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import presentation.FileParser;

public class MainClass {
    public static void main(String[] args) {
        //System.out.println("Hello World!");
        (new OrderBLL()).deleteAll();
        (new ClientBLL()).deleteAll();
        (new ProductBLL()).deleteAll();
        FileParser.parse(args[0]);
    }
}
