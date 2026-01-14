package model;

public class Specialization {

    private int specializationId;
    private String specializationName;

    public Specialization(int specializationId, String specializationName) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    @Override
    public String toString() {
        return specializationName;
    }
}
