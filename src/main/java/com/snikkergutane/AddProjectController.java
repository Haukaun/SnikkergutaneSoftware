package com.snikkergutane;

import com.snikkergutane.project.Project;
import com.snikkergutane.project.ProjectLib;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class AddProjectController extends Dialog<Project> {
    ProjectLib projectLib;
    @FXML private TextField addProjectBox;
    @FXML private TextField addCustomerBox;
    @FXML private TextField addCustomerPhoneBox;
    @FXML private TextField addCustomerName;


    public AddProjectController(ProjectLib projectLib) {
        this.projectLib = projectLib;
    }

    @FXML
    private void addProjectButtonClicked() {
        setResultConverter(
                (ButtonType button) -> {
                    Project result = null;
                    if (button == ButtonType.APPLY) {
                        String firstName = addProjectBox.getText();
                        String lastName = addCustomerBox.getText();
                        String socialSecurityNumber = addCustomerPhoneBox.getText();
                        String generalPractitioner = addCustomerName.getText();

                        result = new Project(firstName, lastName, generalPractitioner, socialSecurityNumber, null);
                    }
                    return result;
                }
        );
    }

}
