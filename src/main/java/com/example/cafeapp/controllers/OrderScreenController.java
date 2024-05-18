package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.cafeapp.App;
import com.example.cafeapp.MenuItem;
import com.example.cafeapp.ShoppingCart;
import com.example.cafeapp.dao.MenuItemDao;
import com.example.cafeapp.dao.OrderDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class OrderScreenController implements Initializable {

    @FXML
    private VBox drinkButtons;

    @FXML
    private VBox foodButtons;

    @FXML
    private Label totalLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private ListView<String> cartListView;

    private void createMenuButtons(List<MenuItem> menu, VBox container) {
        for (MenuItem item : menu) {
            Button button = new Button(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            button.setOnAction(e -> {
                ShoppingCart.addItem(item);
                updateCart();
            });
            button.setId("menuButtonLabel");
            container.getChildren().add(button);
        }
    }

    private void updateCart() {
        ObservableList<String> itemNames = FXCollections.observableArrayList();
        for (MenuItem cartItem : ShoppingCart.getItems()) {
            itemNames.add(cartItem.getName());
        }
        cartListView.setItems(itemNames);
        totalLabel.setText("Total: $" + String.format("%.2f", ShoppingCart.getTotal()));
        saveCartToFile();
    }

    private void handleCheckout() {
        try {
            if (!ShoppingCart.getItems().isEmpty()) {
                OrderDao.addOrder();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total: $" + String.format("%.2f", ShoppingCart.getTotal()));
        System.out.println("Thanks for visiting!");
        ShoppingCart.clearItems();
        updateCart();
    }

    private void loadCartFromFile() {
        // Implement the loading logic here
    }

    private void saveCartToFile() {
        // Implement the saving logic here
    }

    @FXML
    private void showMainMenuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/MainMenu.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            List<MenuItem> menu = new ArrayList<>();
            menu = MenuItemDao.getMenu();
            List<MenuItem> drinkMenu = new ArrayList<>();
            List<MenuItem> foodMenu = new ArrayList<>();

            for (MenuItem item : menu) {
                if ("drink".equals(item.getType())) {
                    drinkMenu.add(item);
                } else if ("food".equals(item.getType())) {
                    foodMenu.add(item);
                }
            }

            createMenuButtons(drinkMenu, drinkButtons);
            createMenuButtons(foodMenu, foodButtons);

            loadCartFromFile();

            checkoutButton.setOnAction(event -> handleCheckout());
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initialize();
    }
}
