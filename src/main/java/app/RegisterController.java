package app;

import dao.DoctorDAO;
import dao.PatientDAO;
import dao.SpecializationDAO;
import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Specialization;
import model.User;

import java.util.List;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField peselField; // dla pacjenta
    @FXML private ChoiceBox<String> roleChoice; // Pacjent/Lekarz
    @FXML private ChoiceBox<String> specializationChoice; // Tylko dla lekarza
    @FXML private Label messageLabel;

    private final UserDAO userDAO = new UserDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final SpecializationDAO specializationDAO = new SpecializationDAO();

    @FXML
    public void initialize() {
        roleChoice.setItems(FXCollections.observableArrayList("Pacjent", "Lekarz"));
        loadSpecializations();

        // pokazuj ChoiceBox tylko dla lekarza
        roleChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            specializationChoice.setDisable(!"Lekarz".equals(newVal));
        });
    }

    private void loadSpecializations() {
        try {
            List<Specialization> specs = specializationDAO.getAllSpecializations();
            ObservableList<String> specNames = FXCollections.observableArrayList();
            for (Specialization s : specs) {
                specNames.add(s.getSpecializationName());
            }
            specializationChoice.setItems(specNames);
            specializationChoice.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String pesel = peselField.getText();
        String role = roleChoice.getValue();

        if(email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || role == null) {
            messageLabel.setText("Wypełnij wszystkie wymagane pola!");
            return;
        }

        try {
            int roleId = "Pacjent".equals(role) ? 3 : 2;
            String passwordHash = PasswordUtil.hashPassword(password);

            userDAO.createUser(email, passwordHash, roleId);
            User user = userDAO.findByEmail(email);

            if("Pacjent".equals(role)) {
                patientDAO.addPatient(user.getUserId(), firstName, lastName, pesel);
            } else if("Lekarz".equals(role)) {
                String specName = specializationChoice.getValue();
                if(specName == null) {
                    messageLabel.setText("Wybierz specjalizację!");
                    return;
                }
                int specializationId = specializationDAO.getSpecializationIdByName(specName);
                doctorDAO.addDoctor(user.getUserId(), specializationId, firstName, lastName);
            }

            messageLabel.setText("Rejestracja zakończona sukcesem!");
            clearForm();
        } catch (Exception e) {
            messageLabel.setText("Błąd rejestracji: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearForm() {
        emailField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        peselField.clear();
        roleChoice.getSelectionModel().clearSelection();
        specializationChoice.getSelectionModel().clearSelection();
    }

    @FXML
    private void backToLogin() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
            Main.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddSpecialization() {
        try {
            Main.showAddSpecializationAndWait(this::loadSpecializations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
