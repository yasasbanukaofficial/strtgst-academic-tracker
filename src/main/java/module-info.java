module edu.ijse.strtgst {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.ijse.strtgst.controller to javafx.fxml;
    exports edu.ijse.strtgst;
}