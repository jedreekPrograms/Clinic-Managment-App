package app;

import dao.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.*;

public class PatientDashboardController {

    @FXML private ComboBox<Specialization> specializationBox;
    @FXML private ListView<Doctor> doctorsList;
    @FXML private ListView<Appointment> myVisitsList;
    @FXML private FlowPane timeGrid;

    private DoctorSchedule selectedSchedule;
    private int patientId;

    private final SpecializationDAO specDAO = new SpecializationDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final DoctorScheduleDAO scheduleDAO = new DoctorScheduleDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public static void show(int patientId) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                PatientDashboardController.class.getResource("/fxml/PatientDashboard.fxml")
        );

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 520, 600)); // ⬅️ mniejsza wysokość
        stage.setTitle("Clinic System – Pacjent");
        stage.setResizable(false);

        PatientDashboardController c = loader.getController();
        c.init(patientId);


        stage.show();

    }

    private void init(int patientId) throws Exception {
        this.patientId = patientId;

        specializationBox.getItems().setAll(specDAO.getAllSpecializations());

        doctorsList.setCellFactory(lv -> new DoctorCardCell());
        myVisitsList.setCellFactory(lv -> new AppointmentCardCell());

        doctorsList.setPlaceholder(
                new Label("Wybierz specjalizację, aby zobaczyć lekarzy")
        );

        myVisitsList.setPlaceholder(
                new Label("Brak zaplanowanych wizyt")
        );

        timeGrid.getChildren().add(
                new Label("Wybierz lekarza, aby zobaczyć terminy")
        );

        doctorsList.setOnMouseClicked(e -> loadSchedules());

        loadMyVisits();
    }

    @FXML
    private void loadDoctors() throws Exception {
        Specialization s = specializationBox.getValue();

        doctorsList.getItems().clear();
        timeGrid.getChildren().clear();
        selectedSchedule = null;

        if (s == null) return;

        doctorsList.getItems().setAll(
                doctorDAO.getBySpecialization(s.getSpecializationId())
        );
    }

    private void loadSchedules() {
        Doctor d = doctorsList.getSelectionModel().getSelectedItem();
        if (d == null) return;

        selectedSchedule = null;
        timeGrid.getChildren().clear();

        try {
            for (DoctorSchedule s : scheduleDAO.getAvailableByDoctor(d.getDoctorId())) {

                TimeChipButton chip = new TimeChipButton(
                        s.getVisitDate().toLocalTime().toString()
                );

                chip.setOnAction(e -> {
                    selectedSchedule = s;
                    timeGrid.getChildren().forEach(n ->
                            n.getStyleClass().remove("selected"));
                    chip.getStyleClass().add("selected");
                });

                timeGrid.getChildren().add(chip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void book() throws Exception {
        if (selectedSchedule == null) return;

        appointmentDAO.book(
                selectedSchedule.getScheduleId(),
                selectedSchedule.getDoctorId(),
                patientId
        );

        loadSchedules();
        loadMyVisits();
    }

    private void loadMyVisits() throws Exception {
        myVisitsList.getItems().setAll(
                appointmentDAO.getByPatient(patientId)
        );
    }

    @FXML
    private void cancelVisit() throws Exception {
        Appointment a = myVisitsList.getSelectionModel().getSelectedItem();
        if (a == null || !"PLANNED".equals(a.getStatus())) return;

        appointmentDAO.cancelByAppointment(a.getAppointmentId());
        loadMyVisits();
    }

    @FXML
    private void logout() throws Exception {
        specializationBox.getScene().getWindow().hide();
        Main.showLogin();
    }


}
