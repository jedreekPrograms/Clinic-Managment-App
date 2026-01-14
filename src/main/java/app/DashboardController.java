package app;

import dao.DoctorDAO;
import dao.DoctorScheduleDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.DoctorSchedule;
import model.User;
import service.DoctorService;

public class DashboardController {

    @FXML private ListView<DoctorSchedule> listView;
    @FXML private Label titleLabel;

    private User user;
    private int doctorId;

    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final DoctorScheduleDAO scheduleDAO = new DoctorScheduleDAO();
    private final DoctorService doctorService = new DoctorService();

    public static void show(User user) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                DashboardController.class.getResource("/fxml/Dashboard.fxml")
        );

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 480, 560));
        stage.setTitle("Clinic System – Lekarz");
        stage.setResizable(false);

        DashboardController c = loader.getController();
        c.user = user;
        c.init();

        stage.show();
    }

    private void init() throws Exception {
        doctorId = doctorDAO.getDoctorIdByUserId(user.getUserId());

        titleLabel.setText("Panel lekarza");

        listView.setCellFactory(lv -> new ScheduleCardCell());
        listView.setPlaceholder(new Label("Brak terminów"));

        reload();
    }

    private void reload() throws Exception {
        listView.getItems().setAll(
                scheduleDAO.getByDoctor(doctorId)
        );
    }

    @FXML
    private void handleAdd() {
        try {
            AddScheduleController.show(user, () -> {
                try {
                    reload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        DoctorSchedule selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            doctorService.removeSchedule(selected.getScheduleId());
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() throws Exception {
        listView.getScene().getWindow().hide();
        Main.showLogin();
    }
}
