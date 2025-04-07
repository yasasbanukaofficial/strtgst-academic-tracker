module edu.ijse.strtgst {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens edu.ijse.strtgst.controller to javafx.fxml;
    exports edu.ijse.strtgst;
}