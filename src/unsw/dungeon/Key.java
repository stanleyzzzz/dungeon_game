package unsw.dungeon;

public class Key extends Collectable {

    private int id;

    public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void changeState() {
        isCollectedState.set(true);
    }

}