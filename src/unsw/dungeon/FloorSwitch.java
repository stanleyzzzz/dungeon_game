package unsw.dungeon;

public class FloorSwitch extends Entity {

    private boolean isActivated;
    
    public FloorSwitch(int x, int y){
        super(x, y);
        isActivated = false;
    }
    public boolean getIsActivated() {
        return isActivated;
    }
 
    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
}