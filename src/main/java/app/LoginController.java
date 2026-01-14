package app;

import dao.PatientDAO;
import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final UserDAO userDAO = new UserDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    private void handleLogin() {
        try {
            User user = userDAO.findByEmail(emailField.getText());

            if (user == null ||
                    !PasswordUtil.checkPassword(passwordField.getText(), user.getPasswordHash())) {
                messageLabel.setText("Błędny login lub hasło");
                return;
            }

            // ROLE:
            // 1 - ADMIN
            // 2 - LEKARZ
            // 3 - PACJENT

            if (user.getRoleId() == 2) {
                DashboardController.show(user);
            }
            else if (user.getRoleId() == 3) {
                int patientId = patientDAO
                        .getAllPatients()
                        .stream()
                        .filter(p -> p.getUserId() == user.getUserId())
                        .findFirst()
                        .orElseThrow()
                        .getPatientId();

                PatientDashboardController.show(patientId);
            }
            else {
                AdminDashboardController.show(user);
            }

            emailField.getScene().getWindow().hide();

        } catch (Exception e) {
            messageLabel.setText("Błąd logowania");
            e.printStackTrace();
        }
    }

    @FXML
    private void openRegister() {
        try {
            Main.showRegister();
            emailField.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
