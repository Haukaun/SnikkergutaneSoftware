package com.snikkergutane;

import com.snikkergutane.project.Project;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Dialog to be displayed when attempting to overwrite an existing project file.
 * The dialog returns an Integer that is to be parsed by the caller.
 *
 * Returns 1 if the previous file is to be overwritten,
 *   2 if the previous file is not to be overwritten,
 *   3 if all files are to be overwritten, or
 *   4 if no files are to be overwritten.
 */
public class OverwriteConfirmationDialog extends Dialog<Integer> {

    private final Project project;

    /**
     * Creates a new instance of the class.
     * @param project {@code Project} that already has a save file.
     */
    public OverwriteConfirmationDialog(Project project) {
        super();
        this.project = project;
        createContent();
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass()
                .getResource("/com/snikkergutane/icons/gear-icon.png")
                .toExternalForm()));
    }

    /**
     * Creates the contents of the dialog.
     */
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
