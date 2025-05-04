package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.View;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentPageController implements Initializable {
    public AnchorPane ancTaskContainer;


    public void navigateTo(View view){
        try {
            ancTaskContainer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(view.getPath()));
            load.prefWidthProperty().bind(ancTaskContainer.widthProperty());
            load.prefHeightProperty().bind(ancTaskContainer.heightProperty());
            ancTaskContainer.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error when accessing the path to  :   " + view.name());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo(View.DEFAULT_ASSIGNMENT);
    }

    public void addNewAssignment(MouseEvent mouseEvent) {
        navigateTo(View.ADD_ASSIGNMENT);
    }
}
