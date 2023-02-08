package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.util.Duration;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Character implements Moveable {

    private Dungeon dungeon;
    private ArrayList<Collectable> collectables;
    private Timeline timeline;
    private PlayerState state;
    private int score;

    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.collectables = new ArrayList<>();
        this.state = new VulnerableState();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(
            Invincibility.getDuration() - 4 * dungeon.getDifficulty().ordinal()), 
            e -> setPlayerState(new VulnerableState())
        ));
        
    }

    @Override
    public boolean hasPath(int x, int y) {
        return dungeon.hasPath(x, y);
    }

    @Override
    public void moveUp() {
        List<Boulder> boulders = dungeon.getBoulders();
        for (Boulder boulder: boulders) {boulder.moveUp(this);}
        List<Door> closedDoors = dungeon.getClosedDoors();
        for (Door door: closedDoors) {
            door.checkDownForPlayer(this); 
            if (door.getCanPassThrough()) break;
        }
        if (getY() == 0) {return; }
        if (hasPath(getX(), (getY() - 1))){
            y().set(getY() - 1);
            collectItem();
            checkExit();
            fight();
            dungeon.checkPortals(this);
        }
        
    }

    @Override
    public void moveDown() {
        List<Boulder> boulders = dungeon.getBoulders();
        for (Boulder boulder: boulders) {boulder.moveDown(this);}
        List<Door> closedDoors = dungeon.getClosedDoors();
        for (Door door: closedDoors) {
            door.checkUpForPlayer(this); 
            if (door.getCanPassThrough()) break;
        }
        if (getY() == dungeon.getHeight() - 1) {return; }
        if (hasPath(getX(), (getY() + 1))){
            y().set(getY() + 1);
            collectItem();
            checkExit();
            fight();
            dungeon.checkPortals(this);
        }
    }

    @Override
    public void moveLeft() {
        List<Boulder> boulders = dungeon.getBoulders();
        for (Boulder boulder: boulders) {boulder.moveLeft(this);}
        List<Door> closedDoors = dungeon.getClosedDoors();
        for (Door door: closedDoors) {
            door.checkRightForPlayer(this); 
            if (door.getCanPassThrough()) break;
        }
        if (getX() == 0) {return; }
        if (hasPath((getX() - 1), getY())){
            x().set(getX() - 1);
            collectItem();
            checkExit();
            fight();
            dungeon.checkPortals(this);
        }
    }

    @Override
    public void moveRight() {
        List<Boulder> boulders = dungeon.getBoulders();
        for (Boulder boulder: boulders) {boulder.moveRight(this);}
        List<Door> closedDoors = dungeon.getClosedDoors();
        for (Door door: closedDoors) {
            door.checkLeftForPlayer(this); 
            if (door.getCanPassThrough()) break;
        }
        if (getX() == dungeon.getWidth() - 1) {return; }
        if (hasPath((getX() + 1), getY())){
            x().set(getX() + 1);
            collectItem();
            checkExit();
            fight();
            dungeon.checkPortals(this);
        }
    }

    public void checkExit(){
        Exit exit = dungeon.getExit();
        if (exit == null) return;
        if (exit.getX() == getX() && exit.getY() == getY()){
            dungeon.checkGoal();
        }
    }


    public void collectItem(){

        Collectable c = dungeon.findCollectable(getX(), getY());
        if (c == null) return;
        
        if (c.getClass().getName().equals("unsw.dungeon.Sword")){
            for (Collectable item: collectables){
                if (item.getClass().getName().equals("unsw.dungeon.Sword")) return;
            }
            score += 50;
        }

        if (c.getClass().getName().equals("unsw.dungeon.Invincibility")){
            score += 20;
            setPlayerState(new InvincibleState());
            timeline.stop();
            timeline.play();
        }

        if (c.getClass().getName().equals("unsw.dungeon.Key")){
            for (Collectable item: collectables){
                if (item.getClass().getName().equals("unsw.dungeon.Key")) return;
            }
            score += 30;
        }
        c.changeState();
        collectables.add(c);
        dungeon.removeCollectable(c);
    }

    public void removeCollectable(Collectable collectable) {
        collectables.remove(collectable);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int addition) {
        score += addition;
    }

    public Key getKey() {
        for (Collectable item: collectables)
            if (item.getClass().getName().equals("unsw.dungeon.Key")) 
                return (Key)item;
        return null; //no key 
    }

    public int getNumCollectable(String type){
        int count = 0;
        for (Collectable c: collectables){
            if (c.getClass().getName().equals("unsw.dungeon."+type)) {
                count++;
            }
        }
        return count;
    }

    public boolean useSword() {
        Collectable obj = null;
        for (Collectable c: collectables){
            if (c.getClass().getName().equals("unsw.dungeon.Sword")) {
                obj = c;
            }
        }
        if (obj != null) {
            Sword sword = (Sword) obj;
            sword.useSword();
            score += 20;
            if (sword.getDurability() == 0) collectables.remove(sword);
            return true;
        }
        return false;
    }

    public IntegerProperty getSwordPropety() {
        Collectable obj = null;
        for (Collectable c: collectables){
            if (c.getClass().getName().equals("unsw.dungeon.Sword")) {
                obj = c;
            }
        }
        if (obj != null) {
            Sword sword = (Sword) obj; 
            return sword.getDurabilityProperty();
        }
        return null;
    }

    public ArrayList<Collectable> getCollectables(){
        return collectables;
    }

    public String getState(){
        return state.getCurrentState();
    }

    public void setPlayerState(PlayerState state){
        this.state = state;
    }

    public void fight() {
        for (Enemy e : dungeon.getEnemies()) {
            if (e.fight()) {
                break;
            }
        }
    }

    public void pause(){
        timeline.pause();
    }

    public void resume(){
        timeline.play();
    }
}
