/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.util.Calendar;
import java.util.TimerTask;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// TODO: fazer

/**
 *
 * @author bruno.weig
 */
public class Game extends TimerTask {
    WritableImage screen = new WritableImage(800, 600);
    
    int tileWidth = 32;
    int tileHeight = 32;
    
    Calendar calendar;
    Calendar currentCalendar;
    long time;
    
    public Game(ImageView v) {
        v.setImage(screen);
        
        calendar = Calendar.getInstance();
    }
    
    @Override
    public void run() {
//        currentCalendar = Calendar.getInstance();
//        
//        time = currentCalendar.getTimeInMillis() - calendar.getTimeInMillis();
//        
//        calendar = currentCalendar;
                
//        System.out.println("Time: " + scheduledExecutionTime());
        
        draw();
    }
    
     private void clearScreen() {
        PixelWriter w = screen.getPixelWriter();
         
        for (int i=0; i < screen.getHeight(); i++) {
            for (int j=0; j < screen.getWidth(); j++) {
                w.setColor(j, i, Color.BLACK);
            }
        }
    }
    
    public void draw() {
        clearScreen();
    }
}
