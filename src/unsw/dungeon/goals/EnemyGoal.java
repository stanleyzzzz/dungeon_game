package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class EnemyGoal implements Goal {

    private Dungeon dungeon;
    private boolean printFlag;
    private BooleanProperty achieved;
    
    public EnemyGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.printFlag = true;
        this.achieved = new SimpleBooleanProperty(false);
    }

    @Override
    public boolean check() {

        Boolean result =  dungeon.getNumEnemies() == 0;
        
        if (result && printFlag) {
            System.out.println("All enemies destroyed");
            achieved.setValue(true);
            printFlag = false;
        }
        return result;
    }

    @Override
	public int checkAsInt() {
        if (achieved.getValue()) return 1;
        else return 0;
	}

    @Override
    public String getInfo() {
        
        return "Beating all enemies";
    }

    public void add(Goal g){
		return;
    }
    
    @Override
	public boolean getAchieved() {
		return achieved.getValue();
	}

    public BooleanProperty getAchievedProperty() {
		return achieved;
	}
}
