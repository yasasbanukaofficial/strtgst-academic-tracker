module edu.ijse.strtgst {
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;
    requires static lombok;
    requires java.desktop;


    opens edu.ijse.strtgst.controller to javafx.fxml;
    opens edu.ijse.strtgst.dto.tm to javafx.base;
    exports edu.ijse.strtgst;
}