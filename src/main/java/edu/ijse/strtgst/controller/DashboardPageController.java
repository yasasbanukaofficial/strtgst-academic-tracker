package edu.ijse.strtgst.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashboardPageController {
    public AnchorPane ancDashboard;

    public void visitTasksPage(MouseEvent mouseEvent) {
        navigateTo("TaskPage.fxml");
    }

    public void visitEventsPage(MouseEvent mouseEvent) {
        navigateTo("EventsPage.fxml");
    }

    public void visitAssignmentsPage(MouseEvent mouseEvent) {
        navigateTo("AssignmentPage.fxml");
    }

    public void visitGradesPage(MouseEvent mouseEvent) {
        navigateTo("GradesPage.fxml");
    }

    public void navigateTo(String path){
        try{
            ancDashboard.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + path));
            load.prefHeightProperty().bind(ancDashboard.heightProperty());
            load.prefWidthProperty().bind(ancDashboard.widthProperty());
            ancDashboard.getChildren().add(load);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Error defining the path");
            e.printStackTrace();
        }
    }
}
