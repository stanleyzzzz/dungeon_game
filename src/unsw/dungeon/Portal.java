package unsw.dungeon;

import java.util.List;

public class Portal extends Entity{
    
    private int id;

    public Portal(int x, int y, int id) {
        super(x, y);
        this.id = id;
    } 
    
    public int getId() {
        return id;
    }

    public boolean canTeleport(Portal other, List<Boulder> boulders) {
        for (Boulder boulder: boulders)
            if (boulder.getBlockedPortal() != null && 
                    boulder.getBlockedPortal().getId() == other.getId()) 
                return false;
        if (getId() != other.getId()) return false;
        // if two same id portals are on top of each other, then it will not
        // pair them up because teleporting would be pointless
        if (getX() == other.getX() && getY() == other.getY()) return false;
        return true;
    }
}