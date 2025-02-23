package com.snikkergutane;

import com.snikkergutane.project.Task;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Controller class for the task.fxml.
 */
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
    private boolean editMode;
    private Task task;

    /**
     * Creates a new instace of the class.
     */
    public TaskController() {
        mainController = null;
        editMode = false;
        task = null;
    }

    /**
     * Sets the Task that is to be handled by the controllers other methods.
     * @param task {@code Task} the task to be set.
     */
    public void setTask(Task task) {
        this.task = task;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Adds the .css file to the contents of the fxml, instantiates the validators for the text fields,
     * and sets the controller's edit mode to true if no task has been set.
     */
    @FXML
    private void initialize() {
        newTaskPane.getStylesheets().add(getClass()
                .getResource("/com/snikkergutane/task.css")
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
        if (null != this.task) {
            useEditMode();
        }
    }

    /**
     * Opens a file chooser and adds the chosen image files to the image display.
     */
    @FXML
    private void addImageButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("Bildefiler (*.png;*.jpg;*.jpeg)", "*.png;*.jpg;*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        fileChooser.setTitle("Åpne ressursfil");
        List<File> fileList =  fileChooser.showOpenMultipleDialog(newTaskPane.getScene().getWindow());
        if (null != fileList) {
            List<Image> imageList = new ArrayList<>();
            fileList.forEach(file -> imageList.add(new Image(file.toURI().toString())));
            displayImages(imageList);
        }
    }

    /**
     * Displays all the images in given list to the image display,
     * as well as a transparent CheckBox in front of each image,
     * to allow the user to select images.
     * @param imageList {@code List<Image>} of the images to be added.
     */
    private void displayImages(List<Image> imageList) {
        if (null != imageList) {

            for (Image image : imageList) {
                ImageView imageView = new ImageView(image);
                imageView.setEffect(new DropShadow());
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150);

                CheckBox checkBox = new CheckBox();
                checkBox.setFont(Font.font(100f));
                checkBox.setText(image.getUrl());
                checkBox.contentDisplayProperty().setValue(ContentDisplay.GRAPHIC_ONLY);
                checkBox.setMaxWidth(200);
                checkBox.getStylesheets().add("com/snikkergutane/task.css");

                ColumnConstraints column = new ColumnConstraints();
                column.setHalignment(HPos.CENTER);
                imageDisplayGridPane.getColumnConstraints().add(column);

                int i = imageDisplayGridPane.getColumnCount();
                imageDisplayGridPane.add(imageView, i, 0);
                imageDisplayGridPane.add(checkBox, i, 0);

            }
        }
    }

    /**
     * Removes the selected images from the image display.
     */
    @FXML
    private void removeSelectedImagesButtonClicked() {
        //Creates a list containing the checked checkboxes in the imageDisplayGrid
        List<CheckBox> checkedBoxes = imageDisplayGridPane.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .filter(node -> ((CheckBox) node).isSelected())
                .map(CheckBox.class::cast)
                .collect(toList());
        //Creates a list containing the ImageViews in the imageDisplayGrid
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

    /**
     * If this class' edit mode is true - edits this class' task.
     * If this class' edit mode is false - creates a new task from the information entered,
     * adds it to the project selected in the project list, and shows it to the user.
     */
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

            ArrayList<Image> images = new ArrayList<>();
            imageDisplayGridPane.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    images.add(((ImageView) node).getImage());
                }
            });

            if (editMode) {
                task.setName(newTaskNameTextField.getText());
                task.setDescription(newTaskDescriptionTextArea.getText());
                task.setStartDate(newTaskStartDatePicker.getValue());
                task.setEndDate(newTaskEndDatePicker.getValue());
                task.setImages(images);
            } else {
                task = new Task(newTaskNameTextField.getText(),
                        newTaskStartDatePicker.getValue(),
                        newTaskEndDatePicker.getValue(),
                        newTaskDescriptionTextArea.getText(),
                        imageUrls);
            }
            if (mainController != null && !editMode) {
                mainController.addTask(task);
            }
            else if (mainController != null) {
                mainController.viewTask(task);
            }
        }
    }

    /**
     * If this class' edit mode is true - calls for the mainController to remove the task from its project.
     * If this class' edit mode is false - removes all text and images from the fields and image display.
     */
    @FXML
    private void resetButtonClicked() {
        if (editMode && null != mainController) {
            if (showDeletionConfirmationDialog(task)) {
                mainController.removeTask(task);
            }
        } else {
            newTaskNameTextField.setText("");
            newTaskDescriptionTextArea.setText("");
            newTaskStartDatePicker.getEditor().clear();
            newTaskEndDatePicker.getEditor().clear();
            imageDisplayGridPane.getChildren().clear();
        }
    }
    /**
     * Asks the user for a deletion confirmation of given task.
     * @param task the {@code Task} to be deleted.
     * @return {@code boolean} true if confirmed, or false if cancelled.
     */
    private boolean showDeletionConfirmationDialog(Task task) {
        boolean deleteConfirmed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/snikkergutane/icons/trash-can.png"));
        alert.setTitle("Bekreft fjerning");
        alert.setHeaderText("Bekreft fjerning");
        alert.setContentText("Slette "
                + task.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            deleteConfirmed = (result.get() == ButtonType.OK);
        }
        return deleteConfirmed;
    }

    /**
     * Sets the class to edit mode,
     * and updates the text of the affected graphical elements to reflect this.
     */
    public void useEditMode() {
        editMode = true;
        newTaskLabel.setText("Endre Oppgave");
        newTaskSaveButton.setText("Lagre endringer");

        newTaskResetButton.setText("Fjern Oppgave");

        newTaskNameTextField.setText(task.getName());
        newTaskDescriptionTextArea.setText(task.getDescription());
        newTaskStartDatePicker.setValue(task.getStartDate());
        newTaskEndDatePicker.setValue(task.getEndDate());

        displayImages(task.getImages());
    }
}
