package com.snikkergutane;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Controller class for the login scene.
 */
public class LoginController {

    /**
     * Switches the scene to the main scene.
     * @throws IOException if the main scene could not be loaded.
     */
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
        App.setSize(1216,714);
        App.stage.setResizable(true);
    }


}
