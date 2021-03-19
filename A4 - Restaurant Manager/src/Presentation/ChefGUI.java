package Presentation;

import Business.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer {
    private JLabel cookingLabel;
    private JPanel content;

    public ChefGUI() {
        content = new JPanel();
        cookingLabel = new JLabel("Cooking queue:");
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(cookingLabel);

        this.setContentPane(content);
        this.setMinimumSize(new Dimension(300, 300));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        MenuItem item = (MenuItem)arg;
        JLabel orderLabel = new JLabel(item.getName());
        content.add(orderLabel);
        content.revalidate();
        content.repaint();
    }

}
