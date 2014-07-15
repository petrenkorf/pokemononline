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
        walkSpeed = 2;
        runSpeed = 4;
    }
    
    /**
     * @return Retorna o sprite corrente
     */
    public SpriteAnim getCurrentSprite() {
        return animation.get(currentState).anim[direction.getValue()][currentFrame];
    }
    
    /**
     * @return Quantidade de sprites de uma determinada animação (estado)
     */
    public int getCurrentStateSize() {
        return animation.get(currentState).anim[direction.getValue()].length;
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }
    
    @Override
    /**
     * Animação do sprite
     */
    protected void update() {
        if ( walking ) {
            if ( timer == null ) {
                lastTime = Calendar.getInstance().getTimeInMillis();
                currentTime = 0;
            }
            
            timer = Calendar.getInstance();

            currentTime += timer.getTimeInMillis() - lastTime;
            lastTime = timer.getTimeInMillis();
            
            SpriteAnim sprite = getCurrentSprite();
            int totalFrame = getCurrentStateSize();
            
//            System.out.println("Time: " + currentFrame + " - " + sprite.sx + " " 
//                                + sprite.sy + " " + sprite.time);

            System.out.println(currentFrame + " (" + sprite.sx + ", " + sprite.sy + ") = " 
                               + currentTime + "/" + sprite.time);
            
            // Quando ultrapassa o tempo de um sprite, zera o timer
            if ( currentTime >= sprite.time ) {
                currentTime = 0;
                
                // Zera índice do sprite corrente caso exceda o valor total
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
