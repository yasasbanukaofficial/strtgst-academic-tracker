package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class IntroPageController implements Initializable {
    public MediaView mediaViewer;
    public AnchorPane ancIntro;
    public Button btnStartJourney;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBackgroundVideo();
    }

    private void playBackgroundVideo() {
        try {
            String videoPath = getClass().getResource("/videos/dirt.mp4").toExternalForm();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            mediaPlayer.play();
            mediaViewer.fitWidthProperty().bind(ancIntro.widthProperty());
            mediaViewer.fitHeightProperty().bind(ancIntro.heightProperty());
            mediaViewer.setPreserveRatio(false);

            mediaViewer.setMediaPlayer(mediaPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visitLoginPage(ActionEvent actionEvent) {
        Navigation.navigateTo(ancIntro, View.LOGIN);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ancIntro.getScene().getWindow();
        stage.close();
    }
}
