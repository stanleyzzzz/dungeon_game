package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.IOException;


import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class TitleScreenController {

    @FXML
    private Button startButton;
    @FXML 
    private ChoiceBox<String> levelChoiceBox;
    @FXML 
    private Slider volumeSlider;
    @FXML 
    private CheckBox multiplayerCheckBox;
    @FXML
    private Text volumeText;
    @FXML
    private Button quitButton;
    @FXML
    private Button instructionButton;
    @FXML 
    private ChoiceBox<String> difficultyChoiceBox;
    @FXML
    private AnchorPane titlePane;
    @FXML
    private AnchorPane instructionPane;
    @FXML
    private Text instructionText;
    @FXML
    private AnchorPane parentPane;


    private String levelName; 
    private double volumeScale;
    private boolean hasMultiplayer;
    private DungeonApplication dungeonApplication;
    private DifficultyMode difficulty;

    public TitleScreenController(DungeonApplication dungeonApplication) {
        levelName = null;
        volumeScale = 1;
        hasMultiplayer = false;
        this.dungeonApplication = dungeonApplication;
    }

    @FXML
    public void initialize() {
        Background background = new Background(new BackgroundFill(Color.rgb(100, 70, 30, 0.85), null, new Insets(-10.0)));
        parentPane.setBackground(background);
        File[] listOfFiles = new File("dungeons").listFiles();
        for (int i = 0; i < listOfFiles.length; i++) 
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().startsWith("test")) {
                String name = listOfFiles[i].getName();
                levelChoiceBox.getItems().add(name.substring(0, name.length()-5));
            } 
            levelChoiceBox.setValue(levelChoiceBox.getItems().get(0));
        handleLevelSelection();
        for (DifficultyMode mode: DifficultyMode.values())
            difficultyChoiceBox.getItems().add(mode.toString());
        difficultyChoiceBox.setValue(difficultyChoiceBox.getItems().get(1));
        handleDifficulty();
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(0.5);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            volumeScale = (double)Math.round(newValue.floatValue()*100)/100; 
            volumeText.setText("Volume Scale: " + Double.toString(2*volumeScale) + "x");
        });
        instructionText.setText(
            "The player moves with the arrow keys. In multiplayer, the second player moves with WSAD.\n" +
            "Each dungeon will require you to complete at least one of the following four goals:\n" +
            "- Reaching an exit once all other nessesary goals are met\n" +
            "- Defeating every enemy\n" +
            "- Covering each floor switch with a boulder\n" +
            "- Collecting treasure\n\n" +
            "Doors prevent anything going through unless they are opened with their respective key.\n" +
            "However, keep in mind that you can only carry one key at a time!\n" +
            "A pair of portals can transport all moving entities between themselves. Enemies will use\n" +
            "this to their advantage, but players can stop them by blocking portals with boulders.\n\n" +
            "Enemies will kill players upon touch unless the player is holding a sword or under the\n" +
            "effects of an invincibility potion. In such cases, they will run away to save themseves!\n" +
            "Do keep in mind though that swords have limited durability.\n\n" +
            "Scores are given once a game ends, based on how much players interacted with the dungeon.\n" +
            "Difficulty modes can be used to make the game more interesting. Easy difficulty will increase\n" +
            "the duration of invincibility potions and decrease enemy movement speed, while Hard\n" +
            "difficulty will do the inverse. Winning on higher difficulties increases the player scores.\n"
            );
    }

    @FXML
    public void handleStartGame() throws IOException {
        if (levelName == null) return;
        dungeonApplication.changeSceneToDungeon(levelName, volumeScale, hasMultiplayer, difficulty);
    }

    @FXML
    public void handleLevelSelection() {
        levelName = levelChoiceBox.getValue();
    }

    @FXML
    public void handleHasMultiplayer() {
        hasMultiplayer = multiplayerCheckBox.isSelected();
        
    }

    @FXML
    public void handleQuitGame() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void handleInstructions() {
        // TODO
        titlePane.setVisible(!titlePane.isVisible());
        instructionPane.setVisible(!instructionPane.isVisible());
    }

    @FXML
    public void handleDifficulty() {
        difficulty = (DifficultyMode.valueOf(difficultyChoiceBox.getValue()));
    }

}

