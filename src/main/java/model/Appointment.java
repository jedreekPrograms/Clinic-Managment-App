package model;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentId;
    private int doctorId;
    private int patientId;
    private LocalDateTime visitDate;
    private String status;

    private String doctorFirstName;
    private String doctorLastName;
    private String specializationName;

    public Appointment(int appointmentId,
                       int doctorId,
                       int patientId,
                       LocalDateTime visitDate,
                       String status,
                       String doctorFirstName,
                       String doctorLastName,
                       String specializationName) {

        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.visitDate = visitDate;
        this.status = status;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.specializationName = specializationName;
    }

    public int getAppointmentId() { return appointmentId; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public LocalDateTime getVisitDate() { return visitDate; }
    public String getStatus() { return status; }

    public String getDoctorFirstName() { return doctorFirstName; }
    public String getDoctorLastName() { return doctorLastName; }
    public String getSpecializationName() { return specializationName; }

    @Override
    public String toString() {
        return visitDate +
                " | " + doctorFirstName + " " + doctorLastName +
                " (" + specializationName + ")" +
                " | " + status;
    }
}
