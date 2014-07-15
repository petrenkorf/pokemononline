/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package client.util;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

/**
*
* @author bruno.weig
*/
public class Display {
    GraphicsEnvironment e = null;
    static Display instance = null;
    boolean fullscreen = false;

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
    
    DisplayMode currentDisplayMode = null;
    
    private Display() {
        e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        currentDisplayMode = e.getDefaultScreenDevice().getDisplayMode();
    }
    
    static public Display getInstance() {
        if ( instance == null ) {
            instance = new Display();
        }
        
        return instance;
    }
    
    public DisplayMode[] getResolutions() {
        return e.getDefaultScreenDevice().getDisplayModes();
    }
    
    public void setCurrentDisplay(DisplayMode mode) {
        DisplayMode[] res = getResolutions();
        
        boolean found = false;
        
        for (DisplayMode r : res) {
            if (mode.getWidth() == r.getWidth() && mode.getHeight() == r.getHeight()) {
                found = true;
                break;
            }
        }
        
        if ( found ) {
            currentDisplayMode = mode;
            System.out.println("Found");
        } else {
            currentDisplayMode = e.getDefaultScreenDevice().getDisplayMode();
            System.out.println("Not Found");
        }
    }
    
    public DisplayMode getCurrentDisplayMode() {
        return currentDisplayMode;
    }
}