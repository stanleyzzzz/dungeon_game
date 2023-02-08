package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;

public class TestSword {
    
    /**
     * This tests for player's ability to pick up a sword
     */
    @Test
    public void testCollect() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("sword", 3, 1);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Sword"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Sword"), 1);
    }


    /**
     * This tests for player's ability to pick up a sword
     * player can pnly pick up one sword
     * after sword is used, the player can pick up another sword
     */
    @Test
    public void testMultipleCollect() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("sword", 3, 1);
        cd.addEntity("sword", 3, 3);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Sword"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Sword"), 1);

        p.moveDown();
        p.moveDown();
        assertEquals(p.getNumCollectable("Sword"), 1);

        p.useSword();
        p.useSword();
        p.useSword();
        p.useSword();
        p.useSword();
        assertEquals(p.getNumCollectable("Sword"), 0);

        p.moveUp();
        p.moveDown();
        assertEquals(p.getNumCollectable("Sword"), 1);
    }

    // fighting enemy will be done in test file for enemy
}