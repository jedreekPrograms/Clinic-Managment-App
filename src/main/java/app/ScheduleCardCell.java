package app;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.DoctorSchedule;

public class ScheduleCardCell extends ListCell<DoctorSchedule> {

    @Override
    protected void updateItem(DoctorSchedule s, boolean empty) {
        super.updateItem(s, empty);

        if (empty || s == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        VBox card = new VBox(4);

        Label date = new Label(s.getVisitDate().toString());
        date.setStyle("-fx-font-weight: 600; -fx-text-fill: #111111;");

        Label status = new Label("DOSTĘPNY");
        status.setStyle("-fx-text-fill: #15803d; -fx-font-weight: 600;");

        card.getChildren().addAll(date, status);

        setText(null);
        setGraphic(card);
    }
}
