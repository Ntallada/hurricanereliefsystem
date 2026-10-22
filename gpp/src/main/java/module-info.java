module com.gpprelief {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.net.http;
    //requires com.fasterxml.jackson.databind;
    //requires javafx.web;


    opens com.gpprelief to javafx.fxml;
    exports com.gpprelief;

    opens com.model to javafx.fxml;
    exports com.model;
}

