package unsw.dungeon;

import org.json.JSONObject;

import unsw.dungeon.goals.CreateGoal;
import unsw.dungeon.goals.Goal;

public class MyDungeonLoader {

    int width, height;

    public MyDungeonLoader(int width, int height) {
        this.width = width;
        this.height = height;
        
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        Dungeon d = new Dungeon(width, height);
        return d;
    }


    public void addEntity(Dungeon dungeon, String type, int x, int y) {

        //Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            //onLoad(player);
            //entity = player;
            break;

        case "wall":
            Wall wall = new Wall(x, y);
            //onLoad(wall);
            //entity = wall;
            dungeon.addObstacle(wall);
            break;

        // Handle other possible entities
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            //onLoad(treasure);
            dungeon.addCollectable(treasure);
            break;

        case "sword":
            Sword sword = new Sword(x, y);
            //onLoad(sword);
            dungeon.addCollectable(sword);
            break;
            
        case "invincibility":
            Invincibility invincibility = new Invincibility(x, y);
            //onLoad(invincibility);
            dungeon.addCollectable(invincibility);
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            // onLoad(enemy);
            dungeon.addEnemy(enemy);
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            //onLoad(exit);
            dungeon.addObstacle(exit);
            break;
        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            //onLoad(boulder);
            dungeon.addObstacle(boulder);
            dungeon.addBoulder(boulder);
            break;
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(x, y);
            //onLoad(floorSwitch);
            dungeon.addFloorSwitch(floorSwitch);
            break;
        }
        //dungeon.addEntity(entity);
    }

    // method overloading for entities with id
    public void addEntity(Dungeon dungeon, String type, int x, int y, int id) {
        switch (type) {
            case "key":
                Key key = new Key(x, y, id);
                //onLoad(key);
                dungeon.addCollectable(key);
                break;
            case "door":
                Door door = new Door(x, y, id, dungeon);
                //onLoad(key);
                dungeon.addClosedDoor(door);
                dungeon.addObstacle(door);
                break;
            case "portal":
                Portal portal = new Portal(x, y, id);
                //onLoad(key);
                dungeon.addPortals(portal);
                break;
        }
    }

    public void setGoal(Dungeon dungeon, JSONObject goalJSON){
        Goal goal = CreateGoal.create(dungeon, goalJSON);
		dungeon.setGoal(goal);
    }
}