package app;

import dao.SpecializationDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SpecializationController {

    @FXML private TextField nameField;
    @FXML private Label messageLabel;

    private final SpecializationDAO specializationDAO = new SpecializationDAO();

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        if(name.isEmpty()) {
            messageLabel.setText("Wpisz nazwę specjalizacji!");
            return;
        }

        try {
            specializationDAO.addSpecialization(name);
            messageLabel.setText("Specjalizacja dodana!");
            nameField.clear();
        } catch (Exception e) {
            messageLabel.setText("Błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
