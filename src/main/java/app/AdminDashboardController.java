package app;

import javafx.fxml.FXML;
import model.User;

public class AdminDashboardController {

    private static User loggedUser;

    public static void show(User user) throws Exception {
        loggedUser = user;

        var loader = new javafx.fxml.FXMLLoader(
                AdminDashboardController.class.getResource("/fxml/AdminDashboard.fxml"));

        var stage = new javafx.stage.Stage();
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.setTitle("ADMIN PANEL");
        stage.show();
    }

    @FXML
    private void addSchedule() {
        try {
            AddScheduleController.show(loggedUser, () -> {
                // Admin nie ma listy do odświeżenia
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
