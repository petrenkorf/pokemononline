package gameElement;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import com.sun.javafx.geom.Rectangle;

public class Player extends Character {
	private Image spritesheet;
	
	public Player(){
		this.rect = new Rectangle();
		this.spritesheet = new Image("/resource/image/sprite/ash.png");
		this.rect.x = 20;
		this.rect.y = 20;
		this.rect.width = 20;
		this.rect.height = 20;
	}
	
	@Override
	public void draw(WritableImage _screen){
		 System.out.print("x=" + this.rect.x + ", y=" + this.rect.y + ", w= " + this.rect.width + ", h= " + this.rect.height);
			
		_screen.getPixelWriter().setPixels(this.rect.x, this.rect.y, this.rect.width, this.rect.height,
                spritesheet.getPixelReader(), 0, 0);
     }
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	
}
