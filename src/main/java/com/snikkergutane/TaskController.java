package com.snikkergutane;

import com.snikkergutane.project.Task;
import javafx.css.converter.EffectConverter;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    @FXML private BorderPane newTaskPane;
    @FXML private Label newTaskLabel;
    @FXML private Button newTaskSaveButton;
    @FXML private Button newTaskResetButton;
    @FXML private TextField newTaskNameTextField;
    @FXML private TextArea newTaskDescriptionTextArea;
    @FXML private DatePicker newTaskStartDatePicker;
    @FXML private DatePicker newTaskEndDatePicker;
    @FXML private GridPane imageDisplayGridPane;
    private MainController mainController;
    private boolean editMode = false;
    private Task task = null;

    public TaskController() {
        mainController = null;
    }

    public TaskController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void addImageButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("Image files (*.png;*.jpg;*.jpeg)", "*.png;*.jpg;*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        fileChooser.setTitle("Åpne ressursfil");
        List<File> fileList = fileChooser.showOpenMultipleDialog(newTaskPane.getScene().getWindow());

        if (null != fileList) {
            int i = 0;
            for (File file : fileList) {
                ImageView imageView = new ImageView(file.toURI().toString());
                imageView.setEffect(new DropShadow());
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150);

                CheckBox checkBox = new CheckBox();
                checkBox.setFont(Font.font(100f));
                checkBox.setText(file.toURI().toString());
                checkBox.contentDisplayProperty().setValue(ContentDisplay.GRAPHIC_ONLY);
                checkBox.setMaxWidth(200);
                checkBox.getStylesheets().add("com/snikkergutane/checkbox.css");

                if (i > 0) {
                    ColumnConstraints column = new ColumnConstraints();
                    column.setHalignment(HPos.CENTER);
                    imageDisplayGridPane.getColumnConstraints().add(column);
                }
                imageDisplayGridPane.add(imageView, i, 0);
                imageDisplayGridPane.add(checkBox, i, 0);

                i++;
            }
        }
    }

    @FXML
    private void saveNewTaskButtonClicked() {
        if (newTaskNameTextField.getText().isBlank()) {
            System.out.println("nameTextField = " + newTaskNameTextField.getText());
        } else if (newTaskDescriptionTextArea.getText().isBlank()) {
            System.out.println("description = " + newTaskDescriptionTextArea.getText());
        } else if (newTaskStartDatePicker.getValue() == null) {
            System.out.println("startDate = " + newTaskStartDatePicker.getValue());
        } else if (newTaskEndDatePicker.getValue() == null) {
            System.out.println("endDate = " + newTaskEndDatePicker.getValue());
        } else {
            ArrayList<String> imageUrls = new ArrayList<>();
            imageDisplayGridPane.getChildren().forEach(node -> {
                if (node instanceof CheckBox) {
                    imageUrls.add(((CheckBox) node).getText());
                }
            });

            task = new Task(newTaskNameTextField.getText(),
                    newTaskStartDatePicker.getValue(),
                    newTaskEndDatePicker.getValue(),
                    newTaskDescriptionTextArea.getText(),
                    imageUrls);
            mainController = (MainController) App.getController(newTaskPane.getScene().getRoot());
            if (mainController != null) {

                mainController.addTask(task);
            }
        }
    }

    @FXML
    private void resetButtonClicked() {
        if (editMode) {
            editMode(task);
        } else {
            newTaskNameTextField.setText("");
            newTaskDescriptionTextArea.setText("");
            newTaskStartDatePicker.getEditor().clear();
            newTaskEndDatePicker.getEditor().clear();
        }
    }

    private void editMode(Task task) {
        this.task = task;
        editMode = true;
        newTaskLabel.setText("Endre Oppgave");
        newTaskSaveButton.setText("Lagre endringer");
        newTaskResetButton.setText("Tilbakestill alle felt");
        newTaskNameTextField.setText(task.getName());
        newTaskDescriptionTextArea.setText(task.getDescription());
        newTaskStartDatePicker.setValue(task.getStartDate());
        newTaskEndDatePicker.setValue(task.getEndDate());
    }
}
