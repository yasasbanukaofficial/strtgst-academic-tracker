package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AssignmentFormController;
import edu.ijse.strtgst.controller.AssignmentPageController;
public class ControllerManager {
    private static AssignmentPageController assignmentPageController;
    private static AssignmentFormController assignmentFormController;

    public static AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public void setAssignmentPageController(AssignmentPageController assignmentPageController) {
        this.assignmentPageController = assignmentPageController;
    }

    public void setAssignmentFormController(AssignmentFormController assignmentFormController) {
        this.assignmentFormController = assignmentFormController;
    }

    public static AssignmentFormController getAssignmentFormController() {
        if (assignmentFormController == null){
            assignmentFormController = new AssignmentFormController();
        }
        return assignmentFormController;
    }



}
