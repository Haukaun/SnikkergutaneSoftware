package com.snikkergutane;

import java.io.IOException;

import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import com.snikkergutane.project.Stage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainController {

    private final ProjectLib projectLib = new ProjectLib();
    public Button mainImageButton;
    public Button backgroundButton;
    public ImageView largeImageView;
    public ListView<String> projectsListView;
    public Button newProjectButton;
    public Button editProjectButton;
    public ScrollPane projectInfoScrollPane;
    public VBox projectInfoVBox;
    public GridPane gridPane1;
    public GridPane gridPane2;
    public GridPane gridPane3;
    public GridPane gridPane4;
    public GridPane gridPane5;
    public GridPane gridPane6;
    public GridPane gridPane7;
    public GridPane gridPane8;
    public GridPane stageListPane;
    public HBox pdfHBox;
    public Label kundeLabel;
    public Label stedLabel;
    public Label telefonLabel;
    public Label epostLabel;
    public Label oppstartsdatoLabel;
    public Label arbeidsbeskrivelseLabel;
    public Label kommentarLabel;
    public Button deleteProjectButton;
    public Button exportPDFButton;
    public Label customerNameLabel;
    public Label projectAddressLabel;
    public Label customerPhoneNumberLabel;
    public Label customerEmailLabel;
    public Label projectStartDateLabel;
    public TextArea projectDescriptionTextArea;
    public Button editCurrentProjectButton;
    public TabPane projectPane;

    @FXML
    private void initialize() {
        projectLib.loadDemoProject();
        projectLib.listProjects().forEach(p -> projectsListView.getItems().add(p));
    }

    @FXML
    private void projectSelected() {
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

            stageListPane.add(button,0,y);

            if (stage.isFinished()) {
                stageListPane.add(finishedImage,1,y);
            }
            else {
                stageListPane.add(unfinishedImage,1,y);
            }
            y++;
        }

        projectPane.getTabs().clear();
        projectPane.getTabs().add(new Tab("Project Info", projectInfoScrollPane));
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
        if (stage.getImageURLs().size() > 0) {
            imageUrl = stage.getImageURLs().get(0);
        }
        mainImageButton.setGraphic(new ImageView(imageUrl));
        String finalImageUrl = imageUrl;
        mainImageButton.setOnAction(e -> mainImageButtonClicked(finalImageUrl));

        FlowPane thumbnailPane = new FlowPane();
        thumbnailPane.setPrefHeight(50);
        thumbnailPane.setMaxHeight(50);

        if (stage.getImageURLs().size() > 0) {
            stage.getImageURLs().forEach(imageURL -> {
                Button button = new Button("");
                ImageView image = new ImageView(imageURL);
                image.setPreserveRatio(true);
                image.setFitHeight(50);
                button.setGraphic(image);
                ImageView mainImage = new ImageView(imageURL);
                mainImage.setPreserveRatio(true);
                mainImage.setFitHeight(150);
                mainImage.setFitWidth(200);
                button.setOnAction(e -> mainImageButton.setGraphic(mainImage));
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

        vBox4.getChildren().add(stageDescriptionArea);
        vBox4.getChildren().add(datesHBox);

        stageDescriptionVBox.getChildren().add(new Label("Arbeidsbeskrivelse:"));
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
        scrollPane.setContent(stageHBox);

        projectPane.getTabs().add(new Tab(stage.getName(), scrollPane));
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