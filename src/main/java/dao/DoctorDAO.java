package dao;

import db.DataBaseConnection;
import model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public void addDoctor(int userId, int specializationId, String firstName, String lastName) throws Exception {
        String sql = """
            INSERT INTO doctors (user_id, specialization_id, first_name, last_name)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, specializationId);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.executeUpdate();
        }
    }

    // 🔴 NAJWAŻNIEJSZA METODA
    public int getDoctorIdByUserId(int userId) throws Exception {
        String sql = "SELECT doctor_id FROM doctors WHERE user_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("doctor_id");
            }
        }
        throw new Exception("Brak lekarza dla user_id=" + userId);
    }

    public List<Doctor> getAllDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getInt("user_id"),
                        rs.getInt("specialization_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ));
            }
        }
        return doctors;
    }

    public List<Doctor> getBySpecialization(int specId) throws Exception {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE specialization_id=?";

        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, specId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getInt("user_id"),
                        rs.getInt("specialization_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ));
            }
        }
        return list;
    }

}
