package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;


public class TestKeyDoor {

    @Test
    public void testDoorWithKey() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 3, 1, 0);
        cd.addEntity("door", 3, 2, 0);
        Player p = d.getPlayer();

        // Get key and go through the door
        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);
        p.moveDown();
        p.moveDown();
        assertEquals(p.getY(), 3);
    }
    
    @Test
    public void testDoorWithoutKey() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("door", 2, 2, 0);
        Player p = d.getPlayer();

        // Cannot enter door from above without key
        p.moveRight();
        p.moveDown();
        assertEquals(p.getY(), 1);

        // Cannot enter door from the right without key
        p.moveRight();
        p.moveDown();
        p.moveLeft();
        assertEquals(p.getX(), 3);

        // Cannot enter door from below without key
        p.moveDown();
        p.moveLeft();
        p.moveUp();
        assertEquals(p.getY(), 3);

        // Cannot enter door from the left without key
        p.moveLeft();
        p.moveUp();
        p.moveRight();
        assertEquals(p.getX(), 1);

    }

    @Test
    public void testDoorBeforeKey() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 2, 1, 0);
        cd.addEntity("door", 1, 2, 0);
        Player p = d.getPlayer();

        // Cannot move down because we don't have the key
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 0);
        assertEquals(p.getY(), 1);
        
        // Getting key 
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);
        
        // Going through the door
        p.moveLeft();
        p.moveDown();
        p.moveDown();
        assertEquals(p.getY(), 3);
    }

    @Test
    public void testTwoDoorsWithOneKey() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 2, 1, 0);
        cd.addEntity("door", 2, 2, 1);
        cd.addEntity("door", 3, 2, 0);
        Player p = d.getPlayer();

        // Getting key
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);

        // Cannot move down because key does not correspond to door
        // Key is also not used
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 1);
        assertEquals(p.getY(), 1);

        // Going through the correct door
        p.moveRight();
        p.moveDown();
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 0);
        assertEquals(p.getY(), 3);
    }

    @Test
    public void testThreeDoorsAndKeys() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 2, 1, 0);
        cd.addEntity("door", 2, 2, 0);
        cd.addEntity("key", 2, 3, 1);
        cd.addEntity("door", 3, 3, 1);
        cd.addEntity("key", 3, 2, 2);
        cd.addEntity("door", 3, 1, 2);
        Player p = d.getPlayer();

        // Stand inside door 1
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 0);
        assertEquals(p.getY(), 2);

        // Stand inside door 2
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 1);
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 0);
        assertEquals(p.getX(), 3);

        // Stand inside door 3
        p.moveUp();
        assertEquals(p.getNumCollectable("Key"), 1);
        p.moveUp();
        assertEquals(p.getNumCollectable("Key"), 0);
        assertEquals(p.getY(), 1);
    }
}