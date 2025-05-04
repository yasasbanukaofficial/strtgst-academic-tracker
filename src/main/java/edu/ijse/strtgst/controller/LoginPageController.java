package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class LoginPageController {
    public AnchorPane loginAnc;

    public void visitSignUpPage() {
        Navigation.navigateTo(loginAnc, View.SIGNUP);
    }

    public void visitDashboard(ActionEvent actionEvent) {
        Navigation.navigateTo(loginAnc, View.MAIN);
    }
}