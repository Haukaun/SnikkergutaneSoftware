package com.snikkergutane;

import com.snikkergutane.project.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class TaskController {
    @FXML private Label newTaskLabel;
    @FXML private Button newTaskSaveButton;
    @FXML private Button newTaskResetButton;
    @FXML private TextField newTaskNameTextField;
    @FXML private TextArea newTaskDescriptionTextArea;
    @FXML private DatePicker newTaskStartDatePicker;
    @FXML private DatePicker newTaskEndDatePicker;
    @FXML private HBox imageDisplayHBox;
    private final MainController mainController;
    private boolean editMode = false;
    private Task task = null;

    public TaskController() {
        this.mainController = null;
    }

    public TaskController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    private void saveNewTaskButtonClicked() {
        if (newTaskNameTextField.getText().isBlank()) {

        } else if (newTaskDescriptionTextArea.getText().isBlank()) {

        } else if (newTaskStartDatePicker.getValue() == null) {

        } else if (newTaskEndDatePicker.getValue() == null) {

        } else {
            ArrayList<String> imageUrls = new ArrayList<>();
            for (Node checkBox : imageDisplayHBox.getChildren()) {
            }
//            task = new Task(newTaskNameTextField.getText(),
//                    newTaskStartDatePicker.getValue(),
//                    newTaskEndDatePicker.getValue(),
//                    newTaskDescriptionTextArea.getText());
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
