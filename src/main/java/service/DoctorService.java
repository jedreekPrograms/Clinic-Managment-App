package service;

import dao.AppointmentDAO;
import dao.DoctorScheduleDAO;
import db.DataBaseConnection;

import java.sql.Connection;

public class DoctorService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final DoctorScheduleDAO scheduleDAO = new DoctorScheduleDAO();

    public void removeSchedule(int scheduleId) throws Exception {

        try (Connection c = DataBaseConnection.getConnection()) {
            c.setAutoCommit(false); // 🔴 TRANSAKCJA

            // 1️⃣ anuluj wizyty pacjentów
            appointmentDAO.cancelBySchedule(scheduleId, c);

            // 2️⃣ oznacz termin jako niedostępny
            scheduleDAO.markUnavailable(scheduleId, c);

            c.commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
