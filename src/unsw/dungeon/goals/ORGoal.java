package unsw.dungeon.goals;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class ORGoal implements Goal {

	private BooleanProperty achieved;
	private Dungeon dungeon;
	private ArrayList<Goal> subgoals;
	
	public ORGoal(Dungeon dungeon) {
		this.achieved = new SimpleBooleanProperty(false);
		this.dungeon = dungeon;
		this.subgoals = new ArrayList<>();
	}

	public void add(Goal g){
		subgoals.add(g);
	}

    @Override
	public boolean check() {
		
		boolean result = false;

		for (Goal g : subgoals) {
			if (g.check()) {
				result = true;
			}
		}
		if (result) {achieved.setValue(true);; }
		return result;
	}
	/*
	public String getDetail() {
		String result =  "OR: [";
		for (Goal g : subgoals) {
			result += String.format(" %s ", g.getInfo());
		}
		result += "]";
		return result;
	}
	*/
	@Override
	public int checkAsInt() {
		int result = 0;
		for (Goal g : subgoals) result = g.checkAsInt();
		if (achieved.getValue()) result++;
		return result;
	}

	@Override
	public String getInfo() {
		
		return "OR";
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
