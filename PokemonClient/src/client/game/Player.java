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
    
    // Distância para próximo tile
    private int walkPosition = 0;
    
    private boolean[] keys = new boolean[4];
    
    public Player() throws IOException {
        initPlayer();
    }
    
    public Player(long id, String verificationCode) throws IOException {
        this.id = id;
        this.verificationCode = verificationCode;
        
        initPlayer();
    }
    
    public void initPlayer() {
        spritesheet = null;
        filepath = "gold_ash.png";

        // Largura do tile + velocidade
        walkPosition = 16;
//        walkPosition = 16 - speed;
        
        direction = Direction.RIGHT;
        
        position = new Rectangle();
        position.x = 32;
        position.y = 32;
        position.width = 16;
        position.height = 24;
        
        this.keys[Direction.UP.getValue()] = false;
        this.keys[Direction.DOWN.getValue()] = false;
        this.keys[Direction.RIGHT.getValue()] = false;
        this.keys[Direction.LEFT.getValue()] = false;
    }
    
    public void loadSpritesheet(GLAutoDrawable canvas) throws IOException {
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
        
        double _leftSpace = c.getLocalX();
        
        // Subtrai 4 da coordenada y para compensar o espaçamento em branco de 4 pixels do sprite
        double _upSpace = c.getLocalY() - 4.0;
        
        gl2.glBegin(GL2.GL_QUADS);
            gl2.glTexCoord2d(spriteX, spriteY + spriteHeight);
            gl2.glVertex2d(_leftSpace, _upSpace);
            
            gl2.glTexCoord2d(spriteX + spriteWidth, spriteY + spriteHeight);
            gl2.glVertex2d(_leftSpace + position.width, _upSpace);
            
            gl2.glTexCoord2d(spriteX + spriteWidth, spriteY);
            gl2.glVertex2d(_leftSpace + position.width, _upSpace + position.height);
            
            gl2.glTexCoord2d(spriteX, spriteY);
            gl2.glVertex2d(_leftSpace, _upSpace + position.height);
        gl2.glEnd();
        
        spritesheet.disable(gl);
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_UP){
        	this.keys[Direction.UP.getValue()] = false;
        }
        
        if(key == KeyEvent.VK_DOWN){
        	this.keys[Direction.DOWN.getValue()] = false;
        }
        
        if(key == KeyEvent.VK_LEFT){
        	this.keys[Direction.LEFT.getValue()] = false;
        }
        
        if(key == KeyEvent.VK_RIGHT){
        	this.keys[Direction.RIGHT.getValue()] = false;
        }
        
      /*  if(this.keys[Direction.RIGHT.getValue()] == false &&
    		this.keys[Direction.LEFT.getValue()] == false &&
    		this.keys[Direction.UP.getValue()] == false &&
    		this.keys[Direction.DOWN.getValue()] == false ){
        	
        	walking = false;
        }*/
        
        /*
        switch ( key ) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                currentFrame = 0;
//                walking = false;
                
                break;
            default:
                break;
        }*/
    }
    
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_RIGHT:
            	this.keys[Direction.RIGHT.getValue()] = true;
            	if ( !walking ) {
                    walking = true;
                    
                    if ( direction != Direction.RIGHT ) {
                        setDirection(Direction.RIGHT);
                        currentTime = 0;
                        currentFrame = 0;
                    }
                }
                
                break;
            case KeyEvent.VK_LEFT:
            	this.keys[Direction.LEFT.getValue()] = true;
            	if ( !walking ) {
                    walking = true;
                    this.keys[Direction.LEFT.getValue()] = true;
                    if ( direction != Direction.LEFT ) {
                        setDirection(Direction.LEFT);
                        currentTime = 0;
                        currentFrame = 0;
                    }
                }
                
                break;
            case KeyEvent.VK_UP:
            	this.keys[Direction.UP.getValue()] = true;
            	if ( !walking ) {
                	this.keys[Direction.UP.getValue()] = true;
                	walking = true;

                    if ( direction != Direction.UP ) {
                        setDirection(Direction.UP);
                        currentTime = 0;
                        currentFrame = 0;
                    }
                }
                
                break;
            case KeyEvent.VK_DOWN:
            	this.keys[Direction.DOWN.getValue()] = true;
            	if ( !walking ) {
                    walking = true;
                    this.keys[Direction.DOWN.getValue()] = true;
                    if ( direction != Direction.DOWN ) {
                        setDirection(Direction.DOWN);
                        currentTime = 0;
                        currentFrame = 0;
                    }
                }
                
                break;
            default:
                break;
        }
    }
    
//    @Override
    public void update(Map map) {
        if ( walking ) {
            walkPosition -= walkSpeed;
            
            if ( walkPosition <= 0 ) {
                walkPosition = map.getTileWidth();
                
                if(this.keys[Direction.RIGHT.getValue()] == false &&
                		this.keys[Direction.LEFT.getValue()] == false &&
                		this.keys[Direction.UP.getValue()] == false &&
                		this.keys[Direction.DOWN.getValue()] == false ){
                    	
                    	walking = false;
                    }
            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
            switch ( direction ) {
                case DOWN:
                    if ( getY() + position.height + walkSpeed <= map.getMapHeight() )
                        setY(getY() + walkSpeed);
                    
                    break;
                case LEFT:
                    if ( getX() >= walkSpeed )
                        setX(getX() - walkSpeed);
                    
                    break;
                case UP:
                    if ( getY() >= walkSpeed )
                        setY(getY() - walkSpeed);
                    
                    break;
                case RIGHT:
                    if ( getX() + position.width + walkSpeed <= map.getMapWidth() )
                        setX(getX() + walkSpeed);
                     
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
