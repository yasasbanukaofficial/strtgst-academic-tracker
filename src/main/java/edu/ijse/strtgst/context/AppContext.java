package edu.ijse.strtgst.context;

import edu.ijse.strtgst.controller.*;

public class AppContext {
    private static AppContext appContext;
    private static AssignmentPageController assignmentPageController;
    private static AssignmentFormController assignmentFormController;
    private static TaskFormController taskFormController;
    private static TaskPageController taskPageController;
    private static MainPageController mainPageController;
    private String username;

    private AppContext() {}

    public static AppContext getInstance() {
        return appContext == null ? appContext = new AppContext() : appContext;
    }

    public AssignmentPageController getAssignmentPageController() {
        if (assignmentPageController == null){
            assignmentPageController = new AssignmentPageController();
        }
        return assignmentPageController;
    }

    public TaskPageController getTaskPageController() {
        if (taskPageController == null){
            taskPageController = new TaskPageController();
        }
        return taskPageController;
    }

    public AssignmentFormController getAssignmentFormController() {
        if (assignmentFormController == null){
            assignmentFormController = new AssignmentFormController();
        }
        return assignmentFormController;
    }

    public TaskFormController getTaskFormController() {
        if (taskFormController == null){
            taskFormController = new TaskFormController();
        }
        return taskFormController;
    }

    public MainPageController getMainPageController() {
        if (mainPageController == null){
            mainPageController = new MainPageController();
        }
        return mainPageController;
    }

    public String getUsername() {
        return username;
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

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
