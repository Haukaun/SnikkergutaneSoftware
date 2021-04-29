package com.snikkergutane.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a construction project.
 */
public class Project {

    private String name;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private final ArrayList<Task> tasks;

    /**
     * Creates a new instance of the class.
     * @param projectName {@code String} name of the project.
     * @param customerName {@code String} name of the project's customer.
     * @param customerEmail {@code String} customer's email address.
     * @param customerPhoneNumber {@code String} customer's phone number.
     * @param address {@code String} address of the project.
     * @param startDate {@code LocalDate} of the project's start.
     */
    public Project(String projectName, String customerName, String customerEmail, String customerPhoneNumber, String address, LocalDate startDate, String description) {
        this.name = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.address = address;
        this.startDate = startDate;
        this.endDate = startDate;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates a new instance of the project, including end date and description.
     * @param projectName {@code String} name of the project.
     * @param customerName {@code String} name of the project's customer.
     * @param customerEmail {@code String} customer's email address.
     * @param customerPhoneNumber {@code String} customer's phone number.
     * @param address {@code String} address of the project.
     * @param startDate {@code LocalDate} of the project's start.
     * @param endDate {@code LocalDate} of the project's deadline or end date.
     * @param description {@code String} description of the project.
     */
    public Project(String projectName, String customerName, String customerEmail,
                   String customerPhoneNumber, String address, LocalDate startDate,
                   LocalDate endDate, String description) {

        this.name = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.address = address;
        this.startDate = startDate;
        this.description = description;
        this.tasks = new ArrayList<>();
        this.endDate = endDate;
    }

    /**
     * Returns a stage with the provided name.
     * @param name {@code String} name of the stage to be returned.
     * @return {@code Stage} with given name.
     */
    public Task getTask(String name) {
        Task returnTask = null;
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                returnTask = task;
            }
        }
        return returnTask;
    }

    /**
     * Returns the stages of the project as an ArrayList.
     * @return {@code ArrayList<Stage>} of the projects stages.
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a stage to the projects list of stages.
     * @param task {@code Stage} the stage to be added.
     */
    public void addTask(Task task) {
        if(task != null){
        this.tasks.add(task);
        }
    }

    /**
     * Removes a stage from the project.
     * @param taskName {@code String} the name of the project to be removed.
     */
    public void removeTask(String taskName) {
        Task removeTask = null;
        for (Task task : tasks) {
            if (task.getName().equals(taskName)) {
                removeTask = task;
            }
        }
        tasks.remove(removeTask);
    }

    /**
     * Returns the customer's name.
     * @return {@code String} of the customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the project's customer.
     * @param customerName {@code String} name of the project's customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the email address of the project's customer.
     * @return {@code String} email address of the project's customer.
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the email address of the project's customer.
     * @param customerEmail {@code String} email address of the project's customer.
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Returns the phone number of the project's customer.
     * @return {@code String} phone number of the project's customer.
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Sets the phone number of the project's customer.
     * @param customerPhoneNumber {@code String} of the project's customer.
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Returns the project's address.
     * @return {@code String} address of the project.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the project's address.
     * @param address {@code String} address of the project.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the start date of the project.
     * @return {@code LocalDate} start date of the project.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date of the project.
     * @param date {@code LocalDate} start date of the project.
     */
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    /**
     * Returns the end date of the project.
     * @return {@code LocalDate} end date of the project.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Returns the name of the project.
     * @return {@code String} name of the project.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the project.
     * @param name {@code String} name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the project.
     * @return {@code String} description of the project.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a List of string arrays containing all information about the project,
     *   including all it's tasks and their eventual comments.
     * @return {@code List<String[]>} of all information about the project.
     */
    public List<String[]> getProjectAsStringArrayList() {
        List<String> projectInfo = new ArrayList<>(Arrays.asList(this.getName(),this.customerName,
                this.customerEmail, this.customerPhoneNumber, this.address,"" + this.getStartDate(),
                "" + this.getEndDate(), this.getDescription()));

        List<String[]> project = new ArrayList<>();
        project.add(projectInfo.toArray(new String[0]));

        this.getTasks().forEach(task -> project.addAll(task.getTaskAsStringArray()));

        return project;
    }
}
