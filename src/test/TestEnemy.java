package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import unsw.dungeon.Enemy;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.goals.Goal;

public class TestEnemy {
    private static JSONObject singleGoal;
    private static JSONObject ORGoal;
    private static JSONObject ANDGoal;

    public TestEnemy() {
        singleGoal = new JSONObject();
        singleGoal.put("goal", "enemies");
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
     * This tests to see if the enemy will correctly 
     * move towards the player and destroy them
     * The goal is not achieved.
     */
    @Test
    public void testDestroy() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(singleGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();

        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(2, enemy.getY());
        assertEquals(player, d.getPlayer());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(1, enemy.getY());
        assertEquals(player, d.getPlayer());
        enemy.findMove();
        assertEquals(2, enemy.getX());
        assertEquals(1, enemy.getY());
        assertEquals(player, d.getPlayer());
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(1, enemy.getY());
        // Enemy sets player to null
        assertEquals(null, d.getPlayer());
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), false);
    }

    /**
     * This tests to see if the enemy will adjust
     * its path after the player repositions.
     * The goal is not achieved.
     */
    @Test
    public void testFollow() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(singleGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();

        player.moveDown();
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(2, enemy.getY());
        enemy.findMove();
        assertEquals(player, d.getPlayer());
        assertEquals(2, enemy.getX());
        assertEquals(2, enemy.getY());
        assertEquals(player, d.getPlayer());
        player.moveDown();
        enemy.findMove();;
        assertEquals(2, enemy.getX());
        assertEquals(3, enemy.getY());
        assertEquals(player, d.getPlayer());
        player.moveUp();
        player.moveUp();
        player.moveRight();
        player.moveRight();
        enemy.findMove();;
        assertEquals(2, enemy.getX());
        assertEquals(2, enemy.getY());
        assertEquals(player, d.getPlayer());
        enemy.findMove();;
        assertEquals(2, enemy.getX());
        assertEquals(1, enemy.getY());
        assertEquals(player, d.getPlayer());
        enemy.findMove();;
        assertEquals(3, enemy.getX());
        assertEquals(1, enemy.getY());
        assertEquals(null, d.getPlayer());
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), false);
    }

    /**
     * This tests if the player can kill the enemy with a sword.
     * The goal is achieved.
     */
    @Test
    public void testSword() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(singleGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        cd.addEntity("sword", 2, 1);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();
        // Enemy can be killed with a sword
        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        assertEquals(enemy.getAlive(), false);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests if the player can kill the enemy whilst invisble.
     * The goal is achieved.
     */
    @Test
    public void testInvincibility() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(singleGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        cd.addEntity("invincibility", 2, 1);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();
        // Enemy can be killed with a invincibility
        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        assertEquals(enemy.getAlive(), false);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests if the the enemy runs away from the player
     * whilst the player is invincible.
     * The goal is achieved.
     */
    @Test
    public void testRunAway() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(singleGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        cd.addEntity("invincibility", 2, 1);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();
        // Enemy runs away from player with invincibility
        player.moveRight();
        player.moveLeft();
        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());
        player.moveDown();
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());
        player.moveDown();
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(2, enemy.getY());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(1, enemy.getY());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(1, enemy.getY());
        player.moveRight();
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(1, enemy.getY());
        player.moveRight();
        enemy.findMove();
        assertEquals(2, enemy.getX());
        assertEquals(1, enemy.getY());
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(1, enemy.getY());
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(1, enemy.getY());
        player.moveUp();
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(1, enemy.getY());
        player.moveUp();
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(2, enemy.getY());
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(3, enemy.getY());
        player.moveLeft();
        enemy.findMove();
        assertEquals(1, enemy.getX());
        assertEquals(3, enemy.getY());
        player.moveLeft();
        enemy.findMove();
        assertEquals(2, enemy.getX());
        assertEquals(3, enemy.getY());
        enemy.findMove();
        assertEquals(3, enemy.getX());
        assertEquals(3, enemy.getY());

        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        assertEquals(enemy.getAlive(), false);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests the case of Enemies with conjunction with another goal.
     * Player can collect treasures OR kill all enemies.
     */
    @Test
    public void testORGoal() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(ORGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        cd.addEntity("sword", 2, 1);
        cd.addEntity("treasure", 1, 2);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();

        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        assertEquals(enemy.getAlive(), false);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }

    /**
     * This tests the case of Enenmies with conjunction with another goal.
     * Player must collect treasures AND kill all enemies.
     */
    @Test
    public void testANDGoal() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();
        cd.setGoal(ANDGoal);
        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 3);
        cd.addEntity("sword", 2, 1);
        cd.addEntity("treasure", 1, 2);
        Enemy enemy = d.getEnemies().get(0);
        Player player = d.getPlayer();

        player.moveDown();
        player.moveDown();
        player.moveRight();
        player.moveUp();
        player.moveUp();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        assertEquals(enemy.getAlive(), false);
        Goal g = d.getGoal();
        assertEquals(g.getAchieved(), true);
    }
}