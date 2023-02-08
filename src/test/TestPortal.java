package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Boulder;
import unsw.dungeon.Enemy;


public class TestPortal {

    @Test
    public void testGoingThroughPortal() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("portal", 2, 1, 0);
        cd.addEntity("portal", 3, 3, 0);
        Player p = d.getPlayer();

        // Go through portal
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);
    }

    @Test
    public void testGoingThroughPortalAndBack() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("portal", 2, 1, 0);
        cd.addEntity("portal", 3, 3, 0);
        Player p = d.getPlayer();

        // Go through first portal
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);

        // Go through other portal back to the first portal
        p.moveUp();
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testFourPortals() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("portal", 2, 1, 0);
        cd.addEntity("portal", 2, 3, 0);
        cd.addEntity("portal", 3, 1, 1);
        cd.addEntity("portal", 3, 3, 1);
        Player p = d.getPlayer();

        // Go through first portal, check if at its corresponding portal
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);

        // Go through second portal, check if at its corresponding portal
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testOnePortal() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("portal", 2, 1, 0);
        Player p = d.getPlayer();

        // Cannot teleport anywhere via portal because it has no pair
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testPortalsAndBoulder() {
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("boulder", 2, 1);
        cd.addEntity("portal", 3, 1, 0);
        cd.addEntity("portal", 2, 3, 0);
        Player p = d.getPlayer();
        Boulder b = d.getBoulders().get(0);

        // Push boulder through portal
        p.moveRight();
        assertEquals(b.getX(), 2);
        assertEquals(b.getY(), 3);

        // Cannot go through the portal ourselves because the other end is
        // blocked by the boulder
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);

        // Push the boulder out of the way
        // This will make the player land on the portal and teleport
        p.moveDown();
        p.moveDown();
        p.moveLeft();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testPortalsAndEnemy(){
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 1);
        cd.addEntity("wall", 2, 1);
        cd.addEntity("wall", 2, 2);
        cd.addEntity("wall", 2, 3);
        cd.addEntity("portal", 3, 1, 0);
        cd.addEntity("portal", 3, 3, 0);
        Enemy e = d.getEnemies().get(0);

        // Because of the wall, the enemy can only reach the player through the portal
        // The enemy will therefore try to use the portal
        e.findMove();
        e.findMove();
        assertEquals(e.getX(), 3);
        assertEquals(e.getY(), 1);
    }

    @Test
    public void testPortalsAndEnemyAndInvincible(){
        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("enemy", 3, 1);
        cd.addEntity("invincibility", 2, 1);
        cd.addEntity("wall", 1, 2);
        cd.addEntity("wall", 2, 2);
        cd.addEntity("wall", 2, 3);
        cd.addEntity("portal", 1, 3, 0);
        cd.addEntity("portal", 3, 3, 0);
        Enemy e = d.getEnemies().get(0);
        Player p = d.getPlayer();
        p.moveRight();

        // Because of the wall, the enemy can only run away through the portal
        e.findMove();
        e.findMove();
        assertEquals(e.getX(), 1);
        assertEquals(e.getY(), 3);
    }
}