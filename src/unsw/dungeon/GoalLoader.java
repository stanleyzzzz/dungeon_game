package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import unsw.dungeon.goals.*;

public class GoalLoader {
    private Goal goal;
    private List<HBox> result;

    public GoalLoader(Goal goal) {
        this.goal = goal;
    }

    public List<HBox> load() {
        Background background = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 1), null, new Insets(0)));

        result = new ArrayList<>();
        HBox titleBox = new HBox();
        titleBox.setBackground(background);
        Text title = new Text("   Goals");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        title.setFill(Color.WHITE);
        titleBox.getChildren().add(title);
        result.add(titleBox);
        loadHelper(goal, 0);
        return result;
    }
	
    private void loadHelper(Goal goal, int depth) {
        String output = "";
        HBox goalBox = new HBox();
        for (int i = 0; i < depth; i++)
            goalBox.getChildren().add(new Text("    "));
            output += " ";
        if (goal.getInfo() == "AND") {
            Text text = new Text(output + "You must complete both of the following" + "   ");
            CheckBox checkbox = new CheckBox();
            checkbox.setDisable(true);
            goalBox.getChildren().add(checkbox);
            goalBox.getChildren().add(text);
            result.add(goalBox);
            trackGoal(goal, checkbox);
            ANDGoal tempGoal = (ANDGoal) goal;
            loadHelper(tempGoal.getSubgoals().get(0), depth + 1);
            loadHelper(tempGoal.getSubgoals().get(1), depth + 1);
        }
        else if (goal.getInfo() == "OR") {
            Text text = new Text(output + "You only need to complete one of the following" + "   ");
            CheckBox checkbox = new CheckBox();
            checkbox.setDisable(true);
            goalBox.getChildren().add(checkbox);
            goalBox.getChildren().add(text);
            result.add(goalBox);
            trackGoal(goal, checkbox);
            ORGoal tempGoal = (ORGoal) goal;
            loadHelper(tempGoal.getSubgoals().get(0), depth + 1);
            loadHelper(tempGoal.getSubgoals().get(1), depth + 1);
        }
        else {
            Text text = new Text(output + goal.getInfo() + "   ");
            CheckBox checkbox = new CheckBox();
            checkbox.setDisable(true);
            goalBox.getChildren().add(checkbox);
            goalBox.getChildren().add(text);
            result.add(goalBox);
            trackGoal(goal, checkbox);
        }
    }

    private void trackGoal(Goal goal, CheckBox checkbox) {
        
        goal.getAchievedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, 
                    Boolean oldValue, Boolean newValue) {
                checkbox.setSelected(true);
            }
        });
    }
}