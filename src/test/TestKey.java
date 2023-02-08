package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;


public class TestKey {

    /**
     * This tests for the case where player can pick up 1 key
     */
    @Test
    public void testCollect() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 3, 1, 0);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Key"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);
    }

     /**
     * This tests for the case where player is able to pick up multiple keys
     * but only 1 key can be picked up
     */
    @Test
    public void testCollectAdvanced() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("key", 3, 1, 0);
        cd.addEntity("key", 2, 2, 1);
        cd.addEntity("key", 1, 3, 2);
        cd.addEntity("key", 3, 3, 3);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Key"), 0);

        p.moveRight();
        p.moveRight();

        assertEquals(p.getNumCollectable("Key"), 1);
        
        p.moveDown();
        p.moveDown();
        assertEquals(p.getNumCollectable("Key"), 1);

        p.moveLeft();
        p.moveLeft();
        assertEquals(p.getNumCollectable("Key"), 1);

        p.moveUp();
        p.moveRight();
        assertEquals(p.getNumCollectable("Key"), 1);
        
    }

    // test for key door interaction will be done in doors
}