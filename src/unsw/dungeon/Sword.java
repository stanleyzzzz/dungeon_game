package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Sword extends Collectable {

    private IntegerProperty durability = new SimpleIntegerProperty(5);
    
    public Sword(int x, int y){
        super(x, y);
    }

    @Override
    public void changeState() {
        isCollectedState.set(true);
    }
    
    public void useSword(){
        durability.setValue(getDurability()-1);
    }

    public int getDurability() {
        return durability.get();
    }
    
    public IntegerProperty getDurabilityProperty() {
        return durability;
    }
}