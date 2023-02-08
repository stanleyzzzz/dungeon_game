package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class InventoryLoader {
    private boolean hasMultiplayer;
    private DifficultyMode difficulty;
    private int space = 25;
    private ImageView smallSword;
    private ImageView smallInvincibility;
    private ImageView smallKey;
    private ImageView smallTreasure;
    private ImageView blank;
    private ImageView smallSword2;
    private ImageView smallInvincibility2;
    private ImageView smallKey2;
    private ImageView smallTreasure2;
    private ImageView blank2;
    private Text durability;
    private Text duration;
    private Text keyCount;
    private Text treasureCount;
    private Text durability2;
    private Text duration2;
    private Text keyCount2;
    private Text treasureCount2;
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> potionTick()));
    private Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), e -> potionTick2()));

    public InventoryLoader(boolean hasMultiplayer, DifficultyMode difficulty) {
        this.hasMultiplayer = hasMultiplayer;
        this.difficulty = difficulty;
        blank = new ImageView(new Image((new File("images/blank.png")).toURI().toString()));
        smallSword = new ImageView(new Image((new File("images/greatsword_1_new.png")).toURI().toString()));
        smallInvincibility = new ImageView(new Image((new File("images/brilliant_blue_new.png")).toURI().toString()));
        smallKey = new ImageView(new Image((new File("images/key.png")).toURI().toString()));
        smallTreasure = new ImageView(new Image((new File("images/gold_pile.png")).toURI().toString()));
        blank2 = new ImageView(new Image((new File("images/blank.png")).toURI().toString()));
        smallSword2 = new ImageView(new Image((new File("images/greatsword_1_new.png")).toURI().toString()));
        smallInvincibility2 = new ImageView(new Image((new File("images/brilliant_blue_new.png")).toURI().toString()));
        smallKey2 = new ImageView(new Image((new File("images/key.png")).toURI().toString()));
        smallTreasure2 = new ImageView(new Image((new File("images/gold_pile.png")).toURI().toString()));
        blank.setFitHeight(20);
        blank.setFitWidth(20);
        smallSword.setFitHeight(15);
        smallSword.setFitWidth(15);
        smallInvincibility.setFitHeight(20);
        smallInvincibility.setFitWidth(20);
        smallKey.setFitHeight(20);
        smallKey.setFitWidth(20);
        smallTreasure.setFitHeight(20);
        smallTreasure.setFitWidth(20);
        durability = new Text("0/5");
        duration = new Text("0/"+ (Invincibility.getDuration() - 4 * difficulty.ordinal()));
        keyCount = new Text("0");
        treasureCount = new Text("0");
        durability2 = new Text("0/5");
        duration2 = new Text("0/" + (Invincibility.getDuration() - 4 * difficulty.ordinal()));
        keyCount2 = new Text("0");
        treasureCount2 = new Text("0");
    }

    public List<HBox> load() {
        
        List<HBox> result = new ArrayList<>();
        Background background = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 1), null, new Insets(0)));
        HBox title = new HBox();
        title.setBackground(background);
        title.setSpacing(10);
        HBox.setMargin(smallSword, new Insets(5));
        Text items = new Text("Items");
        items.setFill(Color.WHITE);
        items.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        Text count = new Text(" Count");
        count.setFill(Color.WHITE);
        count.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        title.getChildren().add(items);
        title.getChildren().add(count);
        result.add(title);
        if (hasMultiplayer) {
            result.add(box(null, new Text("Player 1:")));
        }
        result.add(box(smallSword, durability));
        result.add(box(smallInvincibility, duration));
        result.add(box(smallKey, keyCount));
        result.add(box(smallTreasure, treasureCount));
        if (hasMultiplayer) {
            result.add(box(null, new Text("Player 2:")));
            result.add(box(smallSword2, durability2));
            result.add(box(smallInvincibility2, duration2));
            result.add(box(smallKey2, keyCount2));
            result.add(box(smallTreasure2, treasureCount2));
        }
        return result;
    }
    public void swordPickup(Sword sword) {
        durability.setText("5/5");
        sword.getDurabilityProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                String text = "";
                if (newValue == null) {
                    text += "0/5";
                }
                else {
                    text += newValue.toString() +"/5";
                    
                }
                durability.setText(text);
            }
        });
    }

    public void treasurePickup(Treasure treasure) {
        treasureCount.setText("" + (Integer.parseInt(treasureCount.getText()) + 1));
    }

    public void keyPickup(Key key) {
        keyCount.setText("1");
    }

    public void doorUnlock() {
        keyCount.setText("0");
    }

    public void invincibilityPickup(Invincibility invincibility) {
        int numCycle = Invincibility.getDuration() - 4 * difficulty.ordinal();
        duration.setText("" + numCycle + "/" + numCycle);
        timeline.setCycleCount(numCycle);
        timeline.play();
    }

    public void swordPickup2(Sword sword) {
        durability2.setText("5/5");
        sword.getDurabilityProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                String text = "";
                if (newValue == null) {
                    text += "0/5";
                }
                else {
                    text += newValue.toString() +"/5";
                    
                }
                durability2.setText(text);
            }
        });
    }

    public void treasurePickup2(Treasure treasure) {
        treasureCount2.setText("" + (Integer.parseInt(treasureCount2.getText()) + 1));
    }

    public void keyPickup2(Key key) {
        keyCount2.setText("1");
    }

    public void doorUnlock2() {
        keyCount2.setText("0");
    }

    public void invincibilityPickup2(Invincibility invincibility) {
        int numCycle = Invincibility.getDuration() - 4 * difficulty.ordinal();
        duration2.setText("" + numCycle + "/" + numCycle);
        timeline2.setCycleCount(numCycle);
        timeline2.play();
    }

    public void potionTick() {
        String[] arrOfStr = duration.getText().split("/", 2);
        String result = "" + (Integer.parseInt(arrOfStr[0])-1) + "/" +arrOfStr[1];
        duration.setText(result);
    }

    public void potionTick2() {
        String[] arrOfStr = duration2.getText().split("/", 2);
        String result = "" + (Integer.parseInt(arrOfStr[0])-1) + "/" +arrOfStr[1];
        duration2.setText(result);
    }

    public HBox box(ImageView image, Text textfield) {
        HBox hbox = new HBox();
        hbox.setSpacing(space);
        if (image != null) {
            HBox.setMargin(image, new Insets(5));
            hbox.getChildren().add(image);
        }
        hbox.getChildren().add(textfield);
        return hbox;
    }
}
