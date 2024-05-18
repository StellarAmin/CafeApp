package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.cafeapp.App;
import com.example.cafeapp.Customer;
import com.example.cafeapp.dao.CustomerDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreenController implements Initializable {

    @FXML
    private Label loginLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private Stage stage;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        // Simulate login process (check credentials)
        if (!email.isEmpty() && !password.isEmpty()) {
            Customer.setEmail(email);

            if (userExists()) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/example/cafeapp/view/MainMenu.fxml"));
                    VBox box = loader.load();
                    App.pane.setCenter(box);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                showAlert("Invalid credentials", "User doesn't exist");
            }
        } else {
            showAlert("Invalid credentials", "Please enter valid username and password.");
        }
    }

    @FXML
    void showRegisterScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cafeapp/view/RegisterScreen.fxml"));
            VBox box = loader.load();
            App.pane.setCenter(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean userExists() {
        try {
            return CustomerDao.signInCustomer();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return false;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
