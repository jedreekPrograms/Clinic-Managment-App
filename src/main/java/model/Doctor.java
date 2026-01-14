package model;

public class Doctor {

    private int doctorId;
    private int userId;
    private int specializationId;
    private String firstName;
    private String lastName;

    public Doctor(int doctorId, int userId,
                  int specializationId, String firstName, String lastName) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.specializationId = specializationId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getDoctorId() { return doctorId; }
    public int getUserId() { return userId; }
    public int getSpecializationId() { return specializationId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
