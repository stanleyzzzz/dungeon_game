package unsw.dungeon;

public class InvincibleState implements PlayerState{
    
    String state;
	
	public InvincibleState() {
		state = "Invincible";
	}
	
	@Override
	public void setInvincibleState(Player player) {
        ;
    }

	/**
	 * change player state to vulnerable state
	 */
	@Override
	public void setVulnerableState(Player player) {
		player.setPlayerState(new VulnerableState());
	}

	@Override
	public String getCurrentState() {
		return state;
	}

}