package com.example.cafeapp;

import java.io.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//Omar Alaa Eldeen 221001732 & Amin Mossadag 222000055
//Searched for each import usage on Google,and learnt how are they meant to be used.
public class CoffeeRestaurantApp extends Application {
    static String appPath = new File("").getAbsolutePath().concat("\\cafeapp");
    private void saveCartToFile(ShoppingCart cart) {
        try (PrintWriter writer = new PrintWriter("cart.txt")) {
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
    // Read From File [had an issue with it and I need some help to fix it]
    private ShoppingCart loadCartFromFile() {
        ShoppingCart cart = new ShoppingCart();
        try (BufferedReader reader = new BufferedReader(new FileReader("cart.txt"))) {
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
                    cart.setTotal(total);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading cart from file: " + e.getMessage()); // try n catch w Exceptions
        }
        return cart;
    }
    // Creating menu items for coffee and food [ARRAY LIST] & Float used for price as well as in main.
    @Override
    public void start(Stage primaryStage) {
        System.out.println(appPath);

        MenuItem[] coffeeMenu = {
                new MenuItem("Espresso", 2.50),
                new MenuItem("Latte", 3.00),
                new MenuItem("Cappuccino", 3.25),
                new MenuItem("Americano", 3.00),
                new MenuItem("Macchiato", 3.50)
        };

        MenuItem[] foodMenu = {
                new MenuItem("Sandwich", 5.00),
                new MenuItem("Salad", 4.50),
                new MenuItem("Burger", 6.50),
                new MenuItem("Pizza", 8.00),
                new MenuItem("Pasta", 7.00)
        };

        Label nameLabel = new Label();
        Label priceLabel = new Label();
        Label totalLabel = new Label("Total: $0.00");
        Button checkoutButton = new Button("Checkout");

        ShoppingCart cart = loadCartFromFile();
        // Creating buttons for coffee and food menus as Vertical Boxes
        VBox coffeeButtons = createMenuButtons(coffeeMenu, nameLabel, priceLabel, totalLabel, cart);
        VBox foodButtons = createMenuButtons(foodMenu, nameLabel, priceLabel, totalLabel, cart);

        ListView<String> cartListView = new ListView<>();
        cartListView.setItems(cart.getItems());

        VBox cartAndPayment = new VBox(10, new Label("Cart"), cartListView, totalLabel, checkoutButton);
        cartAndPayment.setAlignment(Pos.CENTER);

        HBox root = new HBox(20, new VBox(10, new Label("Coffee Menu"), coffeeButtons, new Label("Food Menu"), foodButtons), cartAndPayment);
        root.setAlignment(Pos.CENTER);
        // Creating the scene and displaying the stage
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Coffee & Restaurant App");
        primaryStage.setScene(scene);
        primaryStage.show();

        checkoutButton.setOnAction(e -> {
            System.out.println("Total: $" + String.format("%.2f", cart.getTotal()));
            System.out.println("Thanks for visiting!");
            cart.clearItems();
            totalLabel.setText("Total: $0.00");
            saveCartToFile(cart);
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

    private VBox createMenuButtons(MenuItem[] menu, Label nameLabel, Label priceLabel, Label totalLabel, ShoppingCart cart) {
        VBox menuButtons = new VBox(5);
        for (MenuItem item : menu) {
            Button button = new Button(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            button.setOnAction(e -> {
                nameLabel.setText(nameLabel.getText() + "\n" + item.getName());
                priceLabel.setText(priceLabel.getText() + "\n$" + String.format("%.2f", item.getPrice()));
                cart.addItem(item.getName(), item.getPrice());
                totalLabel.setText("Total: $" + String.format("%.2f", cart.getTotal()));
            });
            menuButtons.getChildren().add(button);
        }
        return menuButtons; // Once the user clicks Checkout it returns the menu and the selections back to normal.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
