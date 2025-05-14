package edu.ijse.strtgst.util;

public enum View {
    DASHBOARD("DashboardPage.fxml"),
    CALENDAR("CalendarPage.fxml"),
    TASK("TaskPage.fxml"),
    ASSIGNMENT("AssignmentPage.fxml"),
    GRADES("GradesPage.fxml"),
    SETTINGS("SettingsPage.fxml"),
    LOGIN("LoginPage.fxml"),
    SIGNUP("SignUpPage.fxml"),
    MAIN("MainPage.fxml"),

    ADD_ASSIGNMENT("AssignmentForm.fxml"),
    ADD_EVENT("AddNewEvent.fxml"),
    ADD_GRADE("AddNewGrade.fxml"),
    ADD_TASK("TaskForm.fxml"),

    DEFAULT_ASSIGNMENT("DefaultAssignmentDisplay.fxml"),
    DEFAULT_GRADE("DefaultGradeDisplay.fxml"),
    DEFAULT_TASK("DefaultTaskDisplay.fxml");


    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return "/view/" + fileName;
    }
}
