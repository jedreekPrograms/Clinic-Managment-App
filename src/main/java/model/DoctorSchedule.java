package model;

import java.time.LocalDateTime;

public class DoctorSchedule {

    private int scheduleId;
    private int doctorId;
    private LocalDateTime visitDate;
    private boolean available;

    public DoctorSchedule(int scheduleId, int doctorId,
                          LocalDateTime visitDate, boolean available) {
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
        this.available = available;
    }

    public int getScheduleId() { return scheduleId; }
    public int getDoctorId() { return doctorId; }
    public LocalDateTime getVisitDate() { return visitDate; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return visitDate + (available ? " [WOLNY]" : " [ZAJĘTY]");
    }
}
