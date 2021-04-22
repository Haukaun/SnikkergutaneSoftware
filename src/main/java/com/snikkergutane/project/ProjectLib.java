package com.snikkergutane.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * A collection of projects.
 * Handles creation, editing and
 * deletion of projects in the application.
 */
public class ProjectLib {
    private final TreeMap<String, Project> projects;

    public ProjectLib() {
        this.projects = new TreeMap<>();
    }
    /**
     * Returns the project with given name.
     * @param name name of the project.
     * @return {@code Project} the project with given name.
     */
    public Project getProject(String name) {
        //TODO: Handle NullPointerExceptions.
        return projects.get(name);
    }

    /**
     * Adds a project to the collection.
     * @param project the project to be added.
     */
    public void addProject(Project project) {
        //TODO: Handle NullPointerExceptions.
        this.projects.put(project.getName(),project);
    }

    /**
     * Removes a project from the collection.
     * @param name the name of the project to be removed.
     */
    public void removeProject(String name) {
        //TODO: Handle NullPointerExceptions.
        projects.remove(name);
    }

    /**
     * Returns a list of all projects in the collection in a String format.
     * @return A {@code String} list of all projects in the collection.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : projects.keySet()) {
            sb.append(name).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the names of all projects as a list of Strings.
     * @return A {@code List<String>} of all projects in the collection.
     */
    public List<String> listProjects() {
        return new ArrayList<>(projects.keySet());
    }

    /**
     * Creates a demo project and adds it to the projectLib.
     */
    public void loadDemoProject() {
        Task task = new Task("Balkongdør Stue");
        task.setDescription("Skifte balkongdør på stue.\n" +
                "\n" +
                "Så mye tekst som ønskelig kan legges til her for å tilstrekkelig formidle informasjon nødvendig for utførelse av oppgaven.");
        task.setStartDate(LocalDate.now());
        task.addImage("com/snikkergutane/images/1.jpg");
        task.addImage("com/snikkergutane/images/2.jpg");
        task.addImage("com/snikkergutane/images/3.jpg");
        task.addImage("com/snikkergutane/images/4.jpg");

        Project newProject = new Project("Portveien 4", "Anne Knutsdotter", "anne.knutsdotter@Steinroys.no", "12345678", "Der ingen skulle tru at nokon kunne bu");
        newProject.setStartDate(LocalDate.now());
        newProject.addTask(task);

        if (this.projects.size() == 0) {
            this.projects.put(newProject.getName(), newProject);
        }
    }
}
