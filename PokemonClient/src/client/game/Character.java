package client.game;

import com.sun.javafx.geom.Rectangle;
import java.util.Calendar;
import javax.media.opengl.GLAutoDrawable;

public class Character extends Sprite {
    protected Rectangle position;
    protected Direction direction;
    int walkSpeed;
    int runSpeed;
    
    boolean moving;
    boolean walking;
    boolean running;

    public Character() {
        walkSpeed = 4;
        runSpeed = 8;
    }
    
    public SpriteAnim getCurrentSprite() {
        return animation.get(currentState).anim[direction.getValue()][currentFrame];
    }
    
    public int getCurrentStateSize() {
        return animation.get(currentState).anim[direction.getValue()].length;
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
        //lol
    }
    
    @Override
    protected void update() {
        if ( walking ) {
            if ( timer == null ) {
                lastTime = Calendar.getInstance().getTimeInMillis();
                currentTime = 0;
            }
            
            timer = Calendar.getInstance();
            
            int totalFrame = getCurrentStateSize();
            
            currentTime += timer.getTimeInMillis() - lastTime;
            lastTime = timer.getTimeInMillis();
            
            SpriteAnim sprite = getCurrentSprite();
            
//            System.out.println("Time: " + currentFrame + " - " + sprite.sx + " " 
//                                + sprite.sy + " " + sprite.time);
            
            if ( currentTime >= sprite.time ) {
                currentTime = 0;
                
                if ( ++currentFrame >= totalFrame ) {
                    currentFrame = 0;
                }
            }
        } else {
            currentFrame = 0;
        }
    }
    
    public void setX(int _x){
        this.position.x = _x;
    }

    public void setY(int _y){
        this.position.y = _y;
    }

    public void setHeight(int _h){
        this.position.height = _h;
    }

    public void setWidth(int _w){
        this.position.height = _w;
    }

    public int getX(){
        return this.position.x;
    }

    public int getY(){
        return this.position.y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isWalking() {
        return walking;
    }
}
