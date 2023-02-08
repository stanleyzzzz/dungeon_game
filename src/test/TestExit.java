package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;
import unsw.dungeon.goals.Goal;


public class TestExit {
    private static JSONObject singleGoal;
    private static JSONObject ORGoal;
    private static JSONObject ANDGoal;

    public TestExit() {
        singleGoal = new JSONObject();
        singleGoal.put("goal", "exit");
        
        JSONObject otherGoal = new JSONObject();
        otherGoal.put("goal", "treasure");
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
     * This tests for a simple exit case
     * Game is finished when player move through the exit
     */
    @Test
    public void testSingleGoal() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("exit", 3, 3);
        cd.setGoal(singleGoal);
        Player p = d.getPlayer();
        Exit exit = d.getExit();

        p.moveRight();
        p.moveRight();
        p.moveDown();
        p.moveDown();
        
        assertEquals(p.getX(), exit.getX());
        assertEquals(p.getY(), exit.getY());

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests the case of Exit with conjunction with another goal
     * Player can collect treasures OR move through exit
     */
    @Test
    public void testORGoal() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 1, 2);
        cd.addEntity("treasure", 1, 3);
        cd.addEntity("treasure", 2, 3);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ORGoal);
        Player p = d.getPlayer();
        Exit exit = d.getExit();

        p.moveRight();
        p.moveRight();
        p.moveDown();
        p.moveDown();
        
        assertEquals(p.getX(), exit.getX());
        assertEquals(p.getY(), exit.getY());

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }


    /**
     * This tests the case of Exit with conjunction with another goal
     * Player must collect treasures AND move through exit
     */
    @Test
    public void testANDGoal() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 1, 2);
        cd.addEntity("treasure", 1, 3);
        cd.addEntity("treasure", 2, 3);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ANDGoal);
        Player p = d.getPlayer();
        Exit exit = d.getExit();

       
        p.moveDown();
        p.moveDown();
        p.moveRight();
        p.moveRight();
        
        assertEquals(p.getX(), exit.getX());
        assertEquals(p.getY(), exit.getY());

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }

    /**
     * This tests the case of Exit with conjunction with another goal, this is of advanced level
     * Player must move around the map to collect all treasures.
     * Exit is blocked off until all treasures are collected
     */
    @Test
    public void testANDGoalComplex() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 1, 2);
        cd.addEntity("treasure", 1, 3);
        cd.addEntity("treasure", 2, 3);
        cd.addEntity("treasure", 2, 1);
        cd.addEntity("treasure", 3, 1);
        cd.addEntity("treasure", 3, 2);
        cd.addEntity("exit", 3, 3);
        cd.setGoal(ANDGoal);
        Player p = d.getPlayer();
        Exit exit = d.getExit();

        p.moveDown();
        p.moveDown();
        p.moveRight();
        p.moveRight();
        
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);

        p.moveLeft();
        p.moveUp();
        p.moveUp();
        p.moveRight();
        p.moveRight();
        p.moveDown();
        p.moveDown();
        assertEquals(p.getX(), exit.getX());
        assertEquals(p.getY(), exit.getY());

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }

}