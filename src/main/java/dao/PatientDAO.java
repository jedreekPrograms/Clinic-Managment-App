package dao;

import db.DataBaseConnection;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // CREATE
    public void addPatient(int userId, String firstName, String lastName, String pesel) throws Exception {
        String sql = "INSERT INTO patients (user_id, first_name, last_name, pesel) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, pesel);
            ps.executeUpdate();
        }
    }

    // READ ALL
    public List<Patient> getAllPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT patient_id, user_id, first_name, last_name, pesel FROM patients";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("patient_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("pesel")
                ));
            }
        }
        return patients;
    }

    // UPDATE
    public void updatePatient(int patientId, String firstName, String lastName, String pesel) throws Exception {
        String sql = "UPDATE patients SET first_name = ?, last_name = ?, pesel = ? WHERE patient_id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, pesel);
            ps.setInt(4, patientId);
            ps.executeUpdate();
        }
    }

    // DELETE
    public void deletePatient(int patientId) throws Exception {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }
}
