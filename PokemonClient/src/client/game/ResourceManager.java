/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_NEAREST;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno.weig
 */
public class ResourceManager {
    static final String resourceDir = "/resource/";
    static final String imageDir = "image/";
    
    static public Texture loadTexture(GLAutoDrawable d, String filepath, 
            String filetype, boolean flipVertical) throws IOException {
        Texture texture = TextureIO.newTexture(ResourceManager.class.getResourceAsStream(
                    resourceDir + imageDir + filepath), false, filetype);
        
        texture.setMustFlipVertically(true);
        texture.bind(d.getGL());
        
        GL gl = d.getGL();

        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        System.out.println("Loaded " + filepath + " texture!");

        return texture;
    }
}
