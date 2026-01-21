package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Główna klasa aplikacji JavaFX.
 * <p>
 * Odpowiada za:
 * <ul>
 *     <li>uruchomienie aplikacji</li>
 *     <li>zarządzanie głównym oknem (Stage)</li>
 *     <li>przełączanie widoków FXML</li>
 * </ul>
 */
public class Main extends Application {

    /**
     * Główne okno aplikacji.
     * Przechowuje aktualną scenę i umożliwia jej zmianę.
     */
    private static Stage mainStage;

    /**
     * Metoda wywoływana automatycznie przy starcie aplikacji JavaFX.
     *
     * @param stage główne okno aplikacji
     * @throws Exception w przypadku problemów z ładowaniem FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        showLogin();
    }

    /**
     * Wyświetla okno logowania jako osobne okno (Stage).
     *
     * @throws Exception w przypadku błędu ładowania pliku FXML
     */
    public static void showLogin() throws Exception {
        FXMLLoader loader =
                new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Logowanie");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    /**
     * Wyświetla widok rejestracji w głównym oknie aplikacji.
     *
     * @throws Exception w przypadku błędu ładowania pliku FXML
     */
    public static void showRegister() throws Exception {
        FXMLLoader loader =
                new FXMLLoader(Main.class.getResource("/fxml/Register.fxml"));

        mainStage.setScene(new Scene(loader.load()));
        mainStage.setTitle("Clinic System - Rejestracja");
        mainStage.show();
    }

    /**
     * Wyświetla okno dodawania specjalizacji jako osobne okno.
     *
     * @throws Exception w przypadku błędu ładowania pliku FXML
     */
    public static void showAddSpecialization() throws Exception {
        FXMLLoader loader =
                new FXMLLoader(Main.class.getResource("/fxml/AddSpecialization.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Dodaj Specjalizację");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    /**
     * Punkt wejścia aplikacji.
     *
     * @param args argumenty linii poleceń
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Wyświetla okno dodawania specjalizacji jako okno modalne
     * i czeka na jego zamknięcie.
     * <p>
     * Po zamknięciu okna wykonywana jest akcja przekazana
     * jako parametr {@code onSuccess}.
     *
     * @param onSuccess akcja wykonywana po zamknięciu okna (np. odświeżenie danych)
     * @throws Exception w przypadku błędu ładowania pliku FXML
     */
    public static void showAddSpecializationAndWait(Runnable onSuccess)
            throws Exception {

        FXMLLoader loader =
                new FXMLLoader(Main.class.getResource("/fxml/AddSpecialization.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Dodaj specjalizację");

        // blokuje interakcję z innymi oknami aplikacji
        stage.initModality(Modality.APPLICATION_MODAL);

        // czeka aż użytkownik zamknie okno
        stage.showAndWait();

        // wykonuje akcję po zamknięciu okna
        if (onSuccess != null) {
            onSuccess.run();
        }
    }
}
