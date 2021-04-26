package com.snikkergutane.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Qiaoduo Shi
 * @version 2020.03.26
 */
class ProjectTest {
    private Project project;
    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        this.project = new Project("Project1", "Snikkerguttane", "test@hotmail.com", "98454796", "test 12", LocalDate.now());
        this.task1 = new Task("Stage1");
        this.task2 = new Task("Stage2");
        this.project.addTask(task1);
        this.project.addTask(task2);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test to get a stage from a project.
     */
    @Test
    public void testGetStage() {
        //To positive test.
        assertEquals(task1, project.getTask("Stage1"));
        assertEquals(null, project.getTask("Nothing"));
        //assertEquals("Stage1", project.getStage("Stage1").getName());
        //negative test with null input.
        assertEquals(null, project.getTask(null));
    }

    /**
     * Test add stage method.
     */
    @Test
    public void testAddStageWithValidInput() {
        // positive test with a valid parameter task3.
        Task task3 = new Task("Stage3");
        project.addTask(task3);
        assertEquals(3, project.getTasks().size());
    }

    @Test
    public void testAddStageWithInvalidInputNull() {
    // negative test with a invalid parameter null.
        project.addTask(null);
        assertEquals(2,project.getTasks().size());
    }
/**
 * To positive test to removeStage() method.
 * 1) Remove an object that is in the project.
 * 2) Remove an object that is NOT in the project.
 */
    @Test
    public void testRemoveStageWithValidInput() {
        project.removeTask("Stage1");
        assertEquals(null, project.getTask("Stage1"));
        assertEquals(1,project.getTasks().size());

        project.removeTask("NotInTheProject");
        assertEquals(1,project.getTasks().size());
    }
    /**
     * A negative test to removeStage() method.
     * Remove a stage with input null.
     */
    public void testRemoveStageWithInvalidInputNull(){
        project.removeTask(null);
        assertEquals(2,project.getTasks().size());
    }
}