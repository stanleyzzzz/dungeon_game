package unsw.dungeon;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

import java.awt.Point;

public class Enemy extends Character implements Moveable {
    
    private Dungeon dungeon;
    private static double interval = 1;
    private Timeline timer;

    public Enemy(Dungeon dungeon, int x, int y){
        super(x, y);
        if (dungeon.getDifficulty() == DifficultyMode.Easy) interval = 1.3;
        else if (dungeon.getDifficulty() == DifficultyMode.Medium) interval = 1;
        else if (dungeon.getDifficulty() == DifficultyMode.Hard) interval = 0.4;
        this.dungeon = dungeon;
        timer = new Timeline(new KeyFrame(Duration.seconds(interval), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findMove();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    // Checks if a path can be made from Enemy position to Player position if a path can be made the Enenmy is moved toward the player, if not nothing happens. 
    public void findMove() {
        Player player1 = dungeon.getPlayer();
        Player player2 = dungeon.getPlayer2();
        if (player1 == null) {
            return;
        }
        List<Point> coordinates;
        
        if (player2 == null){
            coordinates = findMoveSinglePlayer(player1);
        } else {
            coordinates = findMoveMultiPlayer(player1, player2);
        }
        
        if (coordinates.size() == 0) {
            return;
        }
        move(coordinates.get(0));
    }

    private List<Point> findMoveSinglePlayer(Player player1){
        List<Point> coordinates;
        if (player1.getState() == "Invincible") {
            coordinates = dungeon.findEscape(player1.getY(), player1.getX(), this.getY(), this.getX());
        }
        else {
            coordinates = dungeon.findPath(this.getY(), this.getX(), player1.getY(), player1.getX());
        }
        return coordinates;
    }

    private List<Point> findMoveMultiPlayer(Player player1, Player player2){
        List<Point> coordinates1;
        List<Point> coordinates2;

        if (player1.getState() == "Invincible") {
            coordinates1 = dungeon.findEscape(player1.getY(), player1.getX(), this.getY(), this.getX());
        }
        else {
            coordinates1 = dungeon.findPath(this.getY(), this.getX(), player1.getY(), player1.getX());
        }
        if (player2.getState() == "Invincible") {
            coordinates2 = dungeon.findEscape(player2.getY(), player2.getX(), this.getY(), this.getX());
        }
        else {
            coordinates2 = dungeon.findPath(this.getY(), this.getX(), player2.getY(), player2.getX());
        }
        if (coordinates1.size() < coordinates2.size()) { return coordinates1; }
        else { return coordinates2; }
    }   


    public boolean fight() {
        Player player = dungeon.getPlayer();
        Player player2 = dungeon.getPlayer2();
        if (player == null) {
            return false;
        }
        if (getX() == player.getX() && getY() == player.getY()) {
            if (player.getState() == "Invincible" || player.useSword()) {
                // if player has sword enemy is killed
                dungeon.removeEnemy(this);
                setAlive(false);
                timer.stop();
                dungeon.checkGoal();
            }
            else {
                // if player does not have sword player is killed
                player.setAlive(false);
                dungeon.setPlayer(null);
                System.out.println("player1 Died");
            }
            return true;
        }
        if (player2 == null) {return false; }
        if (getX() == player2.getX() && getY() == player2.getY()) {
            if (player2.getState() == "Invincible" || player2.useSword()) {
                // if player has sword enemy is killed
                dungeon.removeEnemy(this);
                setAlive(false);
                timer.stop();
                dungeon.checkGoal();
            }
            else {
                // if player does not have sword player is killed
                player2.setAlive(false);
                dungeon.setPlayer2(null);
                System.out.println("player2 Died");
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPath(int x, int y){
        return dungeon.hasPath(x, y);
    }

    public void move(Point coordinate) {
        if (this.getX() == coordinate.getX()) {
            if (this.getY() > coordinate.getY()) {
                moveUp();
            }
            else {
                moveDown();
            }
        }
        else {
            if (this.getX() > coordinate.getX()) {
                moveLeft();
            }
            else {
                moveRight();
            }
        }
    }

    @Override
    public void moveUp() {
        if (getY() == 0) {return; }
        if (hasPath(getX(), (getY() - 1))){
            y().set(getY() - 1);
            dungeon.checkPortals(this);
        }
        fight();
        
    }

    @Override
    public void moveDown() {
        if (getY() == dungeon.getHeight() - 1) {return; }
        if (hasPath(getX(), (getY() + 1))){
            y().set(getY() + 1);
            dungeon.checkPortals(this);
        }
        fight();

    }

    @Override
    public void moveLeft() {
        if (getX() == 0) {return; }
        if (hasPath((getX() - 1), getY())){
            x().set(getX() - 1);
            dungeon.checkPortals(this);
        }
        fight();

    }

    @Override
    public void moveRight() {
        if (getX() == dungeon.getWidth() - 1) {return; }
        if (hasPath((getX() + 1), getY())){
            x().set(getX() + 1);
            dungeon.checkPortals(this);
        }
        fight();

    }

    public void pause(){
        timer.pause();
    }

    public void resume(){
        timer.play();
    }

}