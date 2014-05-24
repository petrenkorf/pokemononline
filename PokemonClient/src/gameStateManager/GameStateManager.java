package gameStateManager;

import java.util.Stack;

public class GameStateManager {
	Stack<GameState> stack = new Stack<GameState>();
	
	public GameStateManager(){
		this.stack.push(new GameStateLogin());
		//this.stack.push(new GameStateWorld());
		
	}
	
	public GameState top(){
		return this.stack.peek();
	}
}
