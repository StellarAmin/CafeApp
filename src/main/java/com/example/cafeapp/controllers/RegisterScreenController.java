package com.example.cafeapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.cafeapp.App;
import com.example.cafeapp.Customer;
import com.example.cafeapp.dao.CustomerDao;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterScreenController implements Initializable {

    @FXML
    private Label registerLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameField;

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

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    @FXML
    void handleRegister(ActionEvent event) {
        // Simulate registration by storing username and password
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (validateEmail(email) == false) {
            showAlert("Wrong Email", "Please enter email in the correct format: example@example.com");
            return;
        }
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

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cafeapp/view/MainMenu.fxml"));
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

    @FXML
    void showLoginScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cafeapp/view/LoginScreen.fxml"));
            BorderPane pane = loader.load();
            BorderPane.setAlignment(pane, Pos.CENTER);
            App.pane.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
