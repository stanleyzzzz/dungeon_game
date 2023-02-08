package test;

import org.json.JSONObject;

import unsw.dungeon.Dungeon;
import unsw.dungeon.MyDungeonLoader;

public class CreateDungeon {
    MyDungeonLoader loader;
    Dungeon dungeon;

    public CreateDungeon() {
        super();
    }


    /**
     * This creates a 5 x 5 dungeon, with walls on the edge.
     * The range of player movement is 3 x 3,
     * from [1,1] to [3,3]
     * 
     */
    public void createSimpleDungeon(){
        loader = new MyDungeonLoader(5, 5);
        dungeon = loader.load();
        loader.addEntity(dungeon, "player", 1, 1);
        
        loader.addEntity(dungeon, "wall", 0, 0);
        loader.addEntity(dungeon, "wall", 1, 0);
        loader.addEntity(dungeon, "wall", 2, 0);
        loader.addEntity(dungeon, "wall", 3, 0);
        loader.addEntity(dungeon, "wall", 4, 0);
        loader.addEntity(dungeon, "wall", 4, 1);
        loader.addEntity(dungeon, "wall", 4, 2);
        loader.addEntity(dungeon, "wall", 4, 3);
        loader.addEntity(dungeon, "wall", 4, 4);
        loader.addEntity(dungeon, "wall", 3, 4);
        loader.addEntity(dungeon, "wall", 2, 4);
        loader.addEntity(dungeon, "wall", 1, 4);
        loader.addEntity(dungeon, "wall", 0, 4);
        loader.addEntity(dungeon, "wall", 0, 1);
        loader.addEntity(dungeon, "wall", 0, 2);
        loader.addEntity(dungeon, "wall", 0, 3);

    }

    public void addEntity(String type, int x, int y){
        loader.addEntity(dungeon, type, x, y);
    }

    public void addEntity(String type, int x, int y, int id){
        loader.addEntity(dungeon, type, x, y, id);
    }

    public void setGoal(JSONObject goalJSON){
        loader.setGoal(dungeon, goalJSON);
    }

    public Dungeon getSimpleDungeon(){
        return dungeon;
    }
}

