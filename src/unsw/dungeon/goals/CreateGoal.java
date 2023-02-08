package unsw.dungeon.goals;

import org.json.JSONObject;

import unsw.dungeon.Dungeon;

public class CreateGoal {

	
	
	public static Goal create(Dungeon dungeon, JSONObject JSON) {

		String goalString = JSON.getString("goal");
        

		switch (goalString) {
		case "exit":
			return new ExitGoal(dungeon);
		case "enemies":
			return new EnemyGoal(dungeon);
		case "boulders":
			return new BoulderGoal(dungeon);
		case "treasure":
			return new TreasureGoal(dungeon);
		}

		// Composite goals

		Goal node = null;
		switch (goalString) {
			case "AND":
				node = new ANDGoal(dungeon);
				break;
			case "OR":
				node = new ORGoal(dungeon);
				break;
		}

		for (Object subGoalJSON : JSON.getJSONArray("subgoals")) {
			node.add(create(dungeon, (JSONObject) subGoalJSON));
		}

		return node;
	}

}