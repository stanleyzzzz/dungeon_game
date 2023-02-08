package unsw.dungeon;

public interface Moveable {
    
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
    public boolean hasPath(int x, int y);
}