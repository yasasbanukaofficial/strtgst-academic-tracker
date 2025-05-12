package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AddNewAssignmentController;
import edu.ijse.strtgst.controller.AssignmentPageController;
public class ControllerManager {
    private static AssignmentPageController assignmentPageController;
    private static AddNewAssignmentController addNewAssignmentController;

    public static AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public void setAssignmentPageController(AssignmentPageController assignmentPageController) {
        this.assignmentPageController = assignmentPageController;
    }

    public void setAddNewAssignmentController(AddNewAssignmentController addNewAssignmentController) {
        this.addNewAssignmentController = addNewAssignmentController;
    }

    public static AddNewAssignmentController getAddNewAssignmentController() {
        if (addNewAssignmentController == null){
            addNewAssignmentController = new AddNewAssignmentController();
        }
        return addNewAssignmentController;
    }



}
