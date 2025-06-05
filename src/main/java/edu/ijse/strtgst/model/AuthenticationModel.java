package edu.ijse.strtgst.model;

import edu.ijse.strtgst.util.CrudUtil;
import javafx.scene.control.PasswordField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationModel {
    public static boolean updatePassword(String password, String email) throws SQLException {
        return CrudUtil.execute("UPDATE Student SET password = ? WHERE email = ?", password, email);
    }

    public boolean checkIfEmailExisting(String userEmail) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT email FROM Student WHERE email = ? ORDER BY email DESC LIMIT 1", userEmail);
        if (rst.next()){
            return true;
        }
        return false;
    }
}
