package com.snikkergutane;

import com.snikkergutane.project.Project;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OverwriteConfirmationDialog extends Dialog<Integer> {

    private Project project;

    public OverwriteConfirmationDialog(Project project) {
        super();
        this.project = project;
        createContent();
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass()
                .getResource("/com/snikkergutane/Icons/gear-icon.png")
                .toExternalForm()));
    }

    private void createContent() {
        setTitle("Confirm overwrite");
        setHeaderText(project.getName() + ".csv already exists in this location.");
        setContentText("Overwrite existing file?");

        ButtonType buttonTypeYesToAll = new ButtonType("Yes to all", ButtonBar.ButtonData.LEFT);
        ButtonType buttonTypeNoToAll = new ButtonType("No to all", ButtonBar.ButtonData.RIGHT);
        getDialogPane().getButtonTypes().addAll(buttonTypeYesToAll, buttonTypeNoToAll, ButtonType.YES, ButtonType.NO);

        setResultConverter(
                (ButtonType button) -> {
                    int result = 0;
                    if (button == ButtonType.YES) {
                        result = 1;
                    } else if (button == ButtonType.NO) {
                        result = 2;
                    } else if (button == buttonTypeYesToAll) {
                        result = 3;
                    } else if (button == buttonTypeNoToAll) {
                        result = 4;
                    }
                    return result;
                }
        );
    }

}
