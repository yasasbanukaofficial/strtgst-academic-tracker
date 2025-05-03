package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.model.StudentModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SignUpPageController{
    public AnchorPane signUpAnc;
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtPassword;
    public Button btnSignUp;

    private final StudentModel studentModel = new StudentModel();

    private final String usernamePattern = "^[a-zA-Z0-9_-]{3,}$";
    private final String emailPattern = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    private final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.\\-_*])[a-zA-Z0-9@#$%^&+=.\\-_]{3,}$";


    public void visitLoginPage(ActionEvent actionEvent) {
        navigateTo("LoginPage.fxml");
    }

    public void navigateTo(String path){
        try{
            signUpAnc.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefWidthProperty().bind(signUpAnc.widthProperty());
            load.prefHeightProperty().bind(signUpAnc.heightProperty());
            signUpAnc.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cant Identify the url path");
        }
    }

    public void signUpStudent(ActionEvent event) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        StudentDto studentDto = new StudentDto(username, email, password);

        if (validateInputs(username, email, password)) {
            try {
                boolean isSaved = studentModel.addStudent(studentDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Saved user").show();
                    navigateTo("MainPage.fxml");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed when saving user").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed when saving user").show();
            }
        }
    }

    private boolean validateInputs (String username, String email, String password) {
        StringBuilder errorMessage = new StringBuilder();
        boolean isValid = true;
        boolean isValidUserName = username.matches(usernamePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        if (!isValidUserName) {
            txtUsername.setStyle("-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
            errorMessage.append("• Username must be at least 3 characters long and contain only letters, digits, underscores or hyphens.\n");
            isValid = false;
        }
        if (!isValidEmail) {
            txtEmail.setStyle("-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
            errorMessage.append("• Email must be a valid one (e.g., name@example.com).\n");
            isValid = false;
        }
        if (!isValidPassword) {
            txtPassword.setStyle("-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
            errorMessage.append("• Password must be more than 3 characters and should include one uppercase, lowercase, number, and special character.\n");
            isValid = false;
        }

        if (isValidUserName) {
            txtUsername.setStyle("-fx-border-color: #000000; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
        }
        if (isValidEmail){
            txtEmail.setStyle("-fx-border-color: #000000; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
        }
        if (isValidPassword){
            txtPassword.setStyle("-fx-border-color: #000000; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px");
        }

        if (!isValid){
            new Alert(Alert.AlertType.ERROR, "Please fix the following: \n\n" + errorMessage).show();
        }
        return isValid;
    }
}
