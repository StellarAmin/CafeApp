package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.cafeapp.App;
import com.example.cafeapp.Customer;
import com.example.cafeapp.dao.CustomerDao;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    @FXML
    private Label mainMenuLabel;

    @FXML
    private Button viewMenuButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Button contactButton;

    @FXML
    private Button logoutButton;

    private Stage stage;

    @FXML
    void showMenuScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/Menu.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePlaceOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/OrderScreen.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showAboutScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/AboutScreen.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showContactScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/ContactScreen.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        showAlert("Logout", "Have a nice day! Come back again.");
        Platform.exit();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
