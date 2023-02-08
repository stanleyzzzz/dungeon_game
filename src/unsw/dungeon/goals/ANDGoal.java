package unsw.dungeon.goals;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class ANDGoal implements Goal {

	private BooleanProperty achieved;
	private Dungeon dungeon;
	private ArrayList<Goal> subgoals;
	
	public ANDGoal(Dungeon dungeon) {
		this.achieved = new SimpleBooleanProperty(false);
		this.dungeon = dungeon;
		this.subgoals = new ArrayList<>();
	}

	public void add(Goal g){
		subgoals.add(g);
	}

    @Override
	public boolean check() {

		boolean result = true;
		for (Goal g : subgoals) {
			if (g.check() == false) {
				result = false;
			}
		}

		//System.out.println(result);

		Goal subgoal1 = subgoals.get(0);
		Goal subgoal2 = subgoals.get(1);
		if (subgoal1 instanceof ExitGoal){
			if(subgoal2.check()) dungeon.activateExit();
		}
		if (subgoal2 instanceof ExitGoal){
			if(subgoal1.check()) dungeon.activateExit();
		}


		if (result) { achieved.setValue(true); }
		return result;
	}

	@Override
	public int checkAsInt() {
		int result = 0;
		for (Goal g : subgoals) result = g.checkAsInt();
		if (achieved.getValue()) result++;
		return result;
	}


	/*
	public String getDetail() {
		String result =  "AND: [";
		for (Goal g : subgoals) {
			result += String.format(" %s ", g.getInfo());
		}
		result += "]";
		return result;
	}
	*/
	@Override
	public String getInfo() {
		
		return "AND";
	}
	
	@Override
	public boolean getAchieved() {
		return achieved.getValue();
	}

	public ArrayList<Goal> getSubgoals() {
		return subgoals;
	}

	public BooleanProperty getAchievedProperty() {
		return achieved;
	}	
}
