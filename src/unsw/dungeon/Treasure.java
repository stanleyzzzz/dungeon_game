package unsw.dungeon;

public class Treasure extends Collectable{
    
    public Treasure(int x, int y){
        super(x, y);
    }

    @Override
    public void changeState(){
        isCollectedState.set(true);
    }
}