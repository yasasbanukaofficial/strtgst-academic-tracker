package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.model.StudentModel;
import edu.ijse.strtgst.util.IdLoader;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class SignUpPageController{
    public AnchorPane signUpAnc;
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtPassword;
    public Button btnSignUp;

    private final StudentModel studentModel = new StudentModel();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private final String usernamePattern = "^[a-zA-Z0-9_-]{3,}$";
    private final String emailPattern = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    private final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.\\-_*])[a-zA-Z0-9@#$%^&+=.\\-_]{6,}$";
    private final String errorStyle = "-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";
    private final String normalStyle = "-fx-border-color: #000000; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";


    public void visitLoginPage(ActionEvent actionEvent) {
        Navigation.navigateTo(signUpAnc, View.LOGIN);
    }

    public void signUpStudent(ActionEvent event) {
        String studentId = loadNextId();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        StudentDto studentDto = new StudentDto(studentId, username, email, password);

        if (validateInputs(username, email, password)) {
            try {
                if (studentModel.addStudent(studentDto)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully Saved user");
                    alert.show();
                    Navigation.navigateTo(signUpAnc, View.MAIN);
                } else {
                    alert.setContentText("Failed when saving user");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                alert.setContentText("Failed when saving user");
                alert.show();
            }
        }
    }

    public String loadNextId() {
        try {
            return IdLoader.getNextID("Student", "stud_id");
        } catch (SQLException e) {
            alert.setContentText("Error when loading a Student ID");
            alert.show();
            e.printStackTrace();
        }
        return "S001";
    }

    private boolean validateInputs (String username, String email, String password) {
        StringBuilder errorMsg = new StringBuilder();
        boolean isValid = true;

        if (isUsernameTaken(username)) {
            errorMsg.append("• This username has already been taken \n");
            txtUsername.setStyle(errorStyle);
            isValid = false;
        } else if (!username.matches(usernamePattern)) {
            txtUsername.setStyle(errorStyle);
            errorMsg.append("• Username must have 3+ characters long and contain only letters, digits, underscores or hyphens.\n");
            isValid = false;
        } else txtUsername.setStyle(normalStyle);

        if (isEmailTaken(email)) {
            errorMsg.append("• This email has already been taken \n");
            txtEmail.setStyle(errorStyle);
            isValid = false;
        } else if (!email.matches(emailPattern)) {
            txtEmail.setStyle(errorStyle);
            errorMsg.append("• Email must be a valid one (e.g., name@example.com).\n");
            isValid = false;
        } else txtEmail.setStyle(normalStyle);

        if (!password.matches(passwordPattern)) {
            txtPassword.setStyle(errorStyle);
            errorMsg.append("• Password must be 6+ characters and should include following \n one (uppercase, lowercase, number, and special character).\n");
            isValid = false;
        } else txtPassword.setStyle(normalStyle);

        if (!isValid){
            alert.setContentText("Error when creating an account: \n\n" + errorMsg);
            alert.show();
        }
        return isValid;
    }

    private boolean isUsernameTaken(String username){
        try{
            return studentModel.fetchExistingUsername(username);
        } catch (SQLException e){
            alert.setContentText("Error when checking if username exists.");
            alert.show();
            e.printStackTrace();
        }
        return true;
    }

    private boolean isEmailTaken(String email){
        try{
            return studentModel.fetchExistingEmail(email);
        } catch (SQLException e){
            alert.setContentText("Error when checking if email exists.");
            alert.show();
            e.printStackTrace();
        }
        return true;
    }
}
