package com.snikkergutane.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a project containing
 * a number of stages to be completed.
 */
public class Project {

    private String name;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String address;
    private boolean finished;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private final ArrayList<Stage> stages;

    public Project(String projectName, String customerName, String customerEmail, String customerPhoneNumber, String address) {
        this.name = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.address = address;
        this.startDate = null;
        this.endDate = null;
        this.stages = new ArrayList<>();
    }

    public Project(String projectName, String customerName, String customerEmail,
                   String customerPhoneNumber, String address, LocalDate startDate,
                   LocalDate endDate, String description) {

        this.name = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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
    public List<Stage> getStages() {
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

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setEndDate(LocalDate date) {
        this.endDate = date;
        if (LocalDate.now().isAfter(date)) {
            this.finished = true;
        }
    }

    public List<String[]> getProjectAsStringArrayList() {

        List<String> projectInfo = new ArrayList<>(Arrays.asList(this.getName(),this.customerName,
                this.customerEmail,this.address,"" + this.getStartDate(),
                "" + this.getEndDate(), this.getDescription()));

        List<String[]> project = new ArrayList<>();
        project.add(projectInfo.toArray(new String[0]));

        this.getStages().forEach(stage -> project.addAll(stage.getStageAsStringArray()));

        return project;
    }

}
