package com.example.cafeapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.cafeapp.dao.MenuItemDao;
import com.example.cafeapp.dao.OrderDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//Omar Alaa Eldeen 221001732 & Amin Mossadag 222000055
//Searched for each import usage on Google,and learnt how are they meant to be used.
public class CafeRestaurantApp extends Application {
    static String appPath = new File("").getAbsolutePath().concat("\\cafeapp");

    void saveCartToFile() {
        try {
            new ObjectMapper().writeValue(new File(appPath), ShoppingCart.getItems());
        } catch (IOException e) {
            Logger.getLogger(e.getMessage());
        }

    }

    // Read From File [had an issue with it and I need some help to fix it]
    void loadCartFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            @SuppressWarnings("unchecked")
            List<MenuItem> items = objectMapper.readValue(new File(appPath), List.class);
            for (MenuItem item : items) {
                ShoppingCart.addItem(item);
            }
        } catch (IOException e) {
            Logger.getLogger(e.getMessage());

        }
    }

    // Creating menu items for drinks and food [ARRAY LIST] & Float used for price
    // as well as in main.
    @Override
    public void start(Stage primaryStage) {
        System.out.println(appPath);

        List<MenuItem> menu = new ArrayList<>();

        try {
            menu = MenuItemDao.getMenu();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }

        List<MenuItem> drinkMenu = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getType().equals("drink")) {
                drinkMenu.add(menu.get(i));
            }
        }

        List<MenuItem> foodMenu = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getType().equals("food")) {
                foodMenu.add(menu.get(i));
            }
        }

        Label nameLabel = new Label();
        Label priceLabel = new Label();
        Label totalLabel = new Label("Total: $0.00");
        Button checkoutButton = new Button("Checkout");

        loadCartFromFile();

        ListView<String> cartListView = new ListView<>();
        // Creating buttons for drinks and food menus as Vertical Boxes
        VBox drinkButtons = createMenuButtons(drinkMenu, nameLabel, priceLabel, totalLabel, cartListView);
        VBox foodButtons = createMenuButtons(foodMenu, nameLabel, priceLabel, totalLabel, cartListView);

        // try {
        // for (MenuItem item : ShoppingCart.getItems()) {
        // itemNames.add(item.getName());
        // System.out.println(itemNames);

        // }
        // cartListView.setItems(itemNames);

        // } catch (NullPointerException e) {
        // Logger.getLogger(e.getMessage());
        // }

        VBox cartAndPayment = new VBox(10, new Label("Cart"), cartListView, totalLabel, checkoutButton);
        cartAndPayment.setAlignment(Pos.CENTER);

        HBox root = new HBox(20,
                new VBox(10, new Label("Coffee Menu"), drinkButtons, new Label("Food Menu"), foodButtons),
                cartAndPayment);
        root.setAlignment(Pos.CENTER);
        // Creating the scene and displaying the stage
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Coffee & Restaurant App");
        primaryStage.setScene(scene);
        primaryStage.show();

        checkoutButton.setOnAction(b -> {
            try {
                if (ShoppingCart.getItems().size() > 0) {
                    OrderDao.addOrder();
                }
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
            }

            System.out.println("Total: $" + String.format("%.2f", ShoppingCart.getTotal()));
            System.out.println("Thanks for visiting!");
            ShoppingCart.clearItems();
            totalLabel.setText("Total: $0.00");
            saveCartToFile();
        });
        // Multi-threading Part AS Needed
        Thread orderProcessingThread = new Thread(() -> {
            try {
                // Simulate processing time
                Thread.sleep(5000);
                System.out.println("Order processed successfully!");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        orderProcessingThread.setDaemon(true);
        orderProcessingThread.start();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private VBox createMenuButtons(
            List<MenuItem> menu, Label nameLabel, Label priceLabel, Label totalLabel, ListView cartListView) {
        VBox menuButtons = new VBox(5);
        for (MenuItem item : menu) {
            Button button = new Button(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            button.setOnAction(e -> {
                nameLabel.setText(nameLabel.getText() + "\n" + item.getName());
                priceLabel.setText(priceLabel.getText() + "\n$" + String.format("%.2f", item.getPrice()));
                ShoppingCart.addItem(item);
                ObservableList itemNames = FXCollections.observableArrayList();
                for (MenuItem cartItem : ShoppingCart.getItems()) {
                    itemNames.add(cartItem.getName());
                }
                cartListView.setItems(itemNames);
                totalLabel.setText("Total: $" + String.format("%.2f", ShoppingCart.getTotal()));
            });
            menuButtons.getChildren().add(button);
        }
        return menuButtons; // Once the user clicks Checkout it returns the menu and the selections back to
                            // normal.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
