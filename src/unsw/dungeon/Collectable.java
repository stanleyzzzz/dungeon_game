package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Collectable extends Entity {
    
    BooleanProperty isCollectedState = new SimpleBooleanProperty(false);

    public Collectable(int x, int y){
        super(x, y);
    }

    public BooleanProperty getIsCollectedState() {
        return isCollectedState;
    }

    abstract public void changeState();
}