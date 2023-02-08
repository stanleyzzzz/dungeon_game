package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.MyDungeonLoader;
import unsw.dungeon.Player;

public class TestWall {
    
    @Test
    public void testCollision() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveUp();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void testCollisionAdv() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        Player p = d.getPlayer();

        p.moveDown();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);

        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);

        p.moveLeft();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);

        p.moveRight();
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);
        
        p.moveRight();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);

        p.moveDown();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);
    }


    @Test
    public void testAdvancedMap() {

        CreateDungeon cd = new CreateDungeon();
        cd.createSimpleDungeon();

        Dungeon d = cd.getSimpleDungeon();
        cd.addEntity("wall", 2, 2);
        Player p = d.getPlayer();

        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 1);

        p.moveLeft();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);
        p.moveRight();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);

        p.moveDown();
        p.moveRight();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);
        p.moveUp();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);

        p.moveRight();
        p.moveUp();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 2);
        p.moveLeft();
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 2);
    }

}