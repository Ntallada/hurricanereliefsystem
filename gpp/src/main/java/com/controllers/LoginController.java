package com.controllers;

import java.io.IOException;

import com.gpprelief.App;
import com.model.HurricaneReliefFacade;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField txt_username;
    @FXML private TextField txt_password;

    @FXML
    private void login() throws IOException {
        String username = txt_username.getText().trim();
        String password = txt_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Enter your username and password.");
            return;
        }

        boolean ok = HurricaneReliefFacade.getInstance().login(username, password);
        if (ok) {
            App.setRoot("home");
        } else {
            showError("Wrong username or password.");
            txt_password.clear();
        }
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("home");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}