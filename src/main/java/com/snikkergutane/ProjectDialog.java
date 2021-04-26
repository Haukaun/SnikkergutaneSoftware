package com.snikkergutane;

import com.snikkergutane.project.Project;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class ProjectDialog extends Dialog<Project> {

    private final Mode mode;

    public ProjectDialog() {
        super();
        this.mode = Mode.NEW;
        showContent();
    }

    public enum Mode{
        NEW, EDIT, INFO
    }


    private Project existingProject= null;


    public ProjectDialog (Project project, boolean editable){
        super();

        if (editable){
            this.mode = Mode.EDIT;
        } else {
            this.mode = Mode.INFO;
        }

        this.existingProject = project;
        showContent();
    }

    private void showContent(){

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/snikkergutane/images/logoSG .png").toExternalForm()));
        stage.setTitle("Prosjekt Håndtering");

        ButtonType okButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        getDialogPane().setGraphic(new ImageView(getClass().getResource("/com/snikkergutane/images/SnikkergutaneLogo.png").toExternalForm()));
        getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 50, 10, 10));


        TextField projectName = new TextField();
        projectName.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        projectName.setPromptText("ProsjektNavn");

        TextField customerName = new TextField();
        customerName.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        customerName.setPromptText("KundeNavn");

        TextField customerEmail = new TextField();
        customerEmail.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        customerEmail.setPromptText("KundeEmail");

        TextField customerPhoneNumber = new TextField();
        customerPhoneNumber.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        customerPhoneNumber.setPromptText("KundeNummer");

        TextField Adress = new TextField();
        Adress.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Adress.setPromptText("Adresse");

        DatePicker datePicker = new DatePicker();
        datePicker.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        datePicker.setPromptText("Oppstartsdato");


        if((mode == Mode.EDIT) || (mode == Mode.INFO)){

            projectName.setText(existingProject.getName());
            customerName.setText(existingProject.getCustomerName());
            customerEmail.setText(existingProject.getCustomerEmail());
            customerPhoneNumber.setText(existingProject.getCustomerPhoneNumber());
            Adress.setText(existingProject.getAddress());


            if(mode == Mode.INFO){
                projectName.setEditable(false);
                customerName.setEditable(false);
                customerEmail.setEditable(false);
                customerPhoneNumber.setEditable(false);
                Adress.setEditable(false);
            }

        }

        grid.add(new Label("ProsjektNavn:"), 1, 0);
        grid.add(projectName, 2, 0 );

        grid.add(new Label("KundeNavn:"), 1, 1);
        grid.add(customerName, 2, 1);

        grid.add(new Label("KundeEmail:"), 1, 2);
        grid.add(customerEmail, 2, 2);

        grid.add(new Label("KundeNummer:"), 1, 3);
        grid.add(customerPhoneNumber, 2, 3);

        grid.add(new Label("Adresse:"), 1, 4);
        grid.add(Adress,2,4);

        grid.add(new Label("Oppstartsdato:"), 1 , 5);
        grid.add(datePicker, 2,5);


        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        Adress.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        getDialogPane().setContent(grid);



        setResultConverter(
                (ButtonType button) -> {
                    Project result = null;
                    if (button == okButtonType) {
                        if (mode == Mode.NEW) {
                            result = new Project(projectName.getText(), customerName.getText(), customerEmail.getText(), customerPhoneNumber.getText(), Adress.getText(), datePicker.getValue());
                        } else if (mode == Mode.EDIT) {
                            existingProject.setName(projectName.getText());
                            existingProject.setCustomerName(customerName.getText());
                            existingProject.setCustomerEmail(customerEmail.getText());
                            existingProject.setCustomerPhoneNumber(customerPhoneNumber.getText());
                            existingProject.setAddress(Adress.getText());
                            existingProject.setStartDate(datePicker.getValue());
                            result = existingProject;
                        }
                    }
                    return result;
                }
        );

    }


}