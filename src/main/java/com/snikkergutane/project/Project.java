package com.snikkergutane.project;

import java.util.ArrayList;

/**
 * Represents a project containing
 * a number of stages to be completed.
 */
public class Project extends TaskMasterClass {

    private final ArrayList<Stage> stages;

    public Project (String name) {
        setName(name);
        setFinished(false);
        setDeadline(null);
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
}
