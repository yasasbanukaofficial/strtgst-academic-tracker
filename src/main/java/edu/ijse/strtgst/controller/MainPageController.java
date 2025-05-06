package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    public AnchorPane ancTabDisplay;
    public AnchorPane ancMainPage;

    private void stopAndNavigate(AnchorPane anchorPane, View view) {
        UpdateThread.stopThread();
        Navigation.navigateTo(anchorPane, view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stopAndNavigate(ancTabDisplay, View.DASHBOARD);
    }

    public void visitAssignmentPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.ASSIGNMENT);
    }

    public void visitDashboardPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.DASHBOARD);
    }

    public void visitTaskPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.TASK);
    }

    public void visitSettingsPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.SETTINGS);
    }

    public void visitGradesPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.GRADES);
    }

    public void visitCalendarPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancTabDisplay, View.CALENDAR);
    }

    public void visitLoginPage(MouseEvent mouseEvent) {
        stopAndNavigate(ancMainPage, View.LOGIN);
    }
}
