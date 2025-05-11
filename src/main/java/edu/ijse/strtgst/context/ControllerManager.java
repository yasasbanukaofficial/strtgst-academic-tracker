package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AssignmentPageController;
public class ControllerManager {
    private static AssignmentPageController assignmentPageController;

    public static AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public void setAssignmentPageController(AssignmentPageController assignmentPageController) {
        this.assignmentPageController = assignmentPageController;
    }
}
