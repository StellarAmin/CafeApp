package com.example.cafeapp.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.cafeapp.App;
import com.example.cafeapp.Customer;
import com.example.cafeapp.dao.CustomerDao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class LoginScreenController implements Initializable {

    @FXML
    private Label loginLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailFieldFXML;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordFieldFXML;

    @FXML
    private Button loginButtonFXML;

    @FXML
    private Button registerButtonFXML;

    private Label titleLabel = new Label("Login");
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Button registerButton = new Button("Register");
    private VBox loginLayout = new VBox(10);
    private Stage stage;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        // Simulate login process (check credentials)
        if (!email.isEmpty() && !password.isEmpty()) {
            Customer.setEmail(email);

            if (userExists()) {
                // showMainMenuScreen(Customer.getName());
            } else {
                showAlert("Invalid credentials", "User doesn't exist");
            }
        } else {
            showAlert("Invalid credentials", "Please enter valid username and password.");
        }
    }

    @FXML
    void showRegisterScreen(ActionEvent event) {
        Label titleLabel = new Label("Register");
        TextField usernameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");
        VBox registerLayout = new VBox(10);
        registerLayout.getChildren().addAll(titleLabel, new Label("Username:"), usernameField, new Label("Email:"),
                emailField, new Label("Password:"), passwordField, registerButton, backButton);
        registerLayout.setAlignment(Pos.CENTER);
        Scene registerScene = new Scene(registerLayout, 300, 200);
        App.primaryStage.setScene(registerScene);
        App.primaryStage.setTitle("Coffee & Restaurant App - Register");
        App.primaryStage.show();

        registerButton.setOnAction(b -> {
            // Simulate registration by storing username and password
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Customer.setName(username);
            Customer.setEmail(email);
            Customer.setPassword(password);

            try {
                CustomerDao.signUpCustomer(); // Save customer to database (for
                // demonstration)
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
            }

            // Print registration details (for demonstration)
            System.out.println("Registered username: " + username);
            System.out.println("Registered email: " + email);
            System.out.println("Registered password: " + password);

            // Automatically login after registration
            // showMainMenuScreen(username); // Pass username to main menu
        });

        // backButton.setOnAction(b -> showLoginScreen());
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
