module edu.ijse.strtgst {
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;


    opens edu.ijse.strtgst.controller to javafx.fxml;
    exports edu.ijse.strtgst;
}