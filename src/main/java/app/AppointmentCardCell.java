package app;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Appointment;

public class AppointmentCardCell extends ListCell<Appointment> {

    @Override
    protected void updateItem(Appointment a, boolean empty) {
        super.updateItem(a, empty);

        if (empty || a == null) {
            setGraphic(null);
            return;
        }

        VBox card = new VBox(6);
        card.getStyleClass().add("card");

        Label info = new Label(
                a.getVisitDate() + " | " +
                        a.getDoctorFirstName() + " " + a.getDoctorLastName()
        );
        info.getStyleClass().add("card-title");

        Label status = new Label(a.getStatus());
        status.getStyleClass().add(
                "PLANNED".equals(a.getStatus()) ? "badge-success" :
                        "CANCELLED".equals(a.getStatus()) ? "badge-danger" :
                                "badge-muted"
        );

        card.getChildren().addAll(info, status);
        setGraphic(card);
    }
}
