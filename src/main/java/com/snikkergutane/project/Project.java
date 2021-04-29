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
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private final ArrayList<Task> tasks;

    public void setName(String name) {
        this.name = name;
    }


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


    public boolean isFinished() {
        if (null != this.endDate) {
            return LocalDate.now().isAfter(this.endDate);
        } else {
            return false;
        }
    }

    public void setFinished(boolean finished) {
        if (finished) {
            this.endDate = LocalDate.now();
        } else {
            this.endDate = null;
        }
    }

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
