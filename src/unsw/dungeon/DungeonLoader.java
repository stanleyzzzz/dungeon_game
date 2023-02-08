package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.layout.HBox;
import unsw.dungeon.goals.CreateGoal;
import unsw.dungeon.goals.Goal;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private boolean hasMultiPlayer;
    protected DifficultyMode difficulty;
    private double volumeScale;
    protected SoundController soundController;
    protected InventoryLoader inventoryLoader;
    protected List<HBox> goals;
    protected GoalLoader goalLoader;

    public DungeonLoader(String filename, boolean hasMultiPlayer, DifficultyMode difficulty, double volumeScale) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        this.hasMultiPlayer = hasMultiPlayer;
        this.difficulty = difficulty;
        this.volumeScale = volumeScale;
        soundController = new SoundController(volumeScale);
        inventoryLoader = new InventoryLoader(hasMultiPlayer, difficulty);
        goals = new ArrayList<>();
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);
        dungeon.setDifficulty(difficulty);
        dungeon.setVolumeScale(volumeScale);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        JSONObject goalJSON = json.getJSONObject("goal-condition");
        
        Goal goal = CreateGoal.create(dungeon, goalJSON);
        dungeon.setGoal(goal);
        soundController.goalAchivedSound(dungeon);
        GoalLoader goalLoader = new GoalLoader(goal);
        goals = goalLoader.load();
        
        return dungeon;
    }


    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            if (hasMultiPlayer){
                Player player2 = new Player(dungeon, x, y);
                dungeon.setPlayer2(player2);
                onLoadPlayer2(player2);
            }
            //entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            //entity = wall;
            dungeon.addObstacle(wall);
            break;

        // Handle other possible entities
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            dungeon.addCollectable(treasure);
            break;

        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            dungeon.addCollectable(sword);
            break;
            
        case "invincibility":
            Invincibility invincibility = new Invincibility(x, y);
            onLoad(invincibility);
            dungeon.addCollectable(invincibility);
            break;
        
        case "key":
            Key key = new Key(x, y, json.getInt("id"));
            onLoad(key);
            dungeon.addCollectable(key);
            break;

        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            dungeon.addEnemy(enemy);
            break;

        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            onLoad(boulder);
            dungeon.addObstacle(boulder);
            dungeon.addBoulder(boulder);
            break;

        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(x, y);
            onLoad(floorSwitch);
            dungeon.addFloorSwitch(floorSwitch);
            break;

        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            dungeon.addObstacle(exit);
            break;

        case "door":
            Door door = new Door(x, y, json.getInt("id"), dungeon);
            onLoad(door);
            dungeon.addClosedDoor(door);
            dungeon.addObstacle(door);
            break;

        case "portal":
            Portal portal = new Portal(x, y, json.getInt("id"));
            onLoad(portal);
            dungeon.addPortals(portal);
            break;
        }

        //dungeon.addEntity(entity);
    }

    public abstract void onLoad(Player player);
    public abstract void onLoadPlayer2(Player player);
    public abstract void onLoad(Wall wall);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Invincibility invincibility);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Enemy enemy);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(FloorSwitch floorSwitch);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(Door door);
}
