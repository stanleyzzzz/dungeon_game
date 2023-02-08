package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;

public class ExitGoal implements Goal {

    private Dungeon dungeon;
    private boolean printFlag;
    private BooleanProperty achieved;
    
    public ExitGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.printFlag = true;
        this.achieved = new SimpleBooleanProperty(false);
    }

    @Override
    public boolean check() {
        
        Player player1 = dungeon.getPlayer();
        Player player2 = dungeon.getPlayer2();
        Exit exit = dungeon.getExit();
        boolean result;


        boolean resultPlayer1 =  (player1.getX() == exit.getX() && player1.getY() == exit.getY());
        if (player2 == null){
            result = resultPlayer1;
        } else {
            boolean resultPlayer2 =  (player2.getX() == exit.getX() && player2.getY() == exit.getY());
            result = resultPlayer1 && resultPlayer2;
        }

        if (result && printFlag) {
            System.out.println("Exit reached");
            achieved.set(true);
            printFlag = false;
        }
        return result;
    }

    @Override
	public int checkAsInt() {
        if (achieved.getValue()) return 1;
        else return 0;
	}

    @Override
    public String getInfo() {
        
        return "Exit";
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
