package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        showLogin();
    }

    public static void showLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Logowanie");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void showRegister() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Register.fxml"));
        mainStage.setScene(new Scene(loader.load()));
        mainStage.setTitle("Clinic System - Rejestracja");
        mainStage.show();
    }

    public static void showAddSpecialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/AddSpecialization.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dodaj Specjalizację");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showAddSpecializationAndWait(Runnable onSuccess) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/AddSpecialization.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Dodaj specjalizację");

        // zablokuj interakcję z głównym oknem
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait(); // <-- czekamy aż użytkownik zamknie okno
        if(onSuccess != null) onSuccess.run(); // po zamknięciu odśwież ChoiceBox
    }






}
