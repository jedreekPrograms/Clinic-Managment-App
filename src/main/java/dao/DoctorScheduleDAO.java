package dao;

import db.DataBaseConnection;
import model.DoctorSchedule;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorScheduleDAO {

    public void add(int doctorId, LocalDateTime date) throws Exception {
        String sql = """
            INSERT INTO doctor_schedule (doctor_id, visit_date, is_available)
            VALUES (?, ?, TRUE)
        """;

        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setTimestamp(2, Timestamp.valueOf(date));
            ps.executeUpdate();
        }
    }

    public List<DoctorSchedule> getByDoctor(int doctorId) throws Exception {
        List<DoctorSchedule> list = new ArrayList<>();

        String sql = """
            SELECT schedule_id, doctor_id, visit_date, is_available
            FROM doctor_schedule
            WHERE doctor_id = ?
            ORDER BY visit_date
        """;

        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DoctorSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("doctor_id"),
                        rs.getTimestamp("visit_date").toLocalDateTime(),
                        rs.getBoolean("is_available")
                ));
            }
        }
        return list;
    }

    public List<DoctorSchedule> getAvailableByDoctor(int doctorId) throws Exception {
        List<DoctorSchedule> list = new ArrayList<>();

        String sql = """
            SELECT schedule_id, doctor_id, visit_date, is_available
            FROM doctor_schedule
            WHERE doctor_id = ? AND is_available = TRUE
            ORDER BY visit_date
        """;

        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DoctorSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("doctor_id"),
                        rs.getTimestamp("visit_date").toLocalDateTime(),
                        true
                ));
            }
        }
        return list;
    }

    public void markAvailable(int scheduleId, Connection c) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE doctor_schedule SET is_available=TRUE WHERE schedule_id=?")) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }

    public void markUnavailable(int scheduleId, Connection c) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE doctor_schedule SET is_available=FALSE WHERE schedule_id=?")) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }
}
