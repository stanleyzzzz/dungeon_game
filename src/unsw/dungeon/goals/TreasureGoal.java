package unsw.dungeon.goals;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Collectable;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Treasure;

public class TreasureGoal implements Goal {
    
    private Dungeon dungeon;
    private boolean printFlag;
    private BooleanProperty achieved;
    
    public TreasureGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.printFlag = true;
        this.achieved = new SimpleBooleanProperty(false);
    }

    @Override
    public boolean check() {

        Player player1 = dungeon.getPlayer();
        Player player2 = dungeon.getPlayer2();

        int playerTreasure;
        if (player2 == null){
            playerTreasure = countTreasure(player1);
        } else {
            playerTreasure = countTreasure(player1) + countTreasure(player2);
        }

        Boolean result =  (playerTreasure == dungeon.getNumTreasure());
        
        if (result && printFlag) {
            System.out.println("All treasures collected");
            achieved.setValue(true);
            printFlag = false;
        }
        return result;
    }
    /*
    public boolean quiteCheck(){
        int playerTreasure = countTreasure(dungeon.getPlayer());
        return  (playerTreasure == dungeon.getNumTreasure());
    }
    */

    @Override
	public int checkAsInt() {
        if (achieved.getValue()) return 1;
        else return 0;
	}

    @Override
    public String getInfo() {
        return "Collect all treasure";
    }

    public int countTreasure(Player player){
        int count = 0;
        ArrayList<Collectable> collectables = player.getCollectables();
        for(Collectable c: collectables){
            if (c.getClass().getName().equals("unsw.dungeon.Treasure")) count++;
        }
        return count;
    }

    public void add(Goal g){
		return;
    }
    
    @Override
	public boolean getAchieved() {
		return achieved.getValue();
    }
    
    public BooleanProperty getAchievedProperty() {
		return achieved;
    }
}