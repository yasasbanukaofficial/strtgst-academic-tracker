module edu.ijse.strtgst {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ijse.strtgst to javafx.fxml;
    exports edu.ijse.strtgst;
}