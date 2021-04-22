package com.snikkergutane.project;

import java.util.ArrayList;

/**
 * Represents a project containing
 * a number of stages to be completed.
 */
public class Project extends TaskMasterClass {

    private String projectName;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String address;
    private final ArrayList<Stage> stages;


    public Project (String projectName, String customerName, String customerEmail, String customerPhoneNumber, String address) {

        this.projectName = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.address = address;

        setFinished(false);
        setEndDate(null);
        this.stages = new ArrayList<>();
    }

    /**
     * Returns a stage with the provided name.
     * @param name {@code String} name of the stage to be returned.
     * @return {@code Stage} with given name.
     */
    public Stage getStage(String name) {
        Stage returnStage = null;
        for (Stage stage : stages) {
            if (stage.getName().equals(name)) {
                returnStage = stage;
            }
        }
        return returnStage;
    }

    /**
     * Returns the stages of the project as an ArrayList.
     * @return {@code ArrayList<Stage>} of the projects stages.
     */
    public ArrayList<Stage> getStages() {
        return this.stages;
    }

    /**
     * Adds a stage to the projects list of stages.
     * @param stage {@code Stage} the stage to be added.
     */
    public void addStage(Stage stage) {
        if(stage != null){
        this.stages.add(stage);
        }
    }

    /**
     * Removes a stage from the project.
     * @param stageName {@code String} the name of the project to be removed.
     */
    public void removeStage(String stageName) {
        Stage removeStage = null;
        for (Stage stage : stages) {
            if (stage.getName().equals(stageName)) {
                removeStage = stage;
            }
        }
        stages.remove(removeStage);
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
