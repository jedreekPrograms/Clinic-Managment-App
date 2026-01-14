package dao;

import db.DataBaseConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public void createUser(String email, String passwordHash, int roleId)
            throws Exception {

        String sql = """
            INSERT INTO users (email, password_hash, role_id)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ps.setInt(3, roleId);
            ps.executeUpdate();
        }
    }

    public User findByEmail(String email) throws Exception {

        String sql = """
            SELECT user_id, email, password_hash, role_id
            FROM users WHERE email = ?
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("role_id")
                );
            }
        }
        return null;
    }
}
