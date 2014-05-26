package client.game;

import java.util.Calendar;
import javafx.scene.image.WritableImage;

public abstract class Sprite {
	protected int frameTotal;
	protected float transitionTime;
	protected int currentFrame;
	protected int width = 20;
	protected int height = 30;
	protected boolean repeat;
	protected Calendar timer;
	
	abstract public void draw(WritableImage _screen);
	
	abstract public void update();
}
