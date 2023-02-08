package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        mainStage.setTitle("Dungeon Explorers");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TitleScreen.fxml"));
        loader.setController(new TitleScreenController(this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void changeSceneToDungeon(String levelName, double volumeScale, boolean hasMultiplayer, DifficultyMode difficulty) throws IOException  {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(levelName + ".json", hasMultiplayer, difficulty, volumeScale);
        DungeonController controller = dungeonLoader.loadController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void defeatScene(int player1Score, int player2Score) throws IOException {
        DefeatController defeatController = new DefeatController(this, player1Score, player2Score);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Defeat.fxml"));
        loader.setController(defeatController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void winScene(int player1Score, int player2Score) throws IOException {
        WinController winController = new WinController(this, player1Score, player2Score);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Win.fxml"));
        loader.setController(winController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void restart() throws IOException {
        start(mainStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
