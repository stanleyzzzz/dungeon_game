package unsw.dungeon;

public class Invincibility extends Collectable {

    private static int duration = 14;
    
    public Invincibility(int x, int y) {
        super(x, y);
    }

    @Override
    public void changeState() {
        isCollectedState.set(true);

    }

    public static int getDuration() {
        return duration;
    }
    
}