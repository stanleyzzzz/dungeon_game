package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.Player;
import unsw.dungeon.goals.Goal;



public class TestBoulderFloorSwitch {
    
    private static JSONObject singleGoal;
    private static JSONObject ORGoal;
    private static JSONObject ANDGoal;

    public TestBoulderFloorSwitch() {


        singleGoal = new JSONObject();
        singleGoal.put("goal", "boulders");

        JSONObject otherGoal = new JSONObject();
        otherGoal.put("goal", "exit");
        JSONArray subgoalArray = new JSONArray();
        subgoalArray.put(singleGoal);
        subgoalArray.put(otherGoal);

        ORGoal = new JSONObject();
        ORGoal.put("goal", "OR");
        ORGoal.put("subgoals", subgoalArray);

        ANDGoal = new JSONObject();
        ANDGoal.put("goal", "AND");
        ANDGoal.put("subgoals", subgoalArray);
    }

    /**
    * This tests for boulder moving up
    * 
    */
    @Test
    public void testMoveUp() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 2);
        cd.addEntity("switch", 2, 1);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();

        p.moveDown();
        p.moveDown();
        p.moveRight();
        p.moveUp();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
    * This tests for boulder moving down
    * 
    */
    @Test
    public void testMoveDown() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 2);
        cd.addEntity("switch", 2, 3);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();

        p.moveRight();
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
    * This tests for boulder moving left
    * 
    */
    @Test
    public void testMoveLeft() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 2);
        cd.addEntity("switch", 1, 2);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();

        p.moveRight();
        p.moveRight();
        p.moveDown();
        p.moveLeft();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
    * This tests for boulder moving up
    * 
    */
    @Test
    public void testMoveRight() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 2);
        cd.addEntity("switch", 3, 2);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();

        p.moveDown();
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests for a simple boulder case with 1 pair of boulder and switch
     * Game is finished when player have boulder on all floorswitch
     */
    @Test
    public void testSingleGoalSimple() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("switch", 3, 1);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();

        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);

        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        List<Boulder> boulders = d.getBoulders();
        Boulder boulder = boulders.get(0);
        assertEquals(boulder.getX(), 3);
        assertEquals(boulder.getY(), 1);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);

    }

    /**
     * This tests for a complex boulder case with 2 pairs of boulder and switch
     * Game is finished when player have boulder on all floorswitch
     */
    @Test
    public void testSingleGoalAdv() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("switch", 3, 1);
        cd.addEntity("boulder", 1, 2);
        cd.addEntity("switch", 1, 3);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();


        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests for a complex boulder case with 2 pairs of boulder and switch
     * This make use of an OR goal
     * Game is finished when player have boulder on all floorswitch or reaching an exit
     */
    @Test
    public void testORGoal() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("switch", 3, 1);
        cd.addEntity("boulder", 1, 2);
        cd.addEntity("switch", 1, 3);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ORGoal);
        Player p = d.getPlayer();

        p.moveRight();
        p.moveDown();
        p.moveDown();
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests for a complex boulder case with 2 pairs of boulder and switch
     * This make use of an AND goal
     * The test is design such that player fails
     * as player cant go through exit (player didnt pushed all boulders)
     */
    @Test
    public void testANDGoalNoExit() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("switch", 3, 1);
        cd.addEntity("boulder", 1, 2);
        cd.addEntity("switch", 1, 3);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ANDGoal);
        Player p = d.getPlayer();

        p.moveRight();
        p.moveDown();
        p.moveDown();
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), false);
    }

    /**
     * This tests for a complex boulder case with 2 pairs of boulder and switch
     * This make use of an AND goal
     * The test is design such that player succeeds, after pushing all boulders on switches
     */
    @Test
    public void testANDGoalAdv() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("switch", 3, 1);
        cd.addEntity("boulder", 1, 2);
        cd.addEntity("switch", 1, 3);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ANDGoal);
        Player p = d.getPlayer();

        p.moveRight();
        p.moveLeft();
        p.moveDown();
        
        p.moveRight();
        p.moveRight();
        p.moveDown();
    }

}