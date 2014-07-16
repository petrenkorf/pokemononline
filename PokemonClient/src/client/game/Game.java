package client.game;

import client.map.Map;
import client.ui.AbstractUI;
import client.util.Camera;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Calendar;
import javafx.application.Platform;
import javax.media.opengl.GL;
import static javax.media.opengl.GL2.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author bruno.weig
 */
public class Game implements GLEventListener, KeyListener {
    FPSAnimator fps = null;
    Texture image = null;
    
    Map map = null;
    Player player = null;
    TextRenderer textRenderer = null;
    
    Camera c = Camera.getInstance();
    
    long lastTime;
    long lastFrameTime = 0;
    
    int framesPerSecond = 40;
    
    public Game() {
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("Game Initiating...");
        
        GL gl = drawable.getGL();
        
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        gl.glPixelStorei(gl.GL_UNPACK_ALIGNMENT, 1);
        
        try {
            loadResource(drawable);
            
            System.out.println("Resource loaded!");
            
            fps = new FPSAnimator(drawable, framesPerSecond);
            fps.start();
        } catch (IOException e) {
            System.err.println("Init: " + e.getMessage());
        }
        
        System.out.println("Game Initiated");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.out.println("Game Dispose");
    }

    /**
     * Executado pelo FPSAnimator a cada 
     * 
     * @param drawable 
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        lastTime = Calendar.getInstance().getTimeInMillis();
        
        mainLoop(drawable);
        
        lastFrameTime = Calendar.getInstance().getTimeInMillis() - lastTime;
    }

    /**
     * Loop principal
     * 
     * @param drawable 
     */
    private void mainLoop(GLAutoDrawable drawable) {
        // Atualiza a câmera com a posição do personagem
        Camera.getInstance().update();
        
        player.update(map);
        
        // Desenha
        draw(drawable);
    }
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//        System.out.println("Reshape: " + width + "x" + height);
        
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        // TODO Alterar resoluções
        gl.glViewport(0, 0, width, height); 
        gl2.glMatrixMode(GL2.GL_PROJECTION);  
        gl2.glLoadIdentity(); 
        gl2.glOrtho(0, c. getWidth(), c.getHeight(), 0, -1.0, 1.0); 
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Carrega os recursos utilizados no jogo (imagem, audio, mapa)
     * 
     * @throws IOException 
     */
    private void loadResource(GLAutoDrawable canvas) throws IOException {
        Camera c = Camera.getInstance();
        
        // Carrega mapa
        map = new Map();
        map.loadMap("main_2.tmx", canvas, true);
        
        // Text
        textRenderer = new TextRenderer(new Font("Monospaced", Font.BOLD, 16));
        
        // Seta limites do ambiente
        c.setEnvironmentBounds(map.getMapWidth(), map.getMapHeight());
        
        // Jogador
        player = new Player();
        player.setX(32);
        player.setY(40);
//        player.setWidth(16);
//        player.setHeight(24);
        player.loadSpritesheet(canvas);
        
        // Define o foco da câmera
        c.setFocus(player);
        c.update();
        
//        System.out.println("Resolution: " + c.getWidth() + "x" + c.getHeight());
//        System.out.println("Map: " + map.getMapWidth() + "x" + map.getMapHeight());
//        System.out.println("Camera: " + c.getX() + "x" + c.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_ESCAPE:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Action response = Dialogs.create().
                                    title("Exit Game").
                                    masthead(null).
                                    message("Do you really want do close the game ?").
                                    showConfirm();

                        System.out.println(response.toString());

                        // Sair do jogo
                        if ( response == Dialog.Actions.YES ) {
                            fps.stop();
                            AbstractUI.destroyGL();
                            AbstractUI.previousUI();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        player.keyReleased(e);
    }
    
    private void draw(GLAutoDrawable canvas) {
        GL gl = canvas.getGL();
        GL2 gl2 = gl.getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glEnable(GL_BLEND);
        
        map.draw(canvas);
        player.draw(canvas);
        
        gl.glDisable(GL_BLEND);
        
//        String pos = "FrameTime: " + lastFrameTime + " | Position: " + hero.getX() + "x" + hero.getY();
        
        textRenderer.beginRendering(canvas.getWidth(), canvas.getHeight());
        textRenderer.setColor(Color.orange);
        
        String pos = "> FPS(" + lastFrameTime + ") / Position: " + player.getX() + "x" + player.getY() 
                   + " / Walking: " + player.isWalking();
        
        textRenderer.draw(pos, 20, canvas.getHeight() - 20);
        
        pos = "Frame (" + player.getCurrentFrame() + ") / Time (" + player.getCurrentTime() + ")";
        textRenderer.draw(pos, 20, canvas.getHeight() - 40);
        
        textRenderer.endRendering();
        
        gl2.glColor3f(1.0f, 1.0f, 1.0f);
     }
}
