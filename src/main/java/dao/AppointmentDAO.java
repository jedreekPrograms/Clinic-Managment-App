package dao;

import db.DataBaseConnection;
import model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void book(int scheduleId, int doctorId, int patientId) throws Exception {

        try (Connection c = DataBaseConnection.getConnection()) {
            c.setAutoCommit(false);

            // 1️⃣ sprawdź czy istnieje CANCELLED
            Integer existingAppointmentId = null;

            try (PreparedStatement ps = c.prepareStatement("""
                SELECT appointment_id
                FROM appointments
                WHERE schedule_id = ?
                  AND patient_id = ?
                  AND status = 'CANCELLED'
            """)) {
                ps.setInt(1, scheduleId);
                ps.setInt(2, patientId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    existingAppointmentId = rs.getInt("appointment_id");
                }
            }

            if (existingAppointmentId != null) {
                // 2️⃣ przywróć wizytę
                try (PreparedStatement ps = c.prepareStatement("""
                    UPDATE appointments
                    SET status = 'PLANNED'
                    WHERE appointment_id = ?
                """)) {
                    ps.setInt(1, existingAppointmentId);
                    ps.executeUpdate();
                }
            } else {
                // 3️⃣ normalna rejestracja
                try (PreparedStatement ps = c.prepareStatement("""
                    INSERT INTO appointments (schedule_id, doctor_id, patient_id, status)
                    VALUES (?, ?, ?, 'PLANNED')
                """)) {
                    ps.setInt(1, scheduleId);
                    ps.setInt(2, doctorId);
                    ps.setInt(3, patientId);
                    ps.executeUpdate();
                }
            }

            // 4️⃣ oznacz termin jako zajęty
            try (PreparedStatement ps = c.prepareStatement("""
                UPDATE doctor_schedule
                SET is_available = FALSE
                WHERE schedule_id = ?
            """)) {
                ps.setInt(1, scheduleId);
                ps.executeUpdate();
            }

            c.commit();
        }
    }

    /* =========================
       POZOSTAŁE METODY BEZ ZMIAN
       ========================= */

    public void cancelBySchedule(int scheduleId, Connection c) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE appointments SET status='CANCELLED' WHERE schedule_id=?")) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }

    public List<Appointment> getByPatient(int patientId) throws Exception {

        List<Appointment> list = new ArrayList<>();

        String updateDoneSql = """
            UPDATE appointments a
            JOIN doctor_schedule s ON a.schedule_id = s.schedule_id
            SET a.status = 'DONE'
            WHERE a.status = 'PLANNED'
              AND s.visit_date < NOW()
        """;

        String selectSql = """
            SELECT a.appointment_id,
                   a.doctor_id,
                   a.patient_id,
                   s.visit_date,
                   a.status,
                   d.first_name,
                   d.last_name,
                   sp.name AS specialization_name
            FROM appointments a
            JOIN doctor_schedule s ON a.schedule_id = s.schedule_id
            JOIN doctors d ON a.doctor_id = d.doctor_id
            JOIN specializations sp ON d.specialization_id = sp.specialization_id
            WHERE a.patient_id = ?
            ORDER BY s.visit_date
        """;

        try (Connection c = DataBaseConnection.getConnection()) {

            try (PreparedStatement ps = c.prepareStatement(updateDoneSql)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(selectSql)) {
                ps.setInt(1, patientId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("doctor_id"),
                            rs.getInt("patient_id"),
                            rs.getTimestamp("visit_date").toLocalDateTime(),
                            rs.getString("status"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("specialization_name")
                    ));
                }
            }
        }
        return list;
    }

    public void cancelByAppointment(int appointmentId) throws Exception {
        try (Connection c = DataBaseConnection.getConnection()) {
            c.setAutoCommit(false);

            int scheduleId = -1;

            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT schedule_id FROM appointments WHERE appointment_id=?")) {
                ps.setInt(1, appointmentId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) scheduleId = rs.getInt(1);
            }

            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE appointments SET status='CANCELLED' WHERE appointment_id=?")) {
                ps.setInt(1, appointmentId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE doctor_schedule SET is_available=TRUE WHERE schedule_id=?")) {
                ps.setInt(1, scheduleId);
                ps.executeUpdate();
            }

            c.commit();
        }
    }
}
