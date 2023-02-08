package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;

public class TestPlayerMovement {
    
    /**
     * This tests for player's ability to move down
     */
    @Test
    public void testMoveDown() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);

        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);
    }

    /**
     * This tests for player's ability to move up
     */
    @Test
    public void testMoveUp() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveDown();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);

        p.moveUp();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);

        p.moveUp();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }

    /**
     * This tests for player's ability to move right
     */
    @Test
    public void testMoveRight() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);
    }

    /**
     * This tests for player's ability to move left
     */
    @Test
    public void testMoveLeft() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveRight();
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }

    /**
     * This tests for player's ability to be blocked by walls
     */
    @Test
    public void testPath() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveRight();
        p.moveRight();
        p.moveDown();
        p.moveDown();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);

        p.moveLeft();
        p.moveUp();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);

        p.moveLeft();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);
    }
}