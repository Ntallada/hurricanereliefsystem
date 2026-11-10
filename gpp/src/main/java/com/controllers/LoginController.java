package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.gpprelief.App;

public class LoginController {

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}