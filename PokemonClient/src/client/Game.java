/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import client.map.Map;
import client.util.Camera;
import client.util.Display;
import java.awt.DisplayMode;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.Vector;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author bruno.weig
 */
public class Game extends TimerTask {
    static Game game = null;
    
    WritableImage screen = null;
    ImageView screenView = null;
    
    int fps = 60;
    int timePerFrame = 1000 / fps;
    
    long time;
    Calendar currentCalendar;
    
//    Canvas screenView = new Canvas(640 + 32, 480 + 32);
    
    Map map = new Map();
    
    Rectangle hero = new Rectangle();

    private Game() {
        
    }
    
    public void init(ImageView v) {
        screenView = v;
        
        screenView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int speed = 4;
                
                switch ( event.getCode() ) {
                    case RIGHT:
                        if ( hero.getX() + speed <= map.getMapWidth() )
                            hero.setX(hero.getX() + speed);
                        
                        break;
                    case LEFT:
                        if ( hero.getX() >= speed )
                            hero.setX(hero.getX() - speed);
                        
                        break;
                    case UP:
                        if ( hero.getY() >= speed )
                            hero.setY(hero.getY() - speed);

                        break;
                    case DOWN:
                        if ( hero.getY() + speed <= map.getMapHeight() )
                            hero.setY(hero.getY() + speed);
                        
                        break;
                    default:
                        break;
                }
            }
        });
        
        loadResource();
        
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        
        screenView.setFocusTraversable(true);
        
        screenView. setImage(screen);
//        screenView.setClip(new Rectangle(0, 0, d.getWidth(), d.getHeight()));
        screenView.setViewport(new Rectangle2D(0, 0, d.getWidth(), d.getHeight()));
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
//        currentCalendar = Calendar.getInstance();
        
        // Atualiza a câmera com a posição do personagem
        Camera.getInstance().update(hero);
        
        // Desenha
        draw();
        
//        time = Calendar.getInstance().getTimeInMillis() - currentCalendar.getTimeInMillis();
//        System.out.println("Time: " + time);
    }
    
     private void clearScreen() {
        PixelWriter w = screen.getPixelWriter();
         
        for (int i=0; i < screen.getHeight(); i++) {
            for (int j=0; j < screen.getWidth(); j++) {
                w.setColor(j, i, Color.BLACK);
            }
        }
    }
     
     private void draw() {
//        clearScreen();
         
        map.draw(screen, screenView);
     }
     
     private void loadResource() {
        map.loadMap();
        
        Display d = Display.getInstance();
        
        DisplayMode[] lol = d.getResolutions();
        Vector<DisplayMode> displayVector = new Vector<DisplayMode>();
        
        // Deixa somente as resoluções divisíveis exatamente pela dimensão do tile
        for (int i=0; i < lol.length; i++) {
            if ( lol[i].getWidth() % map.getTileWidth() == 0 && 
                 lol[i].getHeight() % map.getTileHeight() == 0 ) {
                displayVector.add(lol[i]);
            }
        }
        
        DisplayMode mode = displayVector.firstElement();
        
        d.setCurrentDisplay(mode);
        
        Camera c = Camera.getInstance();
        
        // Seta limites do ambiente
        c.setEnvironmentBounds(map.getMapWidth(), map.getMapHeight());
        
        System.out.println("Resolution: " + c.getWidth() + "x" + c.getHeight());
        System.out.println("Map: " + map.getMapWidth() + "x" + map.getMapHeight());
        
        // Cria screen com folga para poder desenhar tiles a mais
        screen = new WritableImage(c.getWidth() + map.getTileWidth(), 
                                   c.getHeight() + map.getTileHeight());
        
        hero.setX(30 * map.getTileWidth());
        hero.setY(10 * map.getTileHeight());
        hero.setWidth(map.getTileWidth());
        hero.setHeight(map.getTileWidth());
        
        c.update(hero);
        
        map.draw(screen, screenView);
    }
}
