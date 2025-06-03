module edu.ijse.strtgst {
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;
    requires static lombok;
    requires java.desktop;
    requires google.genai;
    requires org.checkerframework.checker.qual;


    opens edu.ijse.strtgst.controller to javafx.fxml;
    opens edu.ijse.strtgst.dto.tm to javafx.base;
    exports edu.ijse.strtgst;
}