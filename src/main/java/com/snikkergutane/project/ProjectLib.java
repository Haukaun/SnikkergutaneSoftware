package com.snikkergutane.project;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

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
     * @return {@code Project} the project with given name,
     *    or {@code null} if no project with given name exists.
     */
    public Project getProject(String name) {
        Project project;
        try {
            project = projects.get(name);
        } catch (NullPointerException np) {
            project = null;
        }
        return project;
    }

    /**
     * Returns a SorterMap of all projects.
     * @return {@code SortedMap<String, Project>} of all projects.
     */
    public SortedMap<String, Project> getProjects() {
        return this.projects;
    }

    /**
     * Adds a project to the project map, if a project with the same name doesn't already exist in the map.
     * @param project the project to be added.
     */
    public void addProject(Project project) {
        if (null != project) {
            this.projects.put(project.getName(), project);
        }
    }

    /**
     * Removes a given project from the project map, if it exists in the map.
     * @param name the name of the project to be removed.
     */
    public void removeProject(String name) {
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
}
