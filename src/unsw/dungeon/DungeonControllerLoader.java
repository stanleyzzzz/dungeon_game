package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    
    // private SoundController soundController;
    // Images
    private Image playerImage;
    private Image player2Image;
    private Image wallImage;
    private Image treasureImage;
    private Image swordImage;
    private Image invincibilityImage;
    private Image keyImage;
    private Image portalImage;
    private Image enemyImage;
    private Image boulderImage;
    private Image floorSwitchImage;
    private Image exitImage;
    private Image doorImage;

    public DungeonControllerLoader(String filename, boolean hasMultiplayer, DifficultyMode difficulty,
            double volumeScale) throws FileNotFoundException {
        super(filename, hasMultiplayer, difficulty, volumeScale);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        player2Image = new Image((new File("images/gnome.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        invincibilityImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        floorSwitchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
        killCharacter(player, view);
        soundController.playerSound(player);
        soundController.deathSound(player);
    }

    @Override
    public void onLoadPlayer2(Player player2) {
        ImageView view = new ImageView(player2Image);
        addEntity(player2, view);
        killCharacter(player2, view);
        soundController.playerSound(player2);
        soundController.deathSound(player2);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
        collectCollectable(treasure, view);
        soundController.treasurePickupSound(treasure);
        ;
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
        collectCollectable(sword, view);
        soundController.swordPickupSound(sword);
    }

    @Override
    public void onLoad(Invincibility invincibility) {
        ImageView view = new ImageView(invincibilityImage);
        addEntity(invincibility, view);
        collectCollectable(invincibility, view);
        soundController.invincibilityPickupSound(invincibility);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
        collectCollectable(key, view);
        soundController.keyPickupSound(key);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
        killCharacter(enemy, view);
        soundController.stabSound(enemy);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
        soundController.boulderSound(boulder);
    }

    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(floorSwitchImage);
        addEntity(floorSwitch, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
        trackDoor(door, view);
        soundController.unlockDoorSound(door);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
                node.toFront();
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
                node.toFront();
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(DungeonApplication dungeonApplication) throws FileNotFoundException {
        return new DungeonController(load(), entities, dungeonApplication, inventoryLoader.load(), goals);
    }

    private void killCharacter(Character character, ImageView view) {
        character.alive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Image temp = view.getImage();
                view.setImage(null);
            }
        });
    }

    private void trackDoor(Door door, ImageView view) {
        door.getOpenState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, 
                    Boolean oldValue, Boolean newValue) {
                Image openDoor = new Image((new File("images/open_door.png")).toURI().toString());
                view.setImage(openDoor);
                for (ImageView image : entities) {
                    if (view.getX() == image.getX() && view.getX() == image.getX()) {
                        if (image.getImage() == playerImage) {
                            inventoryLoader.doorUnlock();
                        }
                        else {
                            inventoryLoader.doorUnlock2();
                        }
                    }
                }
            }
        });
    }

    private void collectCollectable(Collectable collectable, ImageView view) {
        collectable.getIsCollectedState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                ImageView temp = view;
                
                for (ImageView image : entities) {
                    if (GridPane.getColumnIndex(temp) == GridPane.getColumnIndex(image) && GridPane.getRowIndex(temp) == GridPane.getRowIndex(image)) {
                        if (playerImage.equals(image.getImage())) {
                            if (temp.getImage() == swordImage) {
                                inventoryLoader.swordPickup((Sword) collectable);
                            }
                            else if (temp.getImage() == invincibilityImage) {
                                inventoryLoader.invincibilityPickup((Invincibility) collectable);
                            }
                            else if (temp.getImage() == treasureImage) {
                                inventoryLoader.treasurePickup((Treasure) collectable);
                            }
                            else if (temp .getImage()== keyImage) {
                                inventoryLoader.keyPickup((Key) collectable);
                            } 
                            break;
                        } else if (player2Image.equals(image.getImage())) {
                            if (temp.getImage() == swordImage) {
                                inventoryLoader.swordPickup2((Sword) collectable);
                            }
                            else if (temp.getImage() == invincibilityImage) {
                                inventoryLoader.invincibilityPickup2((Invincibility) collectable);
                            }
                            else if (temp.getImage() == treasureImage) {
                                inventoryLoader.treasurePickup2((Treasure) collectable);
                            }
                            else if (temp .getImage()== keyImage) {
                                inventoryLoader.keyPickup2((Key) collectable);
                            } 
                            break;
                        }
                        
                    }
                }
                view.setImage(null); 
            }
        });
    }


}
