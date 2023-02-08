package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import unsw.dungeon.goals.Goal;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane squares;

    @FXML
    private VBox inventory;

    @FXML
    private VBox goals;

    @FXML
    private HBox buttons;

    @FXML
    private HBox bottom;

    private List<ImageView> initialEntities;

    private List<HBox> initialInventory;

    private List<HBox> initialGoals;

    private Player player;

    private Player player2;

    private List<Boulder> boulders;

    private List<Door> closedDoors;

    private Dungeon dungeon;

    private Goal goal;

    private DungeonApplication dungeonApplication;

    private boolean paused = false;

    private Button pauseButton;

    private Button restartButton;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonApplication dungeonApplication, 
            List<HBox> initialInventory, List<HBox> initialGoals) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.player2 = dungeon.getPlayer2();
        this.boulders = dungeon.getBoulders();
        this.closedDoors = dungeon.getClosedDoors();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.dungeonApplication = dungeonApplication;
        this.initialInventory = initialInventory;
        System.out.println(initialGoals);
        this.initialGoals = initialGoals;
        this.goal = dungeon.getGoal();

        player.alive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean arg1, Boolean arg2) {
                try {
                    dungeonApplication.defeatScene(dungeon.getPlayerScore(), dungeon.getPlayer2Score());
                    dungeon.pause();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        if (player2 != null) {
            player2.alive().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean arg1, Boolean arg2) {
                    try {
                        dungeonApplication.defeatScene(dungeon.getPlayerScore(), dungeon.getPlayer2Score());
                        dungeon.pause();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        goal.getAchievedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean arg1, Boolean arg2) {
                try {
                    dungeonApplication.winScene(dungeon.getPlayerScore(), dungeon.getPlayer2Score());
                    dungeon.pause();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
    }

    @FXML
    public void initialize() {
        
        Background background = new Background(new BackgroundFill(Color.rgb(100, 70, 30, 0.85), null, new Insets(-5.0)));
        borderPane.setBackground(background);

        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        inventory.setSpacing(10);
        for (HBox hbox : initialInventory) {
            inventory.getChildren().add(hbox);
        }

        goals.setSpacing(10);
        for (HBox hbox : initialGoals) {
            goals.getChildren().add(hbox);
        }
    
        String cssLayout = "-fx-border-color: black;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n";
        inventory.setStyle(cssLayout);
        goals.setStyle(cssLayout);

        this.pauseButton = new Button("||");
        squares.add(pauseButton, dungeon.getWidth(), 1);
        pauseButton.toFront();

        EventHandler<ActionEvent> pauseEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!paused){
                    dungeon.pause();
                    paused = true;
                    pauseButton.setText("▶");
                    
                    ColorAdjust ca = new ColorAdjust();
                    ca.setBrightness(-0.75);
                    squares.setEffect(ca);

                } else {
                    dungeon.resume();
                    paused = false;
                    pauseButton.setText("||");

                    ColorAdjust ca = new ColorAdjust();
                    ca.setBrightness(0);
                    squares.setEffect(ca);

                    squares.requestFocus();
                }
            } 
        }; 
        pauseButton.setOnAction(pauseEvent);

        this.restartButton = new Button("⟲");
        squares.add(restartButton, dungeon.getWidth(), 0);
        restartButton.toFront();

        EventHandler<ActionEvent> restartEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    dungeon.pause();
                    dungeonApplication.restart();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } 
        }; 
        restartButton.setOnAction(restartEvent);
    }
    


    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case W:
            if (player2 != null) {  
                player2.moveUp();
            }
            break;
        case S:
            if (player2 != null) {
                player2.moveDown();
            }
            break;
        case A:
            if (player2 != null) {
                player2.moveLeft();
            }
            break;
        case D:
            if (player2 != null) {
                player2.moveRight();
            }
            break;
        default:
            break;
        }
    }

}

