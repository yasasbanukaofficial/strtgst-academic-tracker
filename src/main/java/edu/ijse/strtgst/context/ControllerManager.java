package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AssignmentFormController;
import edu.ijse.strtgst.controller.AssignmentPageController;
public class ControllerManager {
    private static AssignmentPageController assignmentPageController;
    private static AssignmentFormController addNewAssignmentController;

    public static AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public void setAssignmentPageController(AssignmentPageController assignmentPageController) {
        this.assignmentPageController = assignmentPageController;
    }

    public void setAddNewAssignmentController(AssignmentFormController addNewAssignmentController) {
        this.addNewAssignmentController = addNewAssignmentController;
    }

    public static AssignmentFormController getAddNewAssignmentController() {
        if (addNewAssignmentController == null){
            addNewAssignmentController = new AssignmentFormController();
        }
        return addNewAssignmentController;
    }



}
