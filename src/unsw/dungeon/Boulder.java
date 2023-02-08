package unsw.dungeon;

public class Boulder extends Obstacle implements Moveable {

    private Dungeon dungeon;
    private Player player;
    private Player player1;
    private Player player2;
    private boolean hasMultiplayer;
    private Portal blockedPortal = null;

    public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
        this.player1 = dungeon.getPlayer();
        this.player2 = dungeon.getPlayer2();
        if (player2 == null) { hasMultiplayer = false;} 
        else { hasMultiplayer = true;}
        //this is in case a button is created on top of a switch
        dungeon.updateFloorSwitches();
    }

    @Override
    public boolean hasPath(int x, int y){
        return dungeon.hasPath(x, y);
    }

    public void moveUp(Player player){
        this.player = player;
        moveUp();
    }

    @Override
    public void moveUp() {
        
        // checks if player is under the boulder
        if (!(getY() + 1 == player.getY() && getX() == player.getX())) return;
        if (hasMultiplayer){
            if (player1.getX() == getX() && player1.getY()+1 == getY()) return;
            if (player2.getX() == getX() && player2.getY()+1 == getY()) return;
        }
        if (getY() == 0) {return; }
        if (hasPath(getX(), (getY() - 1))){
            y().set(getY() - 1);
            blockedPortal = null;
            dungeon.checkPortals(this);
            dungeon.updateFloorSwitches();
        }
        dungeon.checkGoal();
    }

    public void moveDown(Player player) {
        this.player = player;
        moveDown();
    }

    @Override
    public void moveDown() {
        // checks if player is above the boulder
        if (!(getY() - 1 == player.getY() && getX() == player.getX())) return;
        if (hasMultiplayer){
            if (player1.getX() == getX() && player1.getY()-1 == getY()) return;
            if (player2.getX() == getX() && player2.getY()-1 == getY()) return;
        }
        if (getY() == dungeon.getHeight() - 1) {return; }
        if (hasPath(getX(), (getY() + 1))){
            y().set(getY() + 1);
            blockedPortal = null;
            dungeon.checkPortals(this);
            dungeon.updateFloorSwitches();
        }
        dungeon.checkGoal();
    }

    public void moveLeft(Player player){
        this.player = player;
        moveLeft();
    }

    @Override
    public void moveLeft() {
        // checks if player is to the left of the boulder
        if (!(getX() + 1 == player.getX() && getY() == player.getY())) return;
        if (hasMultiplayer){
            if (player1.getX()+1 == getX() && player1.getY() == getY()) return;
            if (player2.getX()+1 == getX() && player2.getY() == getY()) return;
        }
        if (getX() == 0) {return; }
        if (hasPath((getX() - 1), getY())){
            x().set(getX() - 1);
            blockedPortal = null;
            dungeon.checkPortals(this);
            dungeon.updateFloorSwitches();
        }
        dungeon.checkGoal();
    }

    
    public void moveRight(Player player) {
        this.player = player;
        moveRight();
    }
    
    @Override
    public void moveRight() {
        // checks if player is to the right of the boulder
        if (!(getX() - 1 == player.getX() && getY() == player.getY())) return;
        if (hasMultiplayer){
            if (player1.getX()-1 == getX() && player1.getY() == getY()) return;
            if (player2.getX()-1 == getX() && player2.getY() == getY()) return;
        }
        if (getX() == dungeon.getWidth() - 1) {return; }
        if (hasPath((getX() + 1), getY())){
            x().set(getX() + 1);
            blockedPortal = null;
            dungeon.checkPortals(this);
            dungeon.updateFloorSwitches();
        }
        dungeon.checkGoal();
    }

    public Portal getBlockedPortal() {
        return blockedPortal;
    }

    public void setBlockedPortal(Portal portal) {
        blockedPortal = portal;
    }
}