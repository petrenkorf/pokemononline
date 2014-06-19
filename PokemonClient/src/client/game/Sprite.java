package client.game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.util.Calendar;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;
import javax.media.opengl.GLAutoDrawable;

public abstract class Sprite {
	protected int width = 16;
	protected int height = 24;
        
	protected int frameTotal;
	protected float transitionTime;
	
        protected int currentFrame;
	protected boolean repeat;
	protected Calendar timer;
        
        protected String filepath;
        protected transient Texture spritesheet;
	
        public static final String resourcePath = "/resource/image/sprite/";
        
	abstract public void draw(GLAutoDrawable drawable);
        
        static public Texture loadTexture(String filename, String filetype, GLAutoDrawable d) throws IOException {
            Texture t = TextureIO.newTexture(Sprite.class.getResourceAsStream(
                    resourcePath + filename), false, filetype);
            t.bind(d.getGL());
            
            GL gl = d.getGL();
            
//            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            
            return t;
        }
	
	abstract public void update();
}
