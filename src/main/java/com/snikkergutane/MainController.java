package com.snikkergutane;

import java.io.IOException;

import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import com.snikkergutane.project.Stage;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainController {

    private final ProjectLib projectLib = new ProjectLib();
    @FXML private Button backgroundButton;
    @FXML private ImageView largeImageView;
    @FXML private ListView<String> projectsListView;
    @FXML private Button newProjectButton;
    @FXML private Button editProjectButton;
    @FXML private ScrollPane projectInfoScrollPane;
    @FXML private VBox projectInfoVBox;
    @FXML private GridPane gridPane1;
    @FXML private GridPane gridPane2;
    @FXML private GridPane gridPane3;
    @FXML private GridPane gridPane4;
    @FXML private GridPane gridPane5;
    @FXML private GridPane gridPane6;
    @FXML private GridPane gridPane7;
    @FXML private GridPane gridPane8;
    @FXML private GridPane stageListPane;
    @FXML private HBox pdfHBox;
    @FXML private Label kundeLabel;
    @FXML private Label stedLabel;
    @FXML private Label telefonLabel;
    @FXML private Label epostLabel;
    @FXML private Label oppstartsdatoLabel;
    @FXML private Label arbeidsbeskrivelseLabel;
    @FXML private Label kommentarLabel;
    @FXML private Button deleteProjectButton;
    @FXML private Button exportPDFButton;
    @FXML private Label customerNameLabel;
    @FXML private Label projectAddressLabel;
    @FXML private Label customerPhoneNumberLabel;
    @FXML private Label customerEmailLabel;
    @FXML private Label projectStartDateLabel;
    @FXML private TextArea projectDescriptionTextArea;
    @FXML private Button editCurrentProjectButton;
    @FXML private TabPane projectPane;

    @FXML
    private void initialize() {
        projectLib.loadDemoProject();
        projectLib.listProjects().forEach(p -> projectsListView.getItems().add(p));
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

            stageListPane.getChildren().clear();
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

                stageListPane.add(button, 0, y);

                if (stage.isFinished()) {
                    stageListPane.add(finishedImage, 1, y);
                } else {
                    stageListPane.add(unfinishedImage, 1, y);
                }
                y++;
            }

            projectPane.getTabs().clear();
            projectPane.getTabs().add(new Tab("Project Info", projectInfoScrollPane));
        }
    }

    @FXML
    private void stageButtonClicked(Stage stage) {

        HBox stageHBox = new HBox();

        //Information pane
        HBox informationPaneHBox = new HBox();

        //Image display
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
        String finalImageUrl = imageUrl;
        mainImageButton.setOnAction(e -> mainImageButtonClicked(finalImageUrl));

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
                    mainImageButton.setOnAction(a -> mainImageButtonClicked(imageURL));
                });
                thumbnailPane.getChildren().add(button);
            });
        }

        ScrollPane thumbnailScroll = new ScrollPane();
        thumbnailScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        thumbnailScroll.setPrefSize(200,60);
        thumbnailScroll.setMinHeight(Region.USE_PREF_SIZE);
        thumbnailScroll.setContent(thumbnailPane);

        imageDisplayVBox.getChildren().add(mainImageButton);
        imageDisplayVBox.getChildren().add(thumbnailScroll);

        //Stage description
        VBox stageDescriptionVBox = new VBox();

        VBox vBox4 = new VBox();

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

        vBox4.setPadding(new Insets(0,0,0,20));
        vBox4.getChildren().add(new Label("Arbeidsbeskrivelse:"));
        vBox4.getChildren().add(stageDescriptionArea);
        vBox4.getChildren().add(datesHBox);

        stageDescriptionVBox.getChildren().add(vBox4);


        informationPaneHBox.getChildren().add(imageDisplayVBox);
        informationPaneHBox.getChildren().add(stageDescriptionVBox);

        //TODO: Comment section
        GridPane gridPane1 = new GridPane();


        VBox vBox1 = new VBox();
        vBox1.getChildren().add(informationPaneHBox);
        vBox1.getChildren().add(new Label("Kommentarer:"));
        vBox1.getChildren().add(gridPane1);


        stageHBox.getChildren().add(vBox1);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(0,18,0,18));
        scrollPane.setContent(stageHBox);

        projectPane.getTabs().add(new Tab(stage.getName(), scrollPane));
    }

    @FXML
    private void addProjectButtonClicked() {
        try {
            App.newWindow("addProject");
        } catch (Exception e) {

        }
    }

    @FXML
    private void mainImageButtonClicked(String url) {
        backgroundButton.setVisible(true);
        largeImageView.setImage(new Image(url));
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


}