import Business.Restaurant;
import Data.RestaurantSerializer;
import Presentation.AdminWaiterGUI;
import Presentation.ChefGUI;

public class MainClass {
    public static void main(String[] args) {
        Restaurant restaurant;
        if(args.length == 0) {

            restaurant = new Restaurant("El Greco", new ChefGUI());

            restaurant.createMenuItem("tomato", 1.0f);
            restaurant.createMenuItem("feta cheese", 3.0f);
            restaurant.createMenuItem("orange juice", 2.0f);
            restaurant.createMenuItem("spring water", 2.0f);
            restaurant.createMenuItem("olive oil", 1.0f);
            restaurant.createMenuItem("salad");
            restaurant.addItemComponent("salad", "tomato");
            restaurant.addItemComponent("salad", "feta cheese");
            restaurant.addItemComponent("salad", "olive oil");

            restaurant.createMenuItem("breakfast");
            restaurant.addItemComponent("breakfast", "salad");
            restaurant.addItemComponent("breakfast", "spring water");


        } else {
            restaurant = RestaurantSerializer.deserialize(args[0]);
        }

        AdminWaiterGUI admin = new AdminWaiterGUI(restaurant);
        admin.setVisible(true);
    }
}

