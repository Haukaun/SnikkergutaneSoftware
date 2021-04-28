package com.snikkergutane;

import com.snikkergutane.project.Task;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    private ContextMenu nameValidator;
    private ContextMenu descriptionValidator;
    private ContextMenu startDateValidator;
    private ContextMenu endDateValidator;
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
        newTaskPane.getStylesheets().add(getClass()
                .getResource("/com/snikkergutane/newTask.css")
                .toExternalForm());

        //Creates an error message to display to the user.
        nameValidator = new ContextMenu();
        nameValidator.setAutoHide(false);
        nameValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven et navn"));

        //Adds a listener to show error message if the TextField is left empty
        newTaskNameTextField.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (newTaskNameTextField.getText().isBlank()) {
                            nameValidator.show(newTaskNameTextField, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            nameValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        descriptionValidator = new ContextMenu();
        descriptionValidator.setAutoHide(false);
        descriptionValidator.getItems().add(
                new MenuItem("Vennligst fyll inn beskrivelse av oppgaven"));

        //Adds a listener to show error message if the TextField is left empty
        newTaskDescriptionTextArea.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskDescriptionTextArea is empty
                        if (newTaskDescriptionTextArea.getText().isBlank()) {
                            descriptionValidator.show(newTaskDescriptionTextArea, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            descriptionValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        startDateValidator = new ContextMenu();
        startDateValidator.setAutoHide(false);
        startDateValidator.getItems().add(
                new MenuItem("Vennligst angi en startdato i format dd.mm.åååå"));

        //Adds a listener to show error message if no valid start date has been set
        newTaskStartDatePicker.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if no valid date has been set
                        if (null == newTaskStartDatePicker.getValue()) {
                            startDateValidator.show(newTaskStartDatePicker, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            startDateValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        endDateValidator = new ContextMenu();
        endDateValidator.setAutoHide(false);
        endDateValidator.getItems().add(
                new MenuItem("Vennligst angi en sluttdato i format dd.mm.åååå"));

        //Adds a listener to show error message if no valid end date has been set
        newTaskEndDatePicker.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if no valid date has been set
                        if (null == newTaskEndDatePicker.getValue()) {
                            endDateValidator.show(newTaskEndDatePicker, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            endDateValidator.hide();
                        }
                    }
                });
    }

    @FXML
    private void addImageButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("Bildefiler (*.png;*.jpg;*.jpeg)", "*.png;*.jpg;*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        fileChooser.setTitle("Åpne ressursfil");
        List<File> fileList = fileChooser.showOpenMultipleDialog(newTaskPane.getScene().getWindow());

        if (null != fileList) {
            System.out.println(imageDisplayGridPane.getColumnCount());
            int i = 0;
            if (imageDisplayGridPane.getColumnCount() > 1) {
                i = imageDisplayGridPane.getColumnCount();
            }
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
                checkBox.getStylesheets().add("com/snikkergutane/newTask.css");

                if (i >= imageDisplayGridPane.getColumnCount()) {
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
    private void removeSelectedImagesButtonClicked() {
        //Creates a list containing the checked checkboxes in the imageDisplayGrid
        List<CheckBox> checkedBoxes = imageDisplayGridPane.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .filter(node -> ((CheckBox) node).isSelected())
                .map(CheckBox.class::cast)
                .collect(toList());
        //Creates a list containing the imageviews in the imageDisplayGrid
        List<ImageView> imageViews = imageDisplayGridPane.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(ImageView.class::cast)
                .collect(toList());

        //for each checked box, remove it and the image it's assigned to.
        for (CheckBox box : checkedBoxes) {
            for (ImageView image : imageViews) {
                if (box.getText().equals(image.getImage().getUrl())) {
                    imageDisplayGridPane.getChildren().removeAll(image, box);
                }
            }
        }
    }

    @FXML
    private void saveNewTaskButtonClicked() {
        if (newTaskNameTextField.getText().isBlank()) {
            nameValidator.show(newTaskNameTextField, Side.RIGHT, 10, 0);
        } else if (newTaskDescriptionTextArea.getText().isBlank()) {
            descriptionValidator.show(newTaskDescriptionTextArea, Side.RIGHT, 10, 0);
        } else if (newTaskStartDatePicker.getValue() == null) {
            startDateValidator.show(newTaskStartDatePicker, Side.RIGHT, 10, 0);
        } else if (newTaskEndDatePicker.getValue() == null) {
            endDateValidator.show(newTaskEndDatePicker, Side.RIGHT, 10, 0);
        } else {
            ArrayList<String> imageUrls = new ArrayList<>();
            imageDisplayGridPane.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    imageUrls.add(((ImageView) node).getImage().getUrl());
                }
            });
            System.out.println(imageUrls);
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
