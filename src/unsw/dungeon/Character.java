package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Character extends Entity {

    BooleanProperty alive = new SimpleBooleanProperty(true);

    public Character(int x, int y){
        super(x, y);
    }

    public BooleanProperty alive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive.set(alive);
    }

    public Boolean getAlive() {
        return alive.getValue();
    }

}