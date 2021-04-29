package com.snikkergutane;

import com.snikkergutane.project.Project;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class ProjectDialog extends Dialog<Project> {

    private final Mode mode;

    private ContextMenu projectNameValidator;
    private ContextMenu customerNameValidator;
    private ContextMenu customerEmailValidator;
    private ContextMenu customerNumberValidator;
    private ContextMenu customerAddressValidator;
    private ContextMenu dateValidator;

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
        customerPhoneNumber.setPromptText("MobilNummer");

        TextField address = new TextField();
        address.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        address.setPromptText("Adresse");

        DatePicker datePicker = new DatePicker();
        datePicker.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        datePicker.setPromptText("Oppstartsdato");
        datePicker.setEditable(false);


        if((mode == Mode.EDIT) || (mode == Mode.INFO)){

            projectName.setText(existingProject.getName());
            customerName.setText(existingProject.getCustomerName());
            customerEmail.setText(existingProject.getCustomerEmail());
            customerPhoneNumber.setText(existingProject.getCustomerPhoneNumber());
            address.setText(existingProject.getAddress());
            datePicker.setValue(existingProject.getStartDate());


            if(mode == Mode.INFO){
                projectName.setEditable(false);
                customerName.setEditable(false);
                customerEmail.setEditable(false);
                customerPhoneNumber.setEditable(false);
                address.setEditable(false);
                datePicker.setEditable(false);
            }
        }

        grid.add(new Label("ProsjektNavn:"), 1, 0);
        grid.add(projectName, 2, 0 );

        grid.add(new Label("KundeNavn:"), 1, 1);
        grid.add(customerName, 2, 1);

        grid.add(new Label("KundeEmail:"), 1, 2);
        grid.add(customerEmail, 2, 2);

        grid.add(new Label("MobilNummer:"), 1, 3);
        grid.add(customerPhoneNumber, 2, 3);

        grid.add(new Label("Adresse:"), 1, 4);
        grid.add(address,2,4);

        grid.add(new Label("Oppstartsdato:"), 1 , 5);
        grid.add(datePicker, 2,5);



        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        projectName.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        customerName.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        customerEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        address.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        customerPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });


        grid.getStylesheets().add(getClass()
                .getResource("/com/snikkergutane/task.css")
                .toExternalForm());

        //Creates an error message to display to the user.
        projectNameValidator = new ContextMenu();
        projectNameValidator.setAutoHide(false);
        projectNameValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven et ProsjektNavn!"));

        //Adds a listener to show error message if the TextField is left empty
        projectName.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (projectName.getText().isBlank()) {
                            projectNameValidator.show(projectName, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            projectNameValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        customerNameValidator = new ContextMenu();
        customerNameValidator.setAutoHide(false);
        customerNameValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven et KundeNavn!"));

        //Adds a listener to show error message if the TextField is left empty
        customerName.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (customerName.getText().isBlank()) {
                            customerNameValidator.show(customerName, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            customerNameValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        customerEmailValidator = new ContextMenu();
        customerEmailValidator.setAutoHide(false);
        customerEmailValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven en Email!"));

        //Adds a listener to show error message if the TextField is left empty
        customerEmail.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (customerEmail.getText().isBlank()) {
                            customerEmailValidator.show(customerEmail, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            customerEmailValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        customerNumberValidator = new ContextMenu();
        customerNumberValidator.setAutoHide(false);
        customerNumberValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven et Mobilnummer!"));

        //Adds a listener to show error message if the TextField is left empty
        customerPhoneNumber.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (customerPhoneNumber.getText().isBlank()) {
                            customerNumberValidator.show(customerPhoneNumber, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            customerNumberValidator.hide();
                        }
                    }
                });


        //Creates an error message to display to the user.
        customerAddressValidator = new ContextMenu();
        customerAddressValidator.setAutoHide(false);
        customerAddressValidator.getItems().add(
                new MenuItem("Vennligst gi oppgaven en Addresse"));

        //Adds a listener to show error message if the TextField is left empty
        address.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if newTaskNameTextField is empty
                        if (address.getText().isBlank()) {
                            customerAddressValidator.show(address, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            customerAddressValidator.hide();
                        }
                    }
                });

        //Creates an error message to display to the user.
        dateValidator = new ContextMenu();
        dateValidator.setAutoHide(false);
        dateValidator.getItems().add(
                new MenuItem("Vennligst angi en startdato i format dd.mm.åååå"));

        //Adds a listener to show error message if no valid start date has been set
        datePicker.focusedProperty()
                .addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                    if (Boolean.FALSE.equals(newPropertyValue)) {
                        //Showing error message if no valid date has been set
                        if (null == datePicker.getValue()) {
                            dateValidator.show(datePicker, Side.RIGHT, 10, 0);
                        } else {
                            //Hiding the error message if valid input
                            dateValidator.hide();
                        }
                    }
                });


        customerPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.matches("[0-9+]*")) {
                    customerPhoneNumber.setText(oldValue);
                }

            } catch (NumberFormatException e) {
                customerPhoneNumber.setText(oldValue);
            }
        });

        datePicker.getEditor().setOnKeyTyped(event -> {
            if (!"0123456789/".contains(event.getCharacter())) {
                return;
            }
            datePicker.getEditor().selectForward();
            datePicker.getEditor().cut();
        });

        getDialogPane().setContent(grid);



        setResultConverter(
                (ButtonType button) -> {
                    Project result = null;
                    if (button == okButtonType) {
                        if (mode == Mode.NEW) {
                            result = new Project(projectName.getText(), customerName.getText(), customerEmail.getText(), customerPhoneNumber.getText(), address.getText(), datePicker.getValue());
                        } else if (mode == Mode.EDIT) {
                            existingProject.setName(projectName.getText());
                            existingProject.setCustomerName(customerName.getText());
                            existingProject.setCustomerEmail(customerEmail.getText());
                            existingProject.setCustomerPhoneNumber(customerPhoneNumber.getText());
                            existingProject.setAddress(address.getText());
                            existingProject.setStartDate(datePicker.getValue());
                            result = existingProject;
                        }
                    }
                    return result;
                }
        );
    }
}