package edu.ijse.strtgst.util;

public enum View {
    DASHBOARD("DashboardPage.fxml"),
    CALENDAR("CalendarPage.fxml"),
    TASK("TaskPage.fxml"),
    ASSIGNMENT("AssignmentPage.fxml"),
    SUBJECT("SubjectPage.fxml"),
    SETTINGS("SettingsPage.fxml"),
    LOGIN("LoginPage.fxml"),
    SIGNUP("SignUpPage.fxml"),
    MAIN("MainPage.fxml"),

    ADD_ASSIGNMENT("AssignmentForm.fxml"),
    ADD_EVENT("AddNewEvent.fxml"),
    ADD_TASK("TaskForm.fxml"),
    ADD_SUBJECT("SubjectForm.fxml"),

    LECTURES_FORM("LecturesForm.fxml"),
    FORGOT_PASSWORD("ForgotPasswordPage.fxml"),
    UPDATE_PASSWORD("UpdatePasswordPage.fxml"),

    DEFAULT_ASSIGNMENT("DefaultAssignmentDisplay.fxml"),
    DEFAULT_TASK("DefaultTaskDisplay.fxml"),
    DEFAULT_SUBJECT("DefaultSubjectDisplay.fxml");


    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return "/view/" + fileName;
    }
}
