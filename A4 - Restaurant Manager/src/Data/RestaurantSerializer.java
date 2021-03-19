package Data;

import Business.Restaurant;

import java.io.*;

public class RestaurantSerializer {
    public static void serialize(Serializable object) {
        try {
            FileOutputStream file = new FileOutputStream("restaurant.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Restaurant deserialize(String path) {
        Restaurant restaurant;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            restaurant = (Restaurant)in.readObject();
            in.close();
            file.close();
            return restaurant;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
