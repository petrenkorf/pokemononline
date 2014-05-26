package gameElement;

import com.sun.javafx.geom.Rectangle;

public class Character extends Sprite {
	
	protected Rectangle rect;
	
	public Character(){
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		//lol
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	public void setX(int _x){
		this.rect.x = _x;
	}
	
	public void setY(int _y){
		this.rect.y = _y;
	}
	
	public void setHeight(int _h){
		this.rect.height = _h;
	}
	
	public void setWidth(int _w){
		this.rect.height = _w;
	}
	
	public int getX(){
		return this.rect.x;
	}
	
	public int getY(){
		return this.rect.y;
	}

}
