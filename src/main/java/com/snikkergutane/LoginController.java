package com.snikkergutane;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
        App.setSize(1216,714);
        App.stage.setResizable(true);
    }


}
