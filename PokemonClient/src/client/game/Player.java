package client.game;

import client.map.Map;
import client.util.Camera;

import com.sun.javafx.geom.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Player extends Character implements Serializable {
    public enum Actions {
        WALKING,
        BIKE_RIDING,
        FISHING
    }
    
    private long id;
    private String verificationCode;
    private String name;
    
    private int moveCount;

    public Player() throws IOException {
        filepath = "gold_ash.png";
        
        spritesheet = null;
        
        direction = Direction.RIGHT;
        
        position = new Rectangle();
        
        moveCount = 0;
        position.x = 20;
        position.y = 20;
        position.width = 20;
        position.height = 20;
    }
    
    public Player(long id, String verificationCode) throws IOException {
        this.id = id;
        this.verificationCode = verificationCode;
        
        filepath = "gold_ash.png";
        spritesheet = null;
        
        direction = Direction.RIGHT;
        
        position = new Rectangle();
        position.x = 20;
        position.y = 20;
        position.width = 20;
        position.height = 20;
    }
    
    public void loadSpritesheet(GLAutoDrawable canvas) throws IOException {
//        spritesheet = Sprite.loadTexture(filepath, TextureIO.PNG, canvas);
        
        loadSpriteAnimation("ash.xml", canvas);
    }
    
    @Override
    public void draw(GLAutoDrawable drawable){
        Camera c = Camera.getInstance();
        
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        SpriteAnim anim = getCurrentSprite();
        
        float spriteX, spriteY, spriteWidth, spriteHeight;
        
        float _width = (float)spritesheet.getWidth();
        float _height = (float)spritesheet.getHeight();
        
        spriteWidth = width / _width;
        spriteHeight = height / _height;
        
//        spriteX = (2 * width) / _width; 
//        spriteY = (spritesheet.getHeight() - (0 + 1) * height) / _height; 
        spriteX = anim.sx / _width; 
        spriteY = (spritesheet.getHeight() - (anim.sy+height)) / _height;
        
        spritesheet.enable(gl);
        spritesheet.bind(gl);
        
        gl2.glBegin(GL2.GL_QUADS);
            gl2.glTexCoord2d(spriteX, spriteY + spriteHeight);
            gl2.glVertex2d(c.getLocalX(), c.getLocalY());
            
            gl2.glTexCoord2d(spriteX + spriteWidth, spriteY + spriteHeight);
            gl2.glVertex2d(c.getLocalX() + position.width, c.getLocalY());
            
            gl2.glTexCoord2d(spriteX + spriteWidth, spriteY);
            gl2.glVertex2d(c.getLocalX() + position.width, c.getLocalY() + position.height);
            
            gl2.glTexCoord2d(spriteX, spriteY);
            gl2.glVertex2d(c.getLocalX(), c.getLocalY()+ position.height);
        gl2.glEnd();
        
        spritesheet.disable(gl);
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch ( key ) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                currentFrame = 0;
                walking = false;
                
                break;
            default:
                break;
        }
    }
    
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_RIGHT:
//                if ( !walking ) {
                    walking = true;

                    if ( direction != Direction.RIGHT ) {
                        setDirection(Direction.RIGHT);
                        currentFrame = 0;
                        currentTime = 0;
                    }
//                }
                
                break;
            case KeyEvent.VK_LEFT:
//                if ( !walking ) {
                    walking = true;

                    if ( direction != Direction.LEFT ) {
                        currentFrame = 0;
                        currentTime = 0;
                        setDirection(Sprite.Direction.LEFT);
                    }
//                }
                
                break;
            case KeyEvent.VK_UP:
//                if ( !walking ) {
                    walking = true;

                    if ( direction != Direction.UP ) {
                        setDirection(Sprite.Direction.UP);
                        currentTime = 0;
                        currentFrame = 0;
                    }
//                }
                
                break;
            case KeyEvent.VK_DOWN:
//                if ( !walking ) {
                    walking = true;
                    setDirection(Sprite.Direction.DOWN);

                    if ( direction != Direction.DOWN ) {
                        setDirection(Sprite.Direction.DOWN);
                        currentTime = 0;
                        currentFrame = 0;
                    }
//                }
                
                break;
            default:
                break;
        }
    }
    
//    @Override
    public void update(Map map) {
        int speed = 4;
        
        if ( walking ) {
            if ( moveCount++ < 4 ) {
                moveCount = 0;
            }
            
            switch ( direction ) {
                case DOWN:
                    if ( getY() + speed <= map.getMapHeight() )
                        setY(getY() + speed);
                    
                    break;
                case LEFT:
                    if ( getX() >= speed )
                        setX(getX() - speed);
                    
                    break;
                case UP:
                    if ( getY() >= speed )
                        setY(getY() - speed);
                    
                    break;
                case RIGHT:
                    if ( getX() + speed <= map.getMapWidth() )
                        setX(getX() + speed);
                    
                    break;
            }
            
            super.update();
        }
    }

    public long getId() {
        return id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
