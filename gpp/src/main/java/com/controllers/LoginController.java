package com.controllers;

import java.io.IOException;

import com.gpprelief.App;
import com.model.HurricaneReliefFacade;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.model.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Enter your username and password.");
            return;
        }

        boolean ok = HurricaneReliefFacade.getInstance().login(username, password);
        if (ok) {
            App.setRoot("home");
        } else {
            errorLabel.setText("Wrong username or password.");
            passwordField.clear();
        }
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}