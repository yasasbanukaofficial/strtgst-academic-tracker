package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.model.StudentModel;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPageController {
    private AnchorPane loginAnc;
    private TextField txtUsername;
    private PasswordField txtPassword;

    private final Authorization authorization = new Authorization();

    public void visitSignUpPage() {
        Navigation.navigateTo(loginAnc, View.SIGNUP);
    }

    public void visitDashboard(ActionEvent actionEvent) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.equals("") || password.equals("")){
            new Alert(Alert.AlertType.ERROR,"Please enter username and password.").show();
            return;
        }

        try {
            if (authorization.validateCredentials(username, password)){
                Navigation.navigateTo(loginAnc, View.MAIN);
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please Try again!").show();
                showLoginError();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong while trying to log in.").show();
            e.printStackTrace();
        }
    }

    private void showLoginError() {
        String errorStyle = "-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";
        txtUsername.setStyle(errorStyle);
        txtPassword.setStyle(errorStyle);
    }
}

class Authorization {
    public boolean validateCredentials(String username, String password) throws Exception {
        StudentDto studentDto = StudentModel.getStudent(username);
        return studentDto != null && password.equals(studentDto.getPassword());
    }
}