package gameElement;

import java.util.Calendar;

public abstract class Sprite {
	protected int frameTotal;
	protected float transitionTime;
	protected int currentFrame;
	protected int width = 20;
	protected int height = 30;
	protected boolean repeat;
	protected Calendar timer;
	
	abstract public void draw();
	
	abstract public void update();
}
