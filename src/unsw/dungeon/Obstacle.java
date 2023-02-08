package unsw.dungeon;

public abstract class Obstacle extends Entity {

    private boolean canPassThrough;
    public Obstacle(int x, int y){
        super(x, y);
        canPassThrough = false;
    }

    boolean getCanPassThrough(){
        return canPassThrough;
    }

    void setCanPassThrough(boolean canPassThrough) {
        this.canPassThrough = canPassThrough;
    }
}

