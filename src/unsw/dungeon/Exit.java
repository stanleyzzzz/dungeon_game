package unsw.dungeon;

public class Exit extends Obstacle{

    public Exit(int x, int y) {
        super(x, y);
        
    }

    public void activate(){
        setCanPassThrough(true);
    }

}