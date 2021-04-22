package com.snikkergutane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.snikkergutane.project.CsvManager;
import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import com.snikkergutane.project.Task;
import com.snikkergutane.project.task.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MainController {

    private CsvManager csvManager;
    private final ProjectLib projectLib = new ProjectLib();
    private ObservableList<String> projectListWrapper;
    @FXML Label statusLabel;
    @FXML private Button backgroundButton;
    @FXML private ImageView largeImageView;
    @FXML private ImageView mainImageView;
    @FXML private ListView<String> projectsListView;
    @FXML private ScrollPane projectInfoScrollPane;
    @FXML private GridPane taskListGridPane;
    @FXML private Label customerNameLabel;
    @FXML private Label projectAddressLabel;
    @FXML private Label customerPhoneNumberLabel;
    @FXML private Label customerEmailLabel;
    @FXML private Label projectStartDateLabel;
    @FXML private TextArea projectDescriptionTextArea;
    @FXML private TabPane projectTabPane;

    @FXML
    private void initialize() {
        projectLib.loadDemoProject();
        projectListWrapper =FXCollections.observableArrayList
                (this.projectLib.listProjects());
        projectsListView.setItems(projectListWrapper);
    }

    @FXML
    private void projectSelected() {
        if (projectsListView.getSelectionModel().getSelectedItem() != null) {
            Project selectedProject = projectLib.getProject(projectsListView.getSelectionModel().getSelectedItem());

            customerNameLabel.setText(selectedProject.getCustomerName());
            customerEmailLabel.setText(selectedProject.getCustomerEmail());
            customerPhoneNumberLabel.setText(selectedProject.getCustomerPhoneNumber());
            projectAddressLabel.setText(selectedProject.getAddress());
            projectStartDateLabel.setText("" + selectedProject.getStartDate());
            projectDescriptionTextArea.setText(selectedProject.getDescription());

            taskListGridPane.getChildren().clear();
            int y = 0;


            for (Task task : selectedProject.getTasks()) {
                Button button = new Button(task.getName());
                button.setBackground(Background.EMPTY);
                button.setOnAction(e -> stageButtonClicked(task));

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

            projectTabPane.getTabs().clear();
            projectTabPane.getTabs().add(new Tab("Project Info", projectInfoScrollPane));
        }
    }

    @FXML
    private void stageButtonClicked(Task task) {

        VBox stageVBox = new VBox();

        //Information pane
        HBox informationPaneHBox = new HBox();

        //Image display
        informationPaneHBox.getChildren().add(createImageDisplayVBox(task));

        //Stage description
        informationPaneHBox.getChildren().add(createStageDescriptionVBox(task));

        //Comments section
        VBox commentSection = createCommentSection(task);

        //Put it all together
        stageVBox.getChildren().add(informationPaneHBox);
        stageVBox.getChildren().add(commentSection);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(0,18,0,18));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(stageVBox);


        projectTabPane.getTabs().add(new Tab(task.getName(), scrollPane));
    }

    @FXML
    private void addProjectButtonClicked() {
        Optional<Project> result = new AddProjectController(projectLib).showAndWait();
        result.ifPresent(this.projectLib::addProject);
    }

    @FXML
    private void importProjectButtonClicked() {
        this.csvManager = new CsvManager(projectTabPane.getScene().getWindow());
        List<List<String>> records = csvManager.importCsv();
        try {
            if (!records.isEmpty() && records.size() > 1) {
                List<String> projectInfo = records.get(0);
                Project project = new Project(projectInfo.get(0), projectInfo.get(1),
                        projectInfo.get(2), projectInfo.get(3), projectInfo.get(4),
                        LocalDate.parse(projectInfo.get(5)), LocalDate.parse(projectInfo.get(6)), projectInfo.get(7));
                records.remove(0);

                List<Comment> commentList = new ArrayList<>();
                records.forEach(line -> {
                    if (line.get(0).startsWith("+")) {
                        String name = line.get(0).replace("+", "");
                        LocalDate startDate = LocalDate.parse(line.get(1));
                        LocalDate endDate = LocalDate.parse(line.get(2));
                        String description = line.get(3);

                        ArrayList<String> strings = new ArrayList<>(line);
                        int numberOfImages = line.size() - 4;
                        while (strings.size() > numberOfImages) {
                            strings.remove(0);
                        }

                        Task task = new Task(name, startDate, endDate, description, strings);
                        commentList.forEach(task::addComment);
                        commentList.clear();
                        project.addTask(task);
                    } else {
                        LocalDate date = LocalDate.parse(line.get(0));
                        String user = line.get(1);
                        String commentText = line.get(2);

                        ArrayList<String> strings = new ArrayList<>(line);
                        int numberOfImages = line.size() - 3;
                        while (strings.size() > numberOfImages) {
                            strings.remove(0);
                        }

                        commentList.add(new Comment(date, user, commentText, strings.get(0)));
                    }
                });
                projectLib.addProject(project);
                updateProjectListWrapper();
                projectsListView.getSelectionModel().selectLast();
                projectSelected();
                statusLabel.setText("Import successful");
            }
        } catch (Exception exception) {
            statusLabel.setText("ERROR: Invalid format in .csv");
        }
    }

    @FXML
    private void mainImageButtonClicked() {
        backgroundButton.setVisible(true);
        largeImageView.setImage(new Image(mainImageView.getImage().getUrl()));
        largeImageView.setVisible(true);
    }

    @FXML
    private void backgroundButtonClicked() {
        backgroundButton.setVisible(false);
        largeImageView.setVisible(false);
    }

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

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    private VBox createImageDisplayVBox(Task task) {
        VBox imageDisplayVBox = new VBox();
        imageDisplayVBox.setPadding(new Insets(5,0,0,0));

        Button mainImageButton = new Button("");
        String imageUrl = "images/1.jpg";
        if (!task.getImageURLs().isEmpty()) {
            imageUrl = task.getImageURLs().get(0);
        }
        ImageView mainImage = new ImageView(imageUrl);
        mainImage.setFitHeight(150);
        mainImage.setFitWidth(200);
        mainImageButton.setGraphic(mainImage);
        mainImageButton.setOnAction(e -> mainImageButtonClicked());

        FlowPane thumbnailPane = new FlowPane();
        thumbnailPane.setPrefHeight(50);
        thumbnailPane.setMaxHeight(50);

        if (!task.getImageURLs().isEmpty()) {
            task.getImageURLs().forEach(imageURL -> {
                ImageView image = new ImageView(imageURL);
                image.setPreserveRatio(true);
                image.setFitHeight(50);
                image.setOnMouseClicked(e -> {
                    ImageView largeImage = new ImageView(imageURL);
                    largeImage.setPreserveRatio(true);
                    largeImage.setFitWidth(200);
                    largeImage.setFitHeight(150);
                    mainImageButton.setGraphic(largeImage);
                    mainImageButton.setOnAction(a -> mainImageButtonClicked());
                });
                thumbnailPane.getChildren().add(image);
            });
        }

        ScrollPane thumbnailScroll = new ScrollPane();
        thumbnailScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        thumbnailScroll.setPrefSize(200,60);
        thumbnailScroll.setMinHeight(Region.USE_PREF_SIZE);
        thumbnailScroll.setContent(thumbnailPane);

        imageDisplayVBox.getChildren().add(mainImage);
        imageDisplayVBox.getChildren().add(thumbnailScroll);

        return imageDisplayVBox;
    }

    private VBox createStageDescriptionVBox(Task task) {
        VBox stageDescriptionVBox = new VBox();
        HBox.setHgrow(stageDescriptionVBox, Priority.SOMETIMES);

        TextArea stageDescriptionArea = new TextArea(task.getDescription());
        stageDescriptionArea.setWrapText(true);
        stageDescriptionArea.setEditable(false);


        Region r2 = new Region();
        HBox.setHgrow(r2,Priority.ALWAYS);

        HBox datesHBox = new HBox();
        datesHBox.getChildren().add(new Label("Startdato: " + task.getStartDate()));
        datesHBox.getChildren().add(r2);
        if (task.isFinished()) {
            datesHBox.getChildren().add(new Label("Fullført dato: " + task.getEndDate()));
        }
        else {
            datesHBox.getChildren().add(new Label("Fullføres innen: " + task.getEndDate()));
        }

        Label taskDescriptionLabel = new Label("Arbeidsbeskrivelse:");
        taskDescriptionLabel.setFont(new Font("System Bold", 12.0));

        stageDescriptionVBox.setPadding(new Insets(0,0,0,20));
        stageDescriptionVBox.getChildren().add(taskDescriptionLabel);
        stageDescriptionVBox.getChildren().add(stageDescriptionArea);
        stageDescriptionVBox.getChildren().add(datesHBox);

        return stageDescriptionVBox;
    }

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


        //Edit Comment Button
        ImageView gearIcon = new ImageView("/com/snikkergutane/Icons/gear-icon.png");
        gearIcon.setPreserveRatio(true);
        gearIcon.setFitWidth(20);
        gearIcon.setFitHeight(20);

        Button editSelectedCommentButton = new Button("Rediger valgt kommentar");
        editSelectedCommentButton.setGraphic(gearIcon);

        Label commentSectionLabel = new Label("Kommentarer:");
        commentSectionLabel.setFont(new Font("System Bold", 12.0));

        VBox commentSection = new VBox();
        commentSection.setPadding(new Insets(25,0,40,0));

        commentSection.getChildren().add(commentSectionLabel);
        commentSection.getChildren().add(commentsTableView);
        commentSection.getChildren().add(editSelectedCommentButton);

        return commentSection;
    }
    private void updateProjectListWrapper() {
        this.projectListWrapper.setAll(this.projectLib.listProjects());
    }
}