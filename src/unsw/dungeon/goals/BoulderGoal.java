package unsw.dungeon.goals;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.FloorSwitch;

public class BoulderGoal implements Goal {

    private Dungeon dungeon;
    private boolean printFlag;
    private BooleanProperty achieved;

    
    public BoulderGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.printFlag = true;
        achieved = new SimpleBooleanProperty(false);
    }


    @Override
    public boolean check() {
        

        List<Boulder> boulders = dungeon.getBoulders();
        List<FloorSwitch> floorSwitches = dungeon.getSwitches();
        
        if (boulders.size() == 0 || floorSwitches.size() == 0) {
            return false;
        }

        boolean result = true;

        for (Boulder b: boulders){
            boolean boulderPositionCheck = false;
            for (FloorSwitch fs: floorSwitches){
                //System.out.println("boulder position: [" + b.getX() + "," + b.getY() + "]");
                //System.out.println("switch position: [" + fs.getX() + "," + fs.getY() + "]");
                if (b.getX() == fs.getX() && b.getY() == fs.getY()){
                    boulderPositionCheck = true;
                }
            }
            if (boulderPositionCheck == false) {result = false;}
        }

        if (result && printFlag) {
            System.out.println("All boulders on floorswitches");
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
        
        return "Pushing all boulders on floor switches";
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
