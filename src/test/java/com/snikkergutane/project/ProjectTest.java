package com.snikkergutane.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Qiaoduo Shi
 * @version 2020.03.26
 */
class ProjectTest {
    private Project project;
    private Stage stage1;
    private Stage stage2;

    @BeforeEach
    public void setUp() {
        this.project = new Project("Project1");
        this.stage1 = new Stage("Stage1");
        this.stage2 = new Stage("Stage2");
        this.project.addStage(stage1);
        this.project.addStage(stage2);
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
        assertEquals(stage1, project.getStage("Stage1"));
        assertEquals(null, project.getStage("Nothing"));
        //assertEquals("Stage1", project.getStage("Stage1").getName());
        //negative test with null input.
        assertEquals(null, project.getStage(null));
    }

    /**
     * Test add stage method.
     */
    @Test
    public void testAddStageWithValidInput() {
        // positive test with a valid parameter stage3.
        Stage stage3 = new Stage("Stage3");
        project.addStage(stage3);
        assertEquals(3, project.getStages().size());
    }

    @Test
    public void testAddStageWithInvalidInputNull() {
    // negative test with a invalid parameter null.
        project.addStage(null);
        assertEquals(2,project.getStages().size());
    }
/**
 * To positive test to removeStage() method.
 * 1) Remove an object that is in the project.
 * 2) Remove an object that is NOT in the project.
 */
    @Test
    public void testRemoveStageWithValidInput() {
        project.removeStage("Stage1");
        assertEquals(null, project.getStage("Stage1"));
        assertEquals(1,project.getStages().size());

        project.removeStage("NotInTheProject");
        assertEquals(1,project.getStages().size());
    }
    /**
     * A negative test to removeStage() method.
     * Remove a stage with input null.
     */
    public void testRemoveStageWithInvalidInputNull(){
        project.removeStage(null);
        assertEquals(2,project.getStages().size());
    }
}