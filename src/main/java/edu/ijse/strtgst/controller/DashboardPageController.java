package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashboardPageController {
    public AnchorPane ancDashboard;

    public void visitTasksPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.TASK);
    }

    public void visitEventsPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.CALENDAR);
    }

    public void visitAssignmentsPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.ASSIGNMENT);
    }

    public void visitGradesPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.GRADES);
    }

    public void visitLecturesPage(MouseEvent mouseEvent) {
        Navigation.navigateTo(ancDashboard, View.CALENDAR);
    }
}
