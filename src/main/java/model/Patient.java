package model;

public class Patient {
    private int patientId;
    private int userId;
    private String firstName;
    private String lastName;
    private String pesel;

    public Patient(int patientId, int userId, String firstName, String lastName, String pesel) {
        this.patientId = patientId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public int getPatientId() { return patientId; }
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPesel() { return pesel; }
}

