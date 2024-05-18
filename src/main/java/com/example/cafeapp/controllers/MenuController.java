package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.cafeapp.App;
import com.example.cafeapp.MenuItem;
import com.example.cafeapp.dao.MenuItemDao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private Label loginLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private VBox drinkButtons;

    @FXML
    private VBox foodButtons;

    private Stage stage;

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

    private void createMenuButtons(List<MenuItem> menu, VBox container) {
        for (MenuItem item : menu) {
            Label itemLabel = new Label(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            itemLabel.setId("menuLabel");
            container.getChildren().add(itemLabel);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initialize();
    }

}
