package edu.ijse.strtgst.controller;

import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.model.TaskModel;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.Navigation;
import edu.ijse.strtgst.util.View;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {
    public AnchorPane ancDashboard;
    public Label labelTotalTasks;
    public Label labelTotalAssignments;

    private TaskModel taskModel = new TaskModel();
    private AssignmentModel assignmentModel = new AssignmentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countTotal();
    }

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

    private void countTotal() {
        try {
            labelTotalTasks.setText(taskModel.getPendingOrOverdueTaskCount());
            labelTotalAssignments.setText(assignmentModel.getPendingOrOverdueAssignmentCount());
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when fetching total");
            e.printStackTrace();
        }
    }
}
