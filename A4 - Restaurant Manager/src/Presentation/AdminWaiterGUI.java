package Presentation;

import Business.MenuItem;
import Business.*;
import Data.RestaurantSerializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;

public class AdminWaiterGUI extends JFrame {
    IRestaurantProcessing restaurantProcessing;

    private JCheckBox baseProductCheckBox;
    private JCheckBox compositeProductCheckBox;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JComboBox addItemItems;
    private JButton addComponentButton;
    private JComboBox addItemComponents;
    private JTabbedPane tabbedPane1;
    private JComboBox editItems;
    private JTextField editItemsName;
    private JPanel restaurant;
    private JPanel addItem;
    private JPanel createItem;
    private JPanel editItem;
    private JTextField editItemsPrice;
    private JButton removeComponentButton;
    private JButton editItemButton;
    private JButton createItemButton;
    private JComboBox removableComponents;
    private JLabel editItemsPriceLabel;
    private JLabel editItemsNameLabel;
    private JButton removeItemButton;
    private JLabel createItemNameLabel;
    private JLabel createItemPriceLabel;
    private JTextField waiterTableNumber;
    private JPanel createOrder;
    private JButton createOrderButton;
    private JComboBox orderComboBox;
    private JComboBox waiterItemsComboBox;
    private JButton addItemButton;
    private JButton computePriceButton;
    private JLabel orderPriceLabel;
    private JButton generateBillButton;
    private JButton orderShowItemsButton;
    private JButton adminShowItemsButton;


    public AdminWaiterGUI(IRestaurantProcessing restaurantProcessing) {

        this.restaurantProcessing = restaurantProcessing;

        fillAdminComboBoxes();
        fillOrders(orderComboBox);
        defineWaiterListeners();
        defineEditListeners();
        defineWindowListener();


        baseProductCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                priceTextField.setVisible(true);
                createItemPriceLabel.setVisible(true);
            }
        });
        compositeProductCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                priceTextField.setVisible(false);
                createItemPriceLabel.setVisible(false);
            }
        });
        createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = nameTextField.getText();
                if (itemName != null) {
                    if (baseProductCheckBox.isSelected()) {
                        String priceString = priceTextField.getText();
                        float price = Float.parseFloat(priceString);
                        restaurantProcessing.createMenuItem(itemName, price);
                    } else {
                        restaurantProcessing.createMenuItem(itemName);
                    }
                    fillAdminComboBoxes();
                }
            }
        });
        addItemItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) addItemItems.getSelectedItem();
                fillItemsExcept(addItemComponents, compositeName);
            }
        });
        addComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) addItemItems.getSelectedItem();
                String itemName = (String) addItemComponents.getSelectedItem();
                restaurantProcessing.addItemComponent(compositeName, itemName);
                fillAdminComboBoxes();
            }
        });
        adminShowItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog popup = new JDialog();
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                String[] columns = new String[] {"Name", "Price"};
                int noRows = restaurantProcessing.getMenuItems().size();
                String[][] data = new String[noRows][2];

                int rowIndex = 0;
                for (MenuItem item : restaurantProcessing.getMenuItems().values()) {
                    data[rowIndex][0] = item.getName();
                    data[rowIndex][1] = "" + item.computePrice();
                    rowIndex++;
                }
                JTable itemsTable = new JTable(data, columns);
                panel.add(new JScrollPane(itemsTable));
                popup.setTitle(restaurantProcessing.getName() + " Menu");
                popup.setContentPane(panel);
                popup.setMinimumSize(new Dimension(300, 300));
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        this.setSize(new Dimension(500, 520));
        this.setLocationRelativeTo(null);
        this.setTitle(restaurantProcessing.getName());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(restaurant);
    }

    private void defineWindowListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RestaurantSerializer.serialize((Restaurant)restaurantProcessing);
                super.windowClosing(e);
            }
        });
    }

    private void defineWaiterListeners() {
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableNumberString = waiterTableNumber.getText();
                restaurantProcessing.createOrder(Integer.parseInt(tableNumberString), new Date());
                fillOrders(orderComboBox);
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderID = (Integer)orderComboBox.getSelectedItem();
                String menuItem = (String)waiterItemsComboBox.getSelectedItem();
                restaurantProcessing.addOrderItem(orderID, menuItem);
            }
        });
        computePriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderID = (Integer)orderComboBox.getSelectedItem();
                orderPriceLabel.setText("" + restaurantProcessing.computeOrderPrice(orderID));
            }
        });
        orderShowItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog popup = new JDialog();
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                String[] columns = new String[] {"Order ID", "Table Number", "Date", "Price"};
                int noRows = restaurantProcessing.getOrders().size();
                String[][] data = new String[noRows][4];
                int rowIndex = 0;

                for (Order order : restaurantProcessing.getOrders()) {
                    data[rowIndex][0] = "" + order.getOrderID();
                    data[rowIndex][1] = "" + order.getTableNumber();
                    data[rowIndex][2] = order.getDate().toString();
                    data[rowIndex][3] = "" + restaurantProcessing.computeOrderPrice(order.getOrderID());
                    rowIndex++;
                }
                JTable itemsTable = new JTable(data, columns);
                panel.add(new JScrollPane(itemsTable));
                popup.setTitle(restaurantProcessing.getName() + " Orders");


                popup.setContentPane(panel);
                popup.setMinimumSize(new Dimension(600, 300));
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderID = (Integer)orderComboBox.getSelectedItem();
                try {
                    restaurantProcessing.generateBill(orderID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.out.println(ioException.getMessage());
                }
            }
        });
    }

    private void defineEditListeners() {
        editItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) editItems.getSelectedItem();
                if (compositeName != null) {
                    editItemsName.setText(compositeName);
                    MenuItem item = restaurantProcessing.getMenuItems().get(compositeName);
                    if (item instanceof CompositeProduct) {
                        fillComponents(removableComponents, compositeName);
                        editItemsPrice.setVisible(false);
                        editItemsPriceLabel.setVisible(false);
                        editItemsPrice.setText("");
                        removableComponents.setVisible(true);
                        removeComponentButton.setVisible(true);
                    } else {
                        editItemsPrice.setText("" + ((BaseProduct) item).computePrice());
                        removableComponents.removeAllItems();
                        editItemsPrice.setVisible(true);
                        editItemsPriceLabel.setVisible(true);
                        removableComponents.setVisible(false);
                        removeComponentButton.setVisible(false);
                    }
                }
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = (String) editItems.getSelectedItem();
                String itemNewName = editItemsName.getText();
                String priceString = editItemsPrice.getText();
                float price = priceString.equals("")? 0.0f : Float.parseFloat(priceString);
                restaurantProcessing.editMenuItem(itemName, itemNewName, price);
                fillAdminComboBoxes();
            }
        });
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String removed = (String)editItems.getSelectedItem();
                restaurantProcessing.deleteMenuItem(removed);
                fillAdminComboBoxes();
            }
        });
        removeComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = (String)editItems.getSelectedItem();
                String componentName = (String) removableComponents.getSelectedItem();
                restaurantProcessing.removeItemComponent(itemName, componentName);
                fillAdminComboBoxes();
            }
        });
    }

    private void fillItems(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (MenuItem item : restaurantProcessing.getMenuItems().values()) {
            comboBox.addItem(item.getName());
        }
    }

    private void fillCompositeItems(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (MenuItem item : restaurantProcessing.getMenuItems().values()) {
            if (item instanceof CompositeProduct) {
                comboBox.addItem(item.getName());
            }
        }
    }

    private void fillItemsExcept(JComboBox comboBox, String itemName) {
        comboBox.removeAllItems();
        for (MenuItem item : restaurantProcessing.getMenuItems().values()) {
            if (!item.getName().equals(itemName)) {
                comboBox.addItem(item.getName());
            }
        }
    }

    private void fillComponents(JComboBox comboBox, String compositeName) {
        comboBox.removeAllItems();
        CompositeProduct composite = (CompositeProduct) restaurantProcessing.getMenuItems().get(compositeName);
        for (MenuItem item : composite.getComponents()) {
            comboBox.addItem(item.getName());
        }
    }

    private void fillAdminComboBoxes() {
        fillCompositeItems(addItemItems);
        fillItemsExcept(addItemComponents, (String) addItemItems.getSelectedItem());
        fillItems(editItems);
        fillItems((waiterItemsComboBox));
        if (restaurantProcessing.getMenuItems().get(editItems.getSelectedItem()) instanceof CompositeProduct) {
            fillComponents(removableComponents, (String) editItems.getSelectedItem());
            editItemsPrice.setVisible(false);
            editItemsPriceLabel.setVisible(false);
        }

    }

    private void fillWaiterComboBoxes() {
        fillItems(waiterItemsComboBox);
        fillOrders(orderComboBox);
    }

    private void fillOrders(JComboBox comboBox) {
        comboBox.removeAllItems();
        for(Order order : restaurantProcessing.getOrders()) {
            comboBox.addItem(order.getOrderID());
        }
    }


}


