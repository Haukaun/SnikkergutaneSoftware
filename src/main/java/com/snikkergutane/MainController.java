package com.snikkergutane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.snikkergutane.project.CsvManager;
import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import com.snikkergutane.project.Task;
import com.snikkergutane.project.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Controller for the main.fxml,
 * this will handle all main user interaction.
 */
public class MainController {

    private CsvManager csvManager;
    private final ProjectLib projectLib = new ProjectLib();
    private ObservableList<String> projectListWrapper;
    @FXML
    BorderPane mainPane;
    @FXML
    Label statusLabel;
    @FXML
    private HBox largeImageBackground;
    @FXML
    private ImageView largeImageView;
    @FXML
    private ListView<String> projectsListView;
    @FXML
    private ScrollPane projectInfoScrollPane;
    @FXML
    private GridPane taskListGridPane;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label projectAddressLabel;
    @FXML
    private Label customerPhoneNumberLabel;
    @FXML
    private Label customerEmailLabel;
    @FXML
    private Label projectStartDateLabel;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private TabPane projectTabPane;
    @FXML
    private Tab addTaskTab;

    /**
     * loads the demo project on startup,
     * and shows the projects in the project list.
     */
    @FXML
    private void initialize() {
        projectLib.loadDemoProject();
        projectListWrapper = FXCollections.observableArrayList
                (this.projectLib.listProjects());
        projectsListView.setItems(projectListWrapper);
    }

    /**
     * When a project in the project list is selected,
     * creates and shows the project information tab.
     */
    @FXML
    private void projectSelected() {
        //Checks that a project is selected.
        if (projectsListView.getSelectionModel().getSelectedItem() != null) {

            //Gets the selected project from the library.
            Project selectedProject = projectLib.getProject(projectsListView.getSelectionModel().getSelectedItem());

            //Sets the text of the labels in the information tab to the information in the selected project.
            customerNameLabel.setText(selectedProject.getCustomerName());
            customerEmailLabel.setText(selectedProject.getCustomerEmail());
            customerPhoneNumberLabel.setText(selectedProject.getCustomerPhoneNumber());
            projectAddressLabel.setText(selectedProject.getAddress());
            projectStartDateLabel.setText("" + selectedProject.getStartDate());
            projectDescriptionTextArea.setText(selectedProject.getDescription());

            //Updates the task list with the tasks from the selected project.
            taskListGridPane.getChildren().clear();
            int y = 0;

            for (Task task : selectedProject.getTasks()) {
                Button button = new Button(task.getName());
                button.setBackground(Background.EMPTY);
                button.setOnAction(e -> taskButtonClicked(task));

                taskListGridPane.add(button, 0, y);

                if (task.isFinished()) {
                    ImageView finishedImage = new ImageView("com/snikkergutane/images/person.png");
                    finishedImage.setPreserveRatio(true);
                    finishedImage.setFitHeight(30);
                    taskListGridPane.add(finishedImage, 1, y);
                } else {
                    ImageView unfinishedImage = new ImageView("com/snikkergutane/images/key.png");
                    unfinishedImage.setPreserveRatio(true);
                    unfinishedImage.setFitHeight(30);
                    taskListGridPane.add(unfinishedImage, 1, y);
                }
                y++;
            }

            //Removes all tabs from the tab pane and adds the selected project's information tab.
            projectTabPane.getTabs().clear();
            projectTabPane.getTabs().add(new Tab("Project Info", projectInfoScrollPane));
            selectedProject.getTasks().forEach(this::taskButtonClicked);
            projectTabPane.getTabs().add(this.addTaskTab);
        }
    }

    /**
     * When a task in the project information tab is selected,
     * creates and shows the information tab for the task.
     *
     * @param task the task to be viewed.
     */
    @FXML
    private void taskButtonClicked(Task task) {

        //Information pane
        HBox informationPaneHBox = new HBox();

        //Image display
        informationPaneHBox.getChildren().add(createImageDisplayVBox(task));

        //Stage description
        informationPaneHBox.getChildren().add(createStageDescriptionVBox(task));

        //Comments section
        VBox commentSection = createCommentSection(task);

        //Put it all together in a vbox
        VBox stageVBox = new VBox();
        stageVBox.getChildren().add(informationPaneHBox);
        stageVBox.getChildren().add(commentSection);

        //The main contents of the task tab.
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(0, 18, 0, 18));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(stageVBox);

        //Adds the tab to the tab pane, if it's not already there.
        //If it's already there; view it.
        Tab existingTab = null;
        for (Tab tab : projectTabPane.getTabs()) {
            if (tab.getText().equals(task.getName())) {
                existingTab = tab;
            }
        }
        if (null == existingTab) {
            projectTabPane.getTabs().add(new Tab(task.getName(), scrollPane));
        } else {
            projectTabPane.getSelectionModel().select(existingTab);
        }
    }

    @FXML
    private void addTaskTabSelected() throws IOException {
        if (this.addTaskTab.isSelected()) {
            FXMLLoader taskLoader = new FXMLLoader(getClass().getResource("task.fxml"));
            TaskController taskController = new TaskController(this);
            Parent root = taskLoader.load();
            taskLoader.setController(taskController);

            addTaskTab.setContent(root);
        }
    }

    /**
     * Imports a project from a .csv file and adds it to the projectLib.
     */
    @FXML
    private void importProjectButtonClicked() {
        //Creates the csvManager to handle the import
        this.csvManager = new CsvManager();

        //Chooses the file to import
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Åpne ressursfil");
        File file = fileChooser.showOpenDialog(projectTabPane.getScene().getWindow());

        if (null != file) {
            //Adds the newly generated project to the project library,
            // updates the project list, and displays the new project to the user.
            try {
                Project newProject = csvManager.importCsv(file);
                projectLib.addProject(newProject);
                updateProjectListWrapper();
                projectsListView.getSelectionModel().selectLast();
                projectSelected();
                statusLabel.setText("Successfully imported " + file.getPath());
            } catch (ArrayIndexOutOfBoundsException a) {
                statusLabel.setText("Import failed: wrong format in .csv");
            }
        } else {
            statusLabel.setText("Import aborted");
        }
    }

    /**
     * Imports all .csv files in folder chosen by user to the projectLib.
     */
    @FXML
    private void importFolderButtonClicked() {
        csvManager = new CsvManager();
        //Choose the folder to import
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Velg mappe å importere");
        File importDirectory = directoryChooser.showDialog(this.statusLabel.getScene().getWindow());
        //Aborts the import if user cancels choosing a directory
        if (null == importDirectory) {
            statusLabel.setText("Import aborted");
        } else {
            //Import the projects files
            try {
                List<Project> projects = csvManager.importFolder(importDirectory);
                //If projects were successfully imported
                if (!projects.isEmpty()) {
                    projects.forEach(projectLib::addProject);
                    updateProjectListWrapper();
                    statusLabel.setText("Successfully imported " + importDirectory);
                }
            } catch (IOException e) {
                statusLabel.setText("Import failed: wrong format of file(s) in folder");
            } catch (ArrayIndexOutOfBoundsException a) {
                statusLabel.setText("Import failed: wrong format of file in folder.");
            }
        }
    }

    /**
     * Exports all projects in projectLib to .csv files in a directory chosen by user.
     * If a file with the same name as a project already exists, an overwrite confirmation is displayed.
     */
    @FXML
    private void exportProjectsButtonClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Velg plassering for fillagring");
        File saveDirectory = directoryChooser.showDialog(this.statusLabel.getScene().getWindow());
        if (null != saveDirectory) {
            csvManager.setSaveFileDirectory(saveDirectory.getAbsolutePath());
            Boolean overwriteAll = null;
            for (Project project : projectLib.getProjects().values()) {
                boolean success = false;
                if (csvManager.createCsvFile(project.getName())) {
                    success = true;
                } else {
                    if (null == overwriteAll) {
                        OverwriteConfirmationDialog ocDialog = new OverwriteConfirmationDialog(project);
                        Optional<Integer> result = ocDialog.showAndWait();

                        if (result.isPresent()) {
                            switch (result.get()) {
                                case 1:
                                    success = true;
                                    break;
                                case 3:
                                    success = true;
                                    overwriteAll = true;
                                    break;
                                case 4:
                                    success = false;
                                    overwriteAll = false;
                                    break;
                                default:
                                    success = false;
                            }
                        }
                    }
                }
                if (success || null != overwriteAll && overwriteAll) {
                    csvManager.writeToCsv(project.getProjectAsStringArrayList());
                }
            }
        }
    }

    /**
     * Displays a full size format of the image in the main image view.
     */
    @FXML
    private void mainImageButtonClicked(ImageView imageView) {
        largeImageBackground.setVisible(true);

        largeImageView.setImage(imageView.getImage());
        largeImageView.setVisible(true);
        largeImageView.fitHeightProperty().bind(mainPane.heightProperty());
        largeImageView.fitWidthProperty().bind((mainPane.widthProperty()));
    }

    /**
     * Hides the full size image view from the user.
     */
    @FXML
    private void backgroundButtonClicked() {
        largeImageBackground.setVisible(false);
    }

    /**
     * If a project is selected in the project list, it is removed from the library and from the tab pane,
     * and the list is updated.
     */
    @FXML
    private void deleteProjectButtonClicked() {
        this.projectLib.removeProject
                (this.projectsListView.getSelectionModel().getSelectedItem());
        updateProjectListWrapper();
        this.projectsListView.getSelectionModel().selectLast();
        projectSelected();
        if (this.projectLib.listProjects().isEmpty()) {
            this.projectTabPane.getTabs().clear();
        }
    }

    /**
     * Switches back to the login screen.
     *
     * @throws IOException
     */
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    /**
     * Creates an image display area to show the images in a task.
     *
     * @param task the task in question.
     * @return {@code VBox} image display area.
     */
    private VBox createImageDisplayVBox(Task task) {

        VBox imageDisplayVBox = new VBox();
        imageDisplayVBox.setPadding(new Insets(5, 0, 0, 0));

        //Create the main image view
        ImageView mainImage = new ImageView("com/snikkergutane/images/1.jpg");
        if (!task.getImageURLs().isEmpty()) {
            mainImage.setImage(new Image(task.getImageURLs().get(0)));
        }
        mainImage.setPreserveRatio(true);
        mainImage.setFitHeight(150);
        mainImage.setFitWidth(200);
        mainImage.setOnMouseClicked(e -> mainImageButtonClicked(mainImage));
        mainImage.setEffect(new DropShadow());

        BorderPane mainImagePane = new BorderPane();
        mainImagePane.setPrefSize(200,150);
        mainImagePane.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        mainImagePane.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        mainImagePane.setCenter(mainImage);

        //Create the thumbnail image view
        HBox thumbnailHBox = new HBox();
        thumbnailHBox.setPrefHeight(50);
        thumbnailHBox.setMaxHeight(50);
        thumbnailHBox.setSpacing(2);

        if (!task.getImageURLs().isEmpty()) {
            task.getImageURLs().forEach(imageURL -> {
                ImageView image = new ImageView(imageURL);
                image.setPreserveRatio(true);
                image.setFitHeight(50);
                image.setOnMouseClicked(e -> mainImage.setImage(image.getImage()));
                image.setEffect(new DropShadow());
                thumbnailHBox.getChildren().add(image);
            });
        } else {
            ImageView image = new ImageView(mainImage.getImage());
            image.setPreserveRatio(true);
            image.setFitHeight(50);
            image.setOnMouseClicked(e -> mainImage.setImage(image.getImage()));
            thumbnailHBox.getChildren().add(image);
        }

        ScrollPane thumbnailScroll = new ScrollPane();
        thumbnailScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        thumbnailScroll.setPrefSize(200, 60);
        thumbnailScroll.setMinHeight(Region.USE_PREF_SIZE);
        thumbnailScroll.setContent(thumbnailHBox);
        thumbnailScroll.setEffect(new InnerShadow());

        imageDisplayVBox.getChildren().add(mainImagePane);
        imageDisplayVBox.getChildren().add(thumbnailScroll);

        return imageDisplayVBox;
    }

    /**
     * Creates the task's description area.
     *
     * @param task the task in question.
     * @return {@code VBox} task description area.
     */
    private VBox createStageDescriptionVBox(Task task) {
        VBox stageDescriptionVBox = new VBox();
        HBox.setHgrow(stageDescriptionVBox, Priority.SOMETIMES);

        TextArea stageDescriptionArea = new TextArea(task.getDescription());
        stageDescriptionArea.setWrapText(true);
        stageDescriptionArea.setEditable(false);


        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);

        HBox datesHBox = new HBox();
        datesHBox.getChildren().add(new Label("Startdato: " + task.getStartDate()));
        datesHBox.getChildren().add(r2);
        if (task.isFinished()) {
            datesHBox.getChildren().add(new Label("Fullført dato: " + task.getEndDate()));
        } else {
            datesHBox.getChildren().add(new Label("Fullføres innen: " + task.getEndDate()));
        }

        Label taskDescriptionLabel = new Label("Arbeidsbeskrivelse:");
        taskDescriptionLabel.setFont(new Font("System Bold", 12.0));

        stageDescriptionVBox.setPadding(new Insets(0, 0, 0, 20));
        stageDescriptionVBox.getChildren().add(taskDescriptionLabel);
        stageDescriptionVBox.getChildren().add(stageDescriptionArea);
        stageDescriptionVBox.getChildren().add(datesHBox);

        return stageDescriptionVBox;
    }

    /**
     * Creates a task's comment section.
     *
     * @param task the task in question.
     * @return {@code VBox} the task's comment section.
     */
    private VBox createCommentSection(Task task) {
        //Create the table to display all comments in

        //Define the columns
        //The Date column
        TableColumn<Comment, LocalDate> dateTableColumn = new TableColumn<>("Date");
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateTableColumn.setMinWidth(75);
        dateTableColumn.setMaxWidth(100);

        //The User column
        TableColumn<Comment, String> userNameColumn = new TableColumn<>("User");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        userNameColumn.setMinWidth(100);
        userNameColumn.setMaxWidth(500);

        //The Comment column
        TableColumn<Comment, String> commentColumn = new TableColumn<>("Comment");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("commentText"));

        TableColumn<Comment, Image> imageColumn = new TableColumn<>("Image");
        imageColumn.setMinWidth(50);
        imageColumn.setMaxWidth(500);
        imageColumn.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(30);
            imageview.setFitWidth(50);

            //Set up the Table cell
            TableCell<Comment, Image> cell = new TableCell<>() {
                @Override
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            //Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableView<Comment> commentsTableView = new TableView<>();
        commentsTableView.getColumns().addAll(dateTableColumn, userNameColumn, commentColumn, imageColumn);
        commentsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        commentsTableView.setItems(FXCollections.observableArrayList(task.getComments()));
        commentsTableView.setPrefHeight(1000);


        //Add comment button
        ImageView plusIcon = new ImageView("/com/snikkergutane/Icons/plus-icon.png");
        plusIcon.setFitHeight(20);
        plusIcon.setFitWidth(20);

        Button addCommentButton = new Button("Legg til kommentar");
        addCommentButton.setGraphic(plusIcon);
        addCommentButton.setOnAction(e -> addCommentButtonClicked(task));

        //Edit Comment Button
        ImageView gearIcon = new ImageView("/com/snikkergutane/Icons/gear-icon.png");
        gearIcon.setPreserveRatio(true);
        gearIcon.setFitWidth(20);
        gearIcon.setFitHeight(20);

        Button editSelectedCommentButton = new Button("Rediger valgt kommentar");
        editSelectedCommentButton.setGraphic(gearIcon);
        editSelectedCommentButton.setOnAction(e -> editSelectedCommentButtonClicked(task, commentsTableView));

        //Delete comment button
        ImageView minusIcon = new ImageView("/com/snikkergutane/Icons/minus-icon.png");
        minusIcon.setFitHeight(20);
        minusIcon.setFitWidth(20);

        Button removeCommentButton = new Button("Fjern kommentar");
        removeCommentButton.setGraphic(minusIcon);
        removeCommentButton.setOnAction(e -> removeSelectedCommentButtonClicked(task, commentsTableView));

        HBox commentButtonsHBox = new HBox();
        commentButtonsHBox.setSpacing(10);

        commentButtonsHBox.getChildren().addAll(addCommentButton, editSelectedCommentButton, removeCommentButton);


        Label commentSectionLabel = new Label("Kommentarer:");
        commentSectionLabel.setFont(new Font("System Bold", 12.0));

        VBox commentSection = new VBox();
        commentSection.setPadding(new Insets(25, 0, 40, 0));

        commentSection.getChildren().add(commentSectionLabel);
        commentSection.getChildren().add(commentsTableView);
        commentSection.getChildren().add(commentButtonsHBox);

        return commentSection;
    }

    private void removeSelectedCommentButtonClicked(Task task, TableView commentsTableview) {
        Comment commentSelected = (Comment) commentsTableview.getSelectionModel().getSelectedItem();
        if (commentSelected == null) {
            showPleaseSelectItemDialog();
        } else {
            if (showDeleteConfirmationDialog()) {
                task.removeComment(commentSelected);
            }

        }
    }


    private void editSelectedCommentButtonClicked(Task task, TableView commentsTableView) {
        Comment comment = (Comment) commentsTableView.getSelectionModel().getSelectedItem();
        if (comment == null) {
            showPleaseSelectItemDialog();
        } else {

            CommentDialog commentDialog = new CommentDialog(comment, true);
            Optional<Comment> result = commentDialog.showAndWait();
            if (result.isPresent()) {
                task.addComment(result.get());

            }


        }


    }

    @FXML
    private void addCommentButtonClicked(Task task) {
        CommentDialog commentDialog = new CommentDialog();
        Optional<Comment> result = commentDialog.showAndWait();
        if (result.isPresent()) {
            Comment newComment = result.get();
            task.addComment(newComment);
        }
    }

    /**
     * Updates the project list view.
     */
    private void updateProjectListWrapper() {
        this.projectListWrapper.setAll(this.projectLib.listProjects());
    }

    /**
     * Opens a new project dialog where the user can create a new project.
     */
    @FXML
    private void addProjectButton() {
        ProjectDialog projectDialog = new ProjectDialog();

        Optional<Project> result = projectDialog.showAndWait();

        if (result.isPresent()) {
            Project newProject = result.get();
            this.projectLib.addProject(newProject);
        }
    }

    private void editProject(Project project) {
        projectLib.removeProject(project.getName());

        ProjectDialog projectDialog = new ProjectDialog(project, true);
        Optional<Project> result = projectDialog.showAndWait();
        if (result.isPresent()) {
            projectLib.addProject(result.get());
        }
        updateProjectListWrapper();
    }

    @FXML
    private void editProjectButtonClicked() {
        Project project = this.projectLib.getProject(this.projectsListView.getSelectionModel().getSelectedItem());
        if (null != project) {
            editProject(project);
        } else {
            statusLabel.setText("Error: choose a project from the list");
        }
    }

    public void addTask(Task task) {
        projectLib.getProject(projectsListView.getSelectionModel().getSelectedItem()).addTask(task);
        projectSelected();
        projectTabPane.getSelectionModel().select(projectTabPane.getTabs().size()-2);
    }

    public void showPleaseSelectItemDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information");
        alert.setHeaderText("No item selected");
        alert.setContentText("No item selected.\n"
                + "Please select an item from the table.");

        alert.showAndWait();
    }

    public boolean showDeleteConfirmationDialog() {
        boolean deleteConfirmed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Delete confirmation");
        alert.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            deleteConfirmed = (result.get() == ButtonType.OK);
        }
        return deleteConfirmed;

    }
}