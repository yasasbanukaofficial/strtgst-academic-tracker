package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AssignmentFormController;
import edu.ijse.strtgst.controller.AssignmentPageController;
import edu.ijse.strtgst.controller.TaskFormController;
import edu.ijse.strtgst.controller.TaskPageController;

public class ControllerManager {
    private static AssignmentPageController assignmentPageController;
    private static AssignmentFormController assignmentFormController;
    private static TaskFormController taskFormController;
    private static TaskPageController taskPageController;

    public static AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public static TaskPageController getTaskPageController() {
        if (taskPageController == null){
            taskPageController = new TaskPageController();
        }
        return taskPageController;
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


    public void setTaskFormController(TaskFormController taskFormController) {
        this.taskFormController = taskFormController;
    }

    public static TaskFormController getTaskFormController() {
        if (taskFormController == null){
            taskFormController = new TaskFormController();
        }
        return taskFormController;
    }

    public void setTaskPageController(TaskPageController taskPageController) {
        this.taskPageController = taskPageController;
    }
}
