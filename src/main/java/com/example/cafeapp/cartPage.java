package com.example.cafeapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class cartPage {
    static String cartPath = CafeRestaurantApp.appPath.concat("\\src\\main\\java\\com\\example\\coffeeapp\\cart.txt");
    private static Scene scene;

    // ShoppingCart class manages items added to the cart and calculates the total
    static class ShoppingCart {
        private ObservableList<String> items = FXCollections.observableArrayList();
        private ObservableList<Double> prices = FXCollections.observableArrayList();
        private double total = 0;

        // Method to add an item to the shopping cart
        public void addItem(String item, double price) {
            items.add(item);
            prices.add(price);
            total += price;
        }

        // Method to retrieve the items in the shopping cart
        public ObservableList<String> getItems() {
            return items;
        }

        public ObservableList<Double> getPrices() {
            return prices;
        }

        public double getTotal() {
            return total;
        }

        public void clearItems() {
            items.clear();
            prices.clear();
            total = 0;
        }
    }

    // Read From File
    static ShoppingCart loadCartFromFile() {
        ShoppingCart cart = new ShoppingCart();
        try (BufferedReader reader = new BufferedReader(new FileReader(cartPath))) {
            String line;
            // Read items from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Item line format: itemName,price
                    String itemName = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    cart.addItem(itemName, price);
                } else if (parts.length == 2 && parts[0].equalsIgnoreCase("Total")) {
                    // Total line format: Total,totalAmount
                    double total = Double.parseDouble(parts[1]);
                    cart.total = total;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading cart from file: " + e.getMessage()); // try n catch w Exceptions
        }
        return cart;
    }
    // Creating menu items for drinks and food [ARRAY LIST] & Float used for price
    // as well as in main.

    static void saveCartToFile(ShoppingCart cart) {
        try (PrintWriter writer = new PrintWriter(cartPath)) {
            ObservableList<String> items = cart.getItems();
            ObservableList<Double> prices = cart.getPrices();
            double total = cart.getTotal();

            // Write each item with its price to the file
            for (int i = 0; i < items.size(); i++) {
                writer.println(items.get(i) + "," + prices.get(i));
            }
            // Write total to the file
            writer.println("Total," + total);
        } catch (IOException e) {
            System.err.println("Error saving cart to file: " + e.getMessage()); // Try N catch with Exceptions as Needed
        }
    }

    public Scene getScene(Stage primaryStage) {
        ShoppingCart cart = loadCartFromFile();
        // Creating buttons for drinks and food menus as Vertical Boxes

        ListView<String> cartListView = new ListView<>();
        cartListView.setItems(cart.getItems());

        Label totalLabel = new Label("Total: $0.00");
        Button checkoutButton = new Button("Checkout");

        VBox cartAndPayment = new VBox(10, new Label("Cart"), cartListView, totalLabel, checkoutButton);
        cartAndPayment.setAlignment(Pos.CENTER);

        File bgImgFile = new File(CafeRestaurantApp.appPath.concat("\\src\\media\\bg.jpg"));
        BackgroundImage myBackgroundImage = new BackgroundImage(new Image(bgImgFile.toURI().toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, true));

        HBox root = new HBox(20, cartAndPayment);
        root.setAlignment(Pos.CENTER);
        // Creating the scene and displaying the stage
        scene = new Scene(root, 600, 400);

        root.setBackground(new Background(myBackgroundImage));

        checkoutButton.setOnAction(e -> {
            System.out.println("Total: $" + String.format("%.2f", cart.getTotal()));
            System.out.println("Thanks for visiting!");
            cart.clearItems();
            totalLabel.setText("Total: $0.00");
            saveCartToFile(cart);
        });

        return scene;

    }
}
