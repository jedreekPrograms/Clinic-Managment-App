package app;

import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import model.Doctor;

public class DoctorCardCell extends ListCell<Doctor> {

    @Override
    protected void updateItem(Doctor d, boolean empty) {
        super.updateItem(d, empty);

        if (empty || d == null) {
            setGraphic(null);
            return;
        }

        VBox card = new VBox(6);
        card.getStyleClass().add("card");

        Label name = new Label(d.getFirstName() + " " + d.getLastName());
        name.getStyleClass().add("card-title");

        Label subtitle = new Label("Lekarz specjalista");
        subtitle.getStyleClass().add("card-subtitle");

        card.getChildren().addAll(name, subtitle);
        setGraphic(card);
    }
}
