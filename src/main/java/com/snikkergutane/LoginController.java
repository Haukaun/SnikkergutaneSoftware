package com.snikkergutane;

import java.io.IOException;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }
}
