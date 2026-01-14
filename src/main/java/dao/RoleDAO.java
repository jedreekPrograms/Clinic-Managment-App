package dao;

import db.DataBaseConnection;
import java.sql.*;

public class RoleDAO {

    public int getRoleIdByName(String name) throws Exception {
        String sql = "SELECT role_id FROM roles WHERE name = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("role_id");
        }
        throw new Exception("Rola nie istnieje");
    }
}
