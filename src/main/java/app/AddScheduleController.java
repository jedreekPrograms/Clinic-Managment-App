package app;

import dao.DoctorDAO;
import dao.DoctorScheduleDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddScheduleController {

    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private Label message;

    private static User user;
    private static Runnable onSave;

    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final DoctorScheduleDAO scheduleDAO = new DoctorScheduleDAO();

    public static void show(User u, Runnable afterSave) throws Exception {
        user = u;
        onSave = afterSave;

        var loader = new javafx.fxml.FXMLLoader(
                AddScheduleController.class.getResource("/fxml/AddSchedule.fxml")
        );

        var stage = new Stage();
        stage.setScene(new javafx.scene.Scene(loader.load(), 420, 360));
        stage.setTitle("Dodaj termin");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleAdd() {
        try {
            if (datePicker.getValue() == null || timeField.getText().isBlank()) {
                message.setText("Uzupełnij datę i godzinę");
                return;
            }

            int doctorId = doctorDAO.getDoctorIdByUserId(user.getUserId());

            LocalDateTime dt = LocalDateTime.of(
                    datePicker.getValue(),
                    LocalTime.parse(timeField.getText())
            );

            scheduleDAO.add(doctorId, dt);
            onSave.run();

            close();
        } catch (Exception e) {
            message.setText("Nieprawidłowy format godziny");
        }
    }

    @FXML
    private void handleCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) timeField.getScene().getWindow();
        stage.close();
    }
}
