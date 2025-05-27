package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AcademicsControllerPage {
    public void showOptionsPage(MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(View.ACADEMIC_CHOICE.getPath()));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
