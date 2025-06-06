package edu.ijse.strtgst.controller;


import edu.ijse.strtgst.context.AppContext;
import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.model.StudentModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.IdLoader;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IntroPageController implements Initializable {
    public MediaView mediaViewer;
    public AnchorPane ancIntro;
    public Label lblMain;
    public Label lblComment;
    public Button btnExit;

    private final AppContext appContext = AppContext.getInstance();
    private final StudentModel studentModel = new StudentModel();
    private final String usernamePattern = "^[a-zA-Z0-9_-]{3,}$";
    private final String emailPattern = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    private final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.\\-_*])[a-zA-Z0-9@#$%^&+=.\\-_]{6,}$";
    private final String errorStyle = "-fx-border-color: #ce0101; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";
    private final String normalStyle = "-fx-border-color: #000000; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-background-radius: 10px";

    public Button btnLogin;
    public Button btnSignUp;
    public AnchorPane ancForms;

    public void visitDashboard() {
        Navigation.navigateTo(ancIntro, View.MAIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBackgroundVideo();
        setVisibility(false);
        appContext.setIntroPageController(this);
    }

    private void setVisibility(boolean visibility) {
        ancForms.setVisible(visibility);
    }

    private void playBackgroundVideo() {
        try {
            String videoPath = getClass().getResource("/videos/dirt.mp4").toExternalForm();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            mediaPlayer.play();
            mediaViewer.toBack();
            mediaViewer.fitWidthProperty().bind(ancIntro.widthProperty());
            mediaViewer.fitHeightProperty().bind(ancIntro.heightProperty());
            mediaViewer.setPreserveRatio(false);
            mediaPlayer.setOnReady(() -> {
                mediaViewer.setOpacity(1);
            });

            mediaViewer.setMediaPlayer(mediaPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginFields(ActionEvent actionEvent) {
        lblMain.setText("LOGIN");
        lblComment.setText("Enter your username and password to continue.");
        setVisibility(true);
        Navigation.navigateTo(ancForms, View.LOGIN_FORM);
    }

    public void showSignUpFields(ActionEvent actionEvent) {
        lblMain.setText("SIGNUP");
        lblComment.setText("Start your education journey with us by creating an account.");
        setVisibility(true);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ancIntro.getScene().getWindow();
        stage.close();
    }

    public void forgotPassword(MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(View.FORGOT_PASSWORD.getPath()));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadNextId() {
        try {
            return IdLoader.getNextID("Student", "stud_id");
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading a Student ID");
            e.printStackTrace();
        }
        return "S001";
    }

    private boolean isEmailTaken(String email){
        try{
            return studentModel.fetchExistingEmail(email);
        } catch (SQLException e){
            AlertUtil.setErrorAlert("Error when checking if email exists.");
            e.printStackTrace();
        }
        return true;
    }

    public void visitSignUp(ActionEvent actionEvent) {
    }
}
