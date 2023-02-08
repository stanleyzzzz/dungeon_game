package unsw.dungeon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class SoundController {
    private Map<String, AudioClip> audioClipMap = new HashMap<>();
    private double volumescale;
    public SoundController(double volumescale) {
        this.volumescale = volumescale;
        addClip("step2");
        addClip("sword");
        addClip("treasure");
        addClip("key");
        addClip("unlock");
        addClip("boulder");
        addClip("stab");
        addClip("death");
        addClip("potion");
        addClip("portal");
        addClip("goal");

    }

    public void addClip(String fileName) {
        String result;
        try {
            result = new File("./sounds/" + fileName + ".wav").toURI().toURL().toString();
            AudioClip clip = new AudioClip(result);
            clip.setVolume(volumescale);
            audioClipMap.put(fileName, clip);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
    }
    // COMMENT FOR CODE TO WORK ON VLAB
    public void playClip(String fileName) {
        if (audioClipMap.get(fileName).isPlaying()) {
            audioClipMap.get(fileName).stop();
            audioClipMap.get(fileName).play();
        }
        else {
            audioClipMap.get(fileName).play();
        }
    }

    public void playerSound(Player player) {
        player.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                playClip("step2");
                if (Math.abs(oldValue.intValue()-newValue.intValue())>1) {
                    playClip("portal");
                }
            }
        });
        player.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                playClip("step2");
                if (Math.abs(oldValue.intValue()-newValue.intValue())>1) {
                    playClip("portal");
                }
            }
        });
    }

    public void swordPickupSound(Sword sword) {
        sword.getIsCollectedState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("sword");
            }
        });
    }

    public void treasurePickupSound(Treasure treasure) {
        treasure.getIsCollectedState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("treasure");
            }
        });
    }

    public void keyPickupSound(Key key) {
        key.getIsCollectedState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("key");
            }
        });
    }
    
    public void unlockDoorSound(Door door) {
        door.getOpenState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, 
                    Boolean oldValue, Boolean newValue) {
                playClip("unlock");
            }
        });
    }

    public void boulderSound(Boulder boulder) {
        boulder.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                playClip("boulder");
            }
        });
        boulder.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                playClip("boulder");
            }
        });
    }

    public void stabSound(Enemy enemy) {
        enemy.alive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("stab");
            }
        });
    }

    public void deathSound(Player player) {
        player.alive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("death");
            }
        });
    }

    public void invincibilityPickupSound(Invincibility invincibility) {
        invincibility.getIsCollectedState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("potion");
            }
        });
    }

    public void goalAchivedSound(Dungeon dungeon) {
        dungeon.getAchieved().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                playClip("goal");
            }
        });
    }
}