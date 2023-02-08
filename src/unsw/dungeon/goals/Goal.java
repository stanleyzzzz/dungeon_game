package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;

public interface Goal {

    public boolean check();
    public int checkAsInt();
    public String getInfo();
    public boolean getAchieved();
    
    // only available to AND, OR
    public void add(Goal g);
    public BooleanProperty getAchievedProperty();
}
