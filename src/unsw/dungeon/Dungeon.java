/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.awt.Point;
import java.util.Queue;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.LinkedList;


import unsw.dungeon.goals.Goal;


/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private Player player;
    private Player player2;
    private List<Obstacle> obstacles;
    private List<Boulder> boulders;
    private List<FloorSwitch> floorSwitches;
    private List<Door> closedDoors;
    private List<Collectable> collectables;
    private List<Portal> portals;
    private List<Enemy> enemies;
    private Goal goal;
    private BooleanProperty achieved;
    private DifficultyMode difficulty;
    private double volumeScale;

    // goals
    // initial number of treasures, used to compare with player's possession
    private int numTreasure;
    private boolean isExitOpen;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.player = null;
        this.player2 = null;
        this.obstacles = new ArrayList<>();
        this.boulders = new ArrayList<>();
        this.floorSwitches = new ArrayList<>();
        this.closedDoors = new ArrayList<>();
        this.collectables = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.goal = null;
        this.numTreasure = 0;
        this.isExitOpen = false;
        this.achieved = new SimpleBooleanProperty(false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Boulder> getBoulders() {
        return boulders;
    }

    public List<FloorSwitch> getSwitches() {
        return floorSwitches;
    }

    public Goal getGoal(){
        return goal;
    }

    public DifficultyMode getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyMode difficulty) {
        this.difficulty = difficulty;
    }

    public double getVolumeScale() {
        return volumeScale;
    }

    public void setVolumeScale(double volumeScale) {
        this.volumeScale = volumeScale;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayer2(Player player2){
        this.player2 = player2;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void addPortals(Portal portal) {
        portals.add(portal);
    }

    public void removeObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    public void addBoulder(Boulder boulder) {
        boulders.add(boulder);
    }
    
    public void setGoal(Goal goal) {
        this.goal = goal;
        if (goal.getInfo().equals("Exit") || goal.getInfo().equals("OR")) {
            activateExit();
        }
        
    }

    public void activateExit(){
        isExitOpen = true;
        for (Obstacle o: obstacles){
            if (o instanceof Exit) ((Exit)o).activate();
        }
    }

    public Exit getExit(){
        for (Obstacle o: obstacles){
            if (o instanceof Exit) {
                return (Exit) o;
            }
        }
        return null;
    }
    
    
    public void addFloorSwitch(FloorSwitch floorSwitch) {
        floorSwitches.add(floorSwitch);
    }

    public void addClosedDoor(Door door) {
        closedDoors.add(door);
    }

    public void removeClosedDoor(Door door) {
        closedDoors.remove(door);
    }

    public List<Door> getClosedDoors() {
        return closedDoors;
    }

    public void addCollectable(Collectable collectable){
        collectables.add(collectable);
        if (collectable.getClass().getName().equals("unsw.dungeon.Treasure")) {
            numTreasure += 1;
        }
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        Iterator<Enemy> iter = enemies.iterator();
            while (iter.hasNext()) {
                Enemy e = iter.next();
                if (enemy.equals(e))
                    iter.remove();
            }
    }

    public void checkPortals(Entity entity) {
        for (Portal start: portals) {
            if (entity.getX() == start.getX() && entity.getY() == start.getY()) {
                for (Portal destination: portals) {
                    if (start.canTeleport(destination, boulders)) {
                        entity.teleport(destination.getX(), destination.getY());
                        if (entity.getClass().getName() == "unsw.dungeon.Boulder") {
                            Boulder boulder = (Boulder) entity;
                            boulder.setBlockedPortal(destination);
                        }
                        return;
                    }
                }

            }
        }
    }
    
    public void updateFloorSwitches() {
        for (FloorSwitch floorSwitch : floorSwitches) {
            boolean flag = false;
            for (Boulder boulder: boulders) {
                if (boulder.getX() == floorSwitch.getX() && boulder.getY() == floorSwitch.getY()) {
                    flag = true;
                    break;
                }
            }
            floorSwitch.setIsActivated(flag);
        }
    }

    public boolean hasPath(int x, int y){
        for (Obstacle o: obstacles){
            if (o.getX() == x && o.getY() == y){
                if(o.getCanPassThrough() == false) return false;
            }    
        }
        return true;
    }

    public Collectable findCollectable(int x, int y){

        Collectable item = null;
        for (Collectable c: collectables){
            if (c.getX() == x && c.getY() == y){
                item = c;
            }
        }

        return item;
    }

    public void removeCollectable(Collectable c){
        collectables.remove(c);
        if (c instanceof Treasure){
            checkGoal();
        }
    }

    // Converts the dungeon layout into a 2d array, treats most non collectable entities as walls
    public Point[][] toTable() {
        Point[][] table = new Point[height][width];
        for (int row = 0; row < height; row ++)
            for (int col = 0; col < width; col++)
                table[row][col] = new Point(-1, -1);
        for (Obstacle o : obstacles) {
            if (o.getCanPassThrough() == false)
                table[o.getY()][o.getX()] = null;
        }
        for (Enemy enemy : enemies) {
            table[enemy.getY()][enemy.getX()] = null;
        }
        for (Portal portal : portals) {
            table[portal.getY()][portal.getX()] = new Point(-2, portal.getId());
        }
        return table;
    }

    public int getNumTreasure(){
        return numTreasure;
    }

    public int getNumEnemies() {
        return enemies.size();
    }

    public BooleanProperty getAchieved() {
        return achieved;
    }

    private boolean printFlag = true;
    public void checkGoal(){
        if (goal == null) return;
        boolean result = goal.check();
        if (result && printFlag) {
            System.out.println("All goals accomplished!");
            player.addScore(goal.checkAsInt() * 50 * (1 + difficulty.ordinal()));
            System.out.println("Player 1 final score: " + player.getScore());
            if (player2 != null) {
                player2.addScore(goal.checkAsInt() * 50 * (1 + difficulty.ordinal())); 
                System.out.println("Player 2 final score: " + player2.getScore());
            } 
            printFlag = false;
            achieved.set(true);
            // exit program?
        }
    }

    // Finds path between start and end positions using altered BFS. 
    public List<Point> findPath(int startRow, int startColumn, int endRow, int endColumn) {
        Point[][] table = toTable();
        List<Point> result = new ArrayList<>();
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(startColumn, startRow));
        int[] columnDelta = {0, 0, 1, -1};
        int[] rowDelta = {1, -1, 0, 0};
        while (!queue.isEmpty()) {
            Point coordinates = queue.remove();
            int row = (int) coordinates.getY();
            int col = (int) coordinates.getX();
            if (row == endRow && col == endColumn) {
                break;
            }
            for (int i = 0; i < 4; i++) {
                int newRow = row + rowDelta[i];
                int newColumn = col + columnDelta[i];
                
                if (newRow < 0 || newColumn < 0) continue;
                if (newRow >= height || newColumn >= width) continue;
                if (table[newRow][newColumn] == null) continue;
                if (table[newRow][newColumn].getX() == -2) {
                    for (Portal portal : portals) {
                        if ((portal.getX() != newColumn || portal.getY() != newRow) 
                                && portal.getId() == table[newRow][newColumn].getY()) {
                            queue.add(new Point(portal.getX(), portal.getY()));
                            table[newRow][newColumn] = new Point(col, row);
                            table[portal.getY()][portal.getX()] = new Point(newColumn, newRow);
                            continue;
                        }
                    }
                }
                if (table[newRow][newColumn].getX() != -1) continue;

                queue.add(new Point(newColumn, newRow));
                table[newRow][newColumn] = new Point(col, row);
            }
        }
        int row = endRow;
        int col = endColumn;
        if (table[endRow][endColumn] == null  || table[endRow][endColumn].getX() == -1 
                || table[endRow][endColumn].getX() == -2) {
            return result;
        }
        result.add(new Point(endColumn, endRow));
        while (row != startRow || col != startColumn) {
            Point temp = table[row][col];
            result.add(temp);
            row = (int) temp.getY();
            col = (int) temp.getX();
        }
        Collections.reverse(result);
        result.remove(0);
        return result;
    }

    // Flood Fill algorithm using a queue from the players positions, 
    // returns list of adjacent squares of enemy that is further away from player than current square
    public List<Point> findEscape(int playerRow, int playerColumn, int enemyRow, int enemyColumn)  {
        Point[][] table = toTable();
        Queue<Point> queue = new LinkedList<Point>();
        List<Point> result = new ArrayList<>();
        queue.add(new Point(playerColumn, playerRow));
        int distance = 0;
        table[playerRow][playerColumn] = new Point(distance, distance);
        table[enemyRow][enemyColumn] = new Point(-1, -1);
        int[] columnDelta = {0, 0, 1, -1};
        int[] rowDelta = {1, -1, 0, 0};
        while (!queue.isEmpty()) {
            Point coordinates = queue.remove();
            int row = (int) coordinates.getY();
            int col = (int) coordinates.getX();
            for (int i = 0; i < 4; i++) {
                int newRow = row + rowDelta[i];
                int newColumn = col + columnDelta[i];
                if (newRow < 0 || newColumn < 0) continue;
                if (newRow >= height || newColumn >= width) continue;
                if (table[row][col] == null) continue;
                if (table[newRow][newColumn] == null) continue;
                if (table[newRow][newColumn].getX() == -2) {
                    for (Portal portal : portals) {
                        if ((portal.getX() != newColumn || portal.getY() != newRow) 
                                && portal.getId() == table[newRow][newColumn].getY()) {
                            distance = (int) table[row][col].getX() + 1;
                            queue.add(new Point(portal.getX(), portal.getY()));
                            table[newRow][newColumn] = new Point(distance, distance);
                            table[portal.getY()][portal.getX()] = new Point(distance, distance);
                            continue;
                        }
                    }
                }
                if (table[newRow][newColumn].getX() != -1) continue;
                distance = (int) table[row][col].getX() + 1;
                queue.add(new Point(newColumn, newRow));
                table[newRow][newColumn] = new Point(distance, distance);   
            }
        }

        for (int i = 0; i < 4; i++) {
            int newRow = enemyRow + rowDelta[i];
            int newColumn = enemyColumn + columnDelta[i];
            if (newRow < 0 || newColumn < 0) continue;
            if (newRow >= height || newColumn >= width) continue;
            if (table[newRow][newColumn] == null) continue;
            if (table[newRow][newColumn].getX() > table[enemyRow][enemyColumn].getX()) {
                result.add(new Point(newColumn, newRow));
            }
        }
        return result;
    }

    public void pause(){
        player.pause();
        if (player2 != null) { player2.pause(); }
        for (Enemy e: enemies){
            e.pause();
        }
    }

    public void resume(){
        player.resume();
        if (player2 != null) { player2.resume(); }
        for (Enemy e: enemies){
            e.resume();
        }
    }

    public int getPlayerScore(){
        return player.getScore();
    }

    public int getPlayer2Score(){
        if (player2 != null) { return player2.getScore();}
        else { return -1; }
    }
}
