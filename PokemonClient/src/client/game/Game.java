package client.game;

import client.map.Map;
import client.ui.AbstractUI;
import client.util.Camera;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javax.media.opengl.GL;
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
public class Game extends TimerTask implements GLEventListener, KeyListener {
    static Game game = null;
    
    Timer timer = null;
    long time;
    long currentTime;
    
    Map map = new Map();
    
    Player hero = new Player();

    private Game() {
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl2.glColor3f(1.0f, 1.0f, 1.0f);
        
        gl2.glBegin(gl.GL_LINES);
            gl2.glVertex2i(50, 50);
            gl2.glVertex2i(150, 50);
            gl2.glVertex2i(150, 150);
            gl2.glVertex2i(50, 150);
        gl2.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        gl.glViewport(0, 0, width, height); 
        gl2.glMatrixMode(GL2.GL_PROJECTION);  
        gl2.glLoadIdentity(); 
        gl2.glOrtho(0, width, height, 0, -1.0, 1.0); 
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void init(Timer timer) {
        loadResource();
        
        this.timer = timer;
    }
    
    public void save() {
        
    }
    
    static public Game getInstance() {
        if ( game == null ) 
            game = new Game();
        
        return game;
    }
    
    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getInstance().mainLoop();
            }
        });
    }
    
    private void mainLoop() {
//        currentTime = Calendar.getInstance().getTimeInMillis();
        
        // Atualiza a câmera com a posição do personagem
        Camera.getInstance().update();
        
        // Desenha
//        draw();
        
//        time = Calendar.getInstance().getTimeInMillis() - currentTime;
//        System.out.println("Time: " + time);
    }
    
     private void draw() {
//        map.draw(screen);
//        hero.draw(screen);
     }
     
     private void loadResource() {
        Camera c = Camera.getInstance();
        
        // Carrega mapa
        map.loadMap("main.tmx");
        
        // Seta limites do ambiente
        c.setEnvironmentBounds(map.getMapWidth(), map.getMapHeight());
        
        System.out.println("Resolution: " + c.getWidth() + "x" + c.getHeight());
        System.out.println("Map: " + map.getMapWidth() + "x" + map.getMapHeight());
        
        // Cria screen com folga para poder desenhar tiles a mais
        
        hero.setX(200);//30 * map.getTileWidth());
        hero.setY(200); //* map.getTileHeight());
        hero.setWidth(map.getTileWidth());
        hero.setHeight(map.getTileWidth());
        
        c.setFocus(hero);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        
        System.out.println("Key Pressed!");
                
        int speed = 4;

        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_RIGHT:
                if ( hero.getX() + speed <= map.getMapWidth() )
                    hero.setX(hero.getX() + speed);

                break;
            case KeyEvent.VK_LEFT:
                if ( hero.getX() >= speed )
                    hero.setX(hero.getX() - speed);

                break;
            case KeyEvent.VK_UP:
                if ( hero.getY() >= speed )
                    hero.setY(hero.getY() - speed);

                break;
            case KeyEvent.VK_DOWN:
                if ( hero.getY() + speed <= map.getMapHeight() )
                    hero.setY(hero.getY() + speed);

                break;
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
                            timer.cancel();
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
    }
}
