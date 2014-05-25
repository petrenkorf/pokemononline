package gameElement;

public abstract class Sprite {
	protected int frameTotal;
	protected float transitionTime;
	protected int currentFrame;
	protected final int width = 20;
	protected final int height = 30;
	
	abstract public void draw();
	
	abstract public void update();
}
