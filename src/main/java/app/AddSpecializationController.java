package app;

import dao.SpecializationDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddSpecializationController {

    @FXML private TextField specializationField;
    @FXML private Label messageLabel;

    private final SpecializationDAO specializationDAO = new SpecializationDAO();

    @FXML
    private void handleAddSpecialization() {
        String name = specializationField.getText().trim();
        if(name.isEmpty()) {
            messageLabel.setText("Wpisz nazwę specjalizacji!");
            return;
        }

        try {
            specializationDAO.addSpecialization(name);
            messageLabel.setText("Specjalizacja dodana: " + name);
            specializationField.clear();
        } catch (Exception e) {
            messageLabel.setText("Błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
