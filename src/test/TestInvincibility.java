package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;


public class TestInvincibility {
    
    /**
     * This tests the case where player is able to collect invincibility potion
     */
    @Test
    public void testCollect() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("invincibility", 3, 1);
        Player p = d.getPlayer();

        assertEquals(p.getNumCollectable("Invincibility"), 0);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Invincibility"), 1);
    }

    /**
     * This tests the state of player before and after collecting potion
     */
    @Test
    public void testState() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("invincibility", 3, 1);
        Player p = d.getPlayer();

        assertEquals(p.getState(), "Vulnerable");

        p.moveRight();
        p.moveRight();
        assertEquals(p.getNumCollectable("Invincibility"), 1);
        assertEquals(p.getState(), "Invincible");

    }

    //  fighting enemy will be done in test file for enemy
}