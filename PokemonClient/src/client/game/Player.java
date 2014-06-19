package client.game;

import client.util.Camera;
import com.jogamp.opengl.util.texture.TextureIO;

import com.sun.javafx.geom.Rectangle;
import java.io.IOException;
import java.io.Serializable;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Player extends Character implements Serializable {
    private long id;
    private String verificationCode;
    private String name;

    public Player() throws IOException {
        filepath = "gold_ash.png";
        
        spritesheet = null;
        
        rect = new Rectangle();
        
        rect.x = 20;
        rect.y = 20;
        rect.width = 20;
        rect.height = 20;
    }
    
    public Player(long id, String verificationCode) throws IOException {
        this.id = id;
        this.verificationCode = verificationCode;
        
        filepath = "gold_ash.png";
        spritesheet = null;
        
        rect = new Rectangle();
        rect.x = 20;
        rect.y = 20;
        rect.width = 20;
        rect.height = 20;
    }
    
    public void loadSpritesheet(GLAutoDrawable d) throws IOException {
        spritesheet = Sprite.loadTexture(filepath, TextureIO.PNG, d);
    }
    
    @Override
    public void draw(GLAutoDrawable drawable){
        Camera c = Camera.getInstance();
        
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        double clipX = (double)width / (double)spritesheet.getWidth();
        double clipY = (double)height  / (double)spritesheet.getHeight();
        
        spritesheet.enable(gl);
        spritesheet.bind(gl);
        
        gl2.glBegin(GL2.GL_QUADS);
            gl2.glTexCoord2d(0.0f, clipY);
            gl2.glVertex2d(c.getLocalX(), c.getLocalY());
            
            gl2.glTexCoord2d(clipX, clipY);
            gl2.glVertex2d(c.getLocalX() + rect.width, c.getLocalY());
            
            gl2.glTexCoord2d(clipX, 0.0f);
            gl2.glVertex2d(c.getLocalX() + rect.width, c.getLocalY() + rect.height);
            
            gl2.glTexCoord2d(0.0f, 0.0f);
            gl2.glVertex2d(c.getLocalX(), c.getLocalY()+ rect.height);
        gl2.glEnd();
        
        spritesheet.disable(gl);
 }

    @Override
    public void update() {
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
