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
        try{
            boolean isSaved = studentModel.addStudent(studentDto);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Successfully Saved user").show();
                navigateTo("MainPage.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed when saving user").show();
            }
        } catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed when saving user").show();
        }
    }
}
