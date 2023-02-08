package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class WinController {

    private DungeonApplication dungeonApplication;
    @FXML
    private Button restart;
    @FXML
    private Text score = new Text();

    private int player1Score;

    private int player2Score;
    

    public WinController(DungeonApplication dungeonApplication, int player1Score, int player2Score) {
        this.dungeonApplication = dungeonApplication;
        
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    @FXML
    public void initialize(){
        String result = "Player 1's Score: " + Integer.toString(player1Score);
        if (player2Score != -1) {
            result += "\n";
            result += "Player 2's Score: " + Integer.toString(player2Score);
        }
        score.setText(result);
    }

    @FXML
    public void handleRestart(ActionEvent e) throws IOException {
        dungeonApplication.restart();
    }
}