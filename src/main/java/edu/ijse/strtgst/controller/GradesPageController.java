package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GradesPageController implements Initializable {
    public AnchorPane ancAddGrade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("DefaultGradeDisplay.fxml");
    }

    public void addNewGrade(MouseEvent mouseEvent) {
        navigateTo("AddNewGrade.fxml");
    }

    public void navigateTo(String path){
        try {
            ancAddGrade.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefHeightProperty().bind(ancAddGrade.heightProperty());
            load.prefWidthProperty().bind(ancAddGrade.widthProperty());
            ancAddGrade.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unable to identify the path");
            e.printStackTrace();
        }
    }
}
