package edu.ijse.strtgst.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class LoginPageController {
    public AnchorPane loginAnc;

    public void visitSignUpPage(ActionEvent actionEvent) {
        navigateTo("SignUpPage.fxml");
    }

    public void navigateTo(String path){
        try{
            loginAnc.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/" + path));
            loginAnc.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cant Identify the url path");
            e.printStackTrace();
        }
    }
}
