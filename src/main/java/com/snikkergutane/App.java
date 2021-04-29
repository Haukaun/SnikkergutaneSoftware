package com.snikkergutane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage stage;
    private static MainController mainController;

    /**
     * Loads the login fxml to the given stage and shows the stage.
     * @param stage {@code Stage} to be shown.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        scene = new Scene(loadFxml("login"));
        stage.setTitle("Snikkergutane Software");
        stage.getIcons().add(new Image(getClass().getResource("/com/snikkergutane/images/snikkergutane-logo-small.png").toExternalForm()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launches the application.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets the scene root to a given fxml.
     * @param fxml {@code String} name of the .fxml file.
     * @throws IOException if unable to load fxml.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFxml(fxml));
    }

    /**
     * Sets the size of the stage.
     * @param width width of the stage
     * @param height height of the stage
     */
    static void setSize(int width, int height) {
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        scene.getWindow().centerOnScreen();
    }

    /**
     * Returns a parent node of a .fxml.
     * @param fxml the name of the .fxml file to be loaded.
     * @return {@code Parent} the loaded fxml.
     * @throws IOException exception if fxml could not be loaded.
     */
    private static Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        if (fxml.equals("main")) {
            App.mainController = fxmlLoader.getController();
        }
        return parent;
    }

    /**
     * Calls method to save projects to file on closing application.
     */
    @Override
    public void stop() {
        mainController.saveProjectsToDefaultDirectory();
    }
}