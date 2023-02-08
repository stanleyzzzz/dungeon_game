package unsw.dungeon;

public interface PlayerState {

    public void setInvincibleState(Player player);
    public void setVulnerableState(Player player);
    public String getCurrentState();
}
