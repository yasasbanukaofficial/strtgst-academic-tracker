package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.model.StudentModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPageController {
    public AnchorPane loginAnc;
    public TextField txtUsername;
    public PasswordField txtPassword;

    private final LoginService loginService = new LoginService();

    public void visitSignUpPage() {
        Navigation.navigateTo(loginAnc, View.SIGNUP);
    }

    public void visitDashboard(ActionEvent actionEvent) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.equals("") || password.equals("")){
            AlertUtil.setErrorAlert("Please enter username and password.");
            return;
        }

        try {
            if (loginService.validateCredentials(username, password)){
                Navigation.navigateTo(loginAnc, View.MAIN);
            } else {
                AlertUtil.setErrorAlert("Invalid username or password. Please Try again!");
                showLoginError();
            }
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Something went wrong while trying to log in.");
            e.printStackTrace();
        }
    }

    private void showLoginError() {
        String errorStyle = "-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";
        txtUsername.setStyle(errorStyle);
        txtPassword.setStyle(errorStyle);
    }
}

class LoginService {
    public boolean validateCredentials(String username, String password) throws Exception {
        StudentDto studentDto = StudentModel.getStudent(username);
        return studentDto != null && password.equals(studentDto.getPassword());
    }
}