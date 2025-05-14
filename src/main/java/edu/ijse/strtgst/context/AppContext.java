package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.AssignmentFormController;
import edu.ijse.strtgst.controller.AssignmentPageController;
import edu.ijse.strtgst.controller.TaskFormController;
import edu.ijse.strtgst.controller.TaskPageController;

public class AppContext {
    private static AppContext appContext;
    private static AssignmentPageController assignmentPageController;
    private static AssignmentFormController assignmentFormController;
    private static TaskFormController taskFormController;
    private static TaskPageController taskPageController;
    private String username;

    private AppContext() {}

    public static AppContext getInstance() {
        return appContext == null ? appContext = new AppContext() : appContext;
    }

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

    public static AssignmentFormController getAssignmentFormController() {
        if (assignmentFormController == null){
            assignmentFormController = new AssignmentFormController();
        }
        return assignmentFormController;
    }

    public static TaskFormController getTaskFormController() {
        if (taskFormController == null){
            taskFormController = new TaskFormController();
        }
        return taskFormController;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAssignmentPageController(AssignmentPageController assignmentPageController) {
        this.assignmentPageController = assignmentPageController;
    }

    public void setAssignmentFormController(AssignmentFormController assignmentFormController) {
        this.assignmentFormController = assignmentFormController;
    }

    public void setTaskFormController(TaskFormController taskFormController) {
        this.taskFormController = taskFormController;
    }

    public void setTaskPageController(TaskPageController taskPageController) {
        this.taskPageController = taskPageController;
    }
}
