package unsw.dungeon;

public class VulnerableState implements PlayerState {
    
    private String state;
	
	public VulnerableState() {
		state = "Vulnerable";
	}
	
	@Override
	public void setInvincibleState(Player player) {
		player.setPlayerState(new InvincibleState());
	}

	/**
	 * change to vulnerable state
	 */
	@Override
	public void setVulnerableState(Player player) {
        ;
	}

	@Override
	public String getCurrentState() {
		return state;
	}
}