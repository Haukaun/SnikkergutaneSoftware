package com.snikkergutane;

import com.snikkergutane.project.Project;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


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
        setTitle("Patient Dialog");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 105, 10, 10));

        TextField projectName = new TextField();
        projectName.setPromptText("Firstname");

        TextField customerName = new TextField();
        customerName.setPromptText("Lastname");

        TextField customerEmail = new TextField();
        customerEmail.setPromptText("SecurityNumber");

        TextField customerPhoneNumber = new TextField();
        customerPhoneNumber.setPromptText("Practitioner");

        TextField Adress = new TextField();
        Adress.setPromptText("Diagnosis");

        if((mode == Mode.EDIT) || (mode == Mode.INFO)){

            projectName.setText(existingProject.getProjectName());
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

        grid.add(new Label("ProjectName"), 0, 0);
        grid.add(projectName, 1, 0 );

        grid.add(new Label("Lastname:"), 0, 1);
        grid.add(customerName, 1, 1);

        grid.add(new Label("SecurityNumber:"), 0, 2);
        grid.add(customerEmail, 1, 2);

        grid.add(new Label("Practitioner:"), 0, 3);
        grid.add(customerPhoneNumber, 1, 3);

        grid.add(new Label("Diagnosis:"), 0, 4);
        grid.add(Adress,1,4);

        getDialogPane().setContent(grid);


        setResultConverter(
                (ButtonType button) -> {
                    Project result = null;
                    if (button == ButtonType.OK) {
                        if (mode == Mode.NEW) {
                            result = new Project(projectName.getText(), customerName.getText(), customerEmail.getText(), customerPhoneNumber.getText(), Adress.getText());
                        } else if (mode == Mode.EDIT) {
                            existingProject.setProjectName(projectName.getText());
                            existingProject.setCustomerName(customerName.getText());
                            existingProject.setCustomerEmail(customerEmail.getText());
                            existingProject.setCustomerPhoneNumber(customerPhoneNumber.getText());
                            existingProject.setAddress(Adress.getText());
                            result = existingProject;
                        }
                    }
                    return result;
                }
        );


    }
}