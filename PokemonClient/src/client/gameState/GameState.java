package client.gameState;

public abstract class GameState {
	protected String stateName = "";
	
	public abstract void run();

	String getStateName(){
		return this.stateName;
	}
	
	
	
}
