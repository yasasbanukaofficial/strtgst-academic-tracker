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
    public AnchorPane loginAnc;
    public TextField txtUsername;
    public PasswordField txtPassword;

    public void visitSignUpPage() {
        Navigation.navigateTo(loginAnc, View.SIGNUP);
    }

    public void visitDashboard(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (validateInputs(username, password)) Navigation.navigateTo(loginAnc, View.MAIN);
    }

    private boolean validateInputs (String username, String password) {
        String errorStyle = "-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";
        boolean isPasswordMatches = false;
        boolean isUsernameExisting = false;

        try {
            StudentDto studentDto = StudentModel.getStudent(username);
            if (studentDto != null){
                isUsernameExisting = true;
                isPasswordMatches = password.equals(studentDto.getPassword());
            }
        } catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong while trying to log in.").show();
            return false;
        }

        if (!isUsernameExisting || !isPasswordMatches) {
            txtUsername.setStyle(errorStyle);
            txtPassword.setStyle(errorStyle);
            new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please Try again!").show();
        }
        return isUsernameExisting && isPasswordMatches;
    }
}