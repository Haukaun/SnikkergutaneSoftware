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
        setTitle("Bekreft overskriving");
        setHeaderText(project.getName() + ".csv finnes allerede i denne filplasseringen.");
        setContentText("Vil du overskrive eksisterende fil?");

        ButtonType buttonTypeYesToAll = new ButtonType("Ja til alle", ButtonBar.ButtonData.LEFT);
        ButtonType buttonTypeNoToAll = new ButtonType("Nei til alle", ButtonBar.ButtonData.RIGHT);
        ButtonType buttonTypeYes = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Nei", ButtonBar.ButtonData.NO);

        getDialogPane().getButtonTypes().addAll(buttonTypeYesToAll, buttonTypeNoToAll, buttonTypeYes, buttonTypeNo);

        setResultConverter(
                (ButtonType button) -> {
                    int result = 0;
                    if (button == buttonTypeYes) {
                        result = 1;
                    } else if (button == buttonTypeNo) {
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
