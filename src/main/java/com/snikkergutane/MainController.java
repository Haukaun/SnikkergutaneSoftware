package com.snikkergutane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.snikkergutane.project.CsvManager;
import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import com.snikkergutane.project.Stage;
import com.snikkergutane.project.task.Comment;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainController {

    private CsvManager csvManager;
    private final ProjectLib projectLib = new ProjectLib();
    @FXML Label statusLabel;
    @FXML private Button backgroundButton;
    @FXML private ImageView largeImageView;
    @FXML private ImageView mainImageView;
    @FXML private ListView<String> projectsListView;
    @FXML private ScrollPane projectInfoScrollPane;
    @FXML private GridPane stageListGridPane;
    @FXML private Label commentLabel;
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
        projectsListView.getItems().addAll(projectLib.listProjects());
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

            stageListGridPane.getChildren().clear();
            int y = 0;
            ImageView finishedImage = new ImageView("com/snikkergutane/images/person.png");
            finishedImage.setPreserveRatio(true);
            finishedImage.setFitHeight(30);
            ImageView unfinishedImage = new ImageView("com/snikkergutane/images/key.png");
            unfinishedImage.setPreserveRatio(true);
            unfinishedImage.setFitHeight(30);
            for (Stage stage : selectedProject.getStages()) {
                Button button = new Button(stage.getName());
                button.setBackground(Background.EMPTY);
                button.setOnAction(e -> stageButtonClicked(stage));

                stageListGridPane.add(button, 0, y);

                if (stage.isFinished()) {
                    stageListGridPane.add(finishedImage, 1, y);
                } else {
                    stageListGridPane.add(unfinishedImage, 1, y);
                }
                y++;
            }

            projectTabPane.getTabs().clear();
            projectTabPane.getTabs().add(new Tab("Project Info", projectInfoScrollPane));
        }
    }

    @FXML
    private void stageButtonClicked(Stage stage) {

        HBox stageHBox = new HBox();

        //Information pane
        HBox informationPaneHBox = new HBox();

        //Image display
        VBox imageDisplayVBox = createImageDisplayVBox(stage);
        //Stage description
        VBox stageDescriptionVBox = createStageDescriptionVBox(stage);

        informationPaneHBox.getChildren().add(imageDisplayVBox);
        informationPaneHBox.getChildren().add(stageDescriptionVBox);

        //TODO: Comment section
        //GridPane commentSectionGridPane = createCommentSectionGridPane();




        VBox vBox1 = new VBox();
        vBox1.getChildren().add(informationPaneHBox);
        vBox1.getChildren().add(commentLabel);
        //vBox1.getChildren().add(commentSectionGridPane);


        stageHBox.getChildren().add(vBox1);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(0,18,0,18));
        scrollPane.setContent(stageHBox);

        projectTabPane.getTabs().add(new Tab(stage.getName(), scrollPane));
    }

    @FXML
    private void addProjectButtonClicked() {
        Optional<Project> result = new AddProjectController(projectLib).showAndWait();
        result.ifPresent(this.projectLib::addProject);
    }

    @FXML
    private void importProjectButtonClicked() {
        this.csvManager = new CsvManager(projectTabPane.getScene().getWindow());
        ArrayList<List<String>> records = csvManager.importCsv();
        if (!records.isEmpty() && records.size() > 1) {
            List<String> projectInfo = records.get(0);
            Project project = new Project(projectInfo.get(0), projectInfo.get(1),
                    projectInfo.get(2),projectInfo.get(3),projectInfo.get(4),
                    LocalDate.parse(projectInfo.get(5)),LocalDate.parse(projectInfo.get(6)),projectInfo.get(7));
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

                    Stage stage = new Stage(name, startDate, endDate, description, strings);
                    commentList.forEach(stage::addComment);
                    commentList.clear();
                    project.addStage(stage);
                } else {
                    LocalDate date = LocalDate.parse(line.get(0));
                    String user = line.get(1);
                    String commentText = line.get(2);

                    ArrayList<String> strings = new ArrayList<>(line);
                    int numberOfImages = line.size() - 3;
                    while (strings.size() > numberOfImages) {
                        strings.remove(0);
                    }

                    commentList.add(new Comment(date, user, commentText, strings));
                }
            });
            projectLib.addProject(project);
            projectsListView.getItems().clear();
            projectsListView.getItems().addAll(projectLib.listProjects());
            projectsListView.getSelectionModel().selectLast();
            projectSelected();
            statusLabel.setText("Import successful");
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
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    private VBox createImageDisplayVBox(Stage stage) {
        VBox imageDisplayVBox = new VBox();

        Button mainImageButton = new Button("");
        String imageUrl = "images/1.jpg";
        if (!stage.getImageURLs().isEmpty()) {
            imageUrl = stage.getImageURLs().get(0);
        }
        ImageView mainImage = new ImageView(imageUrl);
        mainImage.setFitHeight(150);
        mainImage.setFitWidth(200);
        mainImageButton.setGraphic(mainImage);
        mainImageButton.setOnAction(e -> mainImageButtonClicked());

        FlowPane thumbnailPane = new FlowPane();
        thumbnailPane.setPrefHeight(50);
        thumbnailPane.setMaxHeight(50);

        if (!stage.getImageURLs().isEmpty()) {
            stage.getImageURLs().forEach(imageURL -> {
                Button button = new Button("");
                ImageView image = new ImageView(imageURL);
                image.setPreserveRatio(true);
                image.setFitHeight(50);
                button.setGraphic(image);
                button.setOnAction(e -> {
                    ImageView largeImage = new ImageView(imageURL);
                    largeImage.setPreserveRatio(true);
                    largeImage.setFitWidth(200);
                    largeImage.setFitHeight(150);
                    mainImageButton.setGraphic(largeImage);
                    mainImageButton.setOnAction(a -> mainImageButtonClicked());
                });
                thumbnailPane.getChildren().add(button);
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

    private VBox createStageDescriptionVBox(Stage stage) {
        VBox stageDescriptionVBox = new VBox();

        TextArea stageDescriptionArea = new TextArea(stage.getDescription());
        stageDescriptionArea.setWrapText(true);
        stageDescriptionArea.setEditable(false);

        Region r2 = new Region();
        r2.setMinWidth(0);
        r2.setPrefHeight(0);
        r2.setPrefWidth(500);

        HBox datesHBox = new HBox();
        datesHBox.getChildren().add(new Label("Startdato: " + stage.getStartDate()));
        datesHBox.getChildren().add(r2);
        if (stage.isFinished()) {
            datesHBox.getChildren().add(new Label("Fullført dato: " + stage.getEndDate()));
        }
        else {
            datesHBox.getChildren().add(new Label("Fullføres innen: " + stage.getEndDate()));
        }

        stageDescriptionVBox.setPadding(new Insets(0,0,0,20));
        stageDescriptionVBox.getChildren().add(new Label("Arbeidsbeskrivelse:"));
        stageDescriptionVBox.getChildren().add(stageDescriptionArea);
        stageDescriptionVBox.getChildren().add(datesHBox);

        return stageDescriptionVBox;
    }
}