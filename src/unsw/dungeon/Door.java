package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Obstacle {
    private int id;
    private Dungeon dungeon;
    private Player player;
    private BooleanProperty openState = new SimpleBooleanProperty(false);;

    public Door(int x, int y, int id, Dungeon dungeon) {
        super(x, y);
        this.id = id;
        this.dungeon = dungeon;
        //this.player = dungeon.getPlayer();
    }

    public int getID() {
        return id;
    }
    
    private void checkForKey() {
        Key key = player.getKey();
        if (key != null && key.getId() == getID()) {
            setCanPassThrough(true);
            player.removeCollectable(key);
            dungeon.removeClosedDoor(this);
            dungeon.removeObstacle(this);
            openState.set(true);
        }
    }

    public void checkDownForPlayer(Player player) {
        this.player = player;
        if (player.getX() == getX() && player.getY()-1 == getY()) checkForKey();
    }

    public void checkUpForPlayer(Player player) {
        this.player = player;
        if (player.getX() == getX() && player.getY()+1 == getY()) checkForKey();
    }

    public void checkRightForPlayer(Player player) {
        this.player = player;
        if (player.getX()-1 == getX() && player.getY() == getY()) checkForKey();
    }

    public void checkLeftForPlayer(Player player) {
        this.player = player;
        if (player.getX()+1 == getX() && player.getY() == getY()) checkForKey();
    }

    public BooleanProperty getOpenState() {
        return openState;
    }
    
}
