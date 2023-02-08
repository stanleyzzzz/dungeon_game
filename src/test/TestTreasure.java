package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.goals.Goal;


public class TestTreasure {

    private static JSONObject simpleGoal;
    private static JSONObject compositeGoal;

    public TestTreasure() {
        simpleGoal = new JSONObject();
        simpleGoal.put("goal", "treasure");


        JSONObject otherGoal = new JSONObject();
        otherGoal.put("goal", "boulders");
        JSONArray subgoalArray = new JSONArray();
        subgoalArray.put(simpleGoal);
        subgoalArray.put(otherGoal);

        compositeGoal = new JSONObject();
        compositeGoal.put("goal", "OR");
        compositeGoal.put("subgoals", subgoalArray);
    }
    
    /**
     * This tests the player's ability to collect one single treasure
     * Goal is achieved subsequently
     */
    @Test
    public void testCollect() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 3, 1);
        cd.setGoal(simpleGoal);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Treasure"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 1);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }

    /**
     * This tests the player's ability to collect teasure
     * This test also make use of an OR goal
     */
    @Test
    public void testCollectComposite() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 3, 1);
        cd.setGoal(compositeGoal);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Treasure"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 1);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }

    /**
     * This tests the player's ability to collect multiple teasure
     * This test make use of an single goal
     */
    @Test
    public void testCollectAdvanced() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 3, 1);
        cd.addEntity("treasure", 2, 2);
        cd.addEntity("treasure", 1, 3);
        cd.addEntity("treasure", 3, 3);
        cd.setGoal(simpleGoal);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Treasure"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 1);

        p.moveDown();
        p.moveDown();
        assertEquals(p.getNumCollectable("Treasure"), 2);

        p.moveLeft();
        p.moveLeft();
        assertEquals(p.getNumCollectable("Treasure"), 3);

        p.moveUp();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 4);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }

    /**
     * This tests the player's ability to collect multiple teasures
     * This test also make use of an OR goal
     */
    @Test
    public void testCollectAdvancedComposite() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("treasure", 3, 1);
        cd.addEntity("treasure", 2, 2);
        cd.addEntity("treasure", 1, 3);
        cd.addEntity("treasure", 3, 3);
        cd.setGoal(compositeGoal);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Treasure"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 1);

        p.moveDown();
        p.moveDown();
        assertEquals(p.getNumCollectable("Treasure"), 2);

        p.moveLeft();
        p.moveLeft();
        assertEquals(p.getNumCollectable("Treasure"), 3);

        p.moveUp();
        p.moveRight();
        assertEquals(p.getNumCollectable("Treasure"), 4);

        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true); 
    }
}

