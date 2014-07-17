/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package client.util;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

/**
*
* @author bruno.weig
*/
public class Display {
    GraphicsEnvironment e = null;
    static Display instance = null;
    boolean fullscreen = false;
    
    public enum DisplayRatio {
        Ratio3_2,
        Ratio4_3,
        Ratio5_3,
        Ratio5_4,
        Ratio8_5,
        Ratio16_9,
        Ratio17_9,
        Ratio21_9
    }

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
    
    /**
     * Retorna uma lista com as resoluções com uma determinada relação de largura/altura
     * 
     * @param ratio
     * @return 
     */
    public List<DisplayMode> getResolutionsPerRatio(DisplayRatio ratio) {
        DisplayMode[] resolutions = getResolutions();
        
        List<DisplayMode> resolutionList = new ArrayList<>();
        
        double ratioFactor = 1.0;
        
        // Define o resultado das proporções de acordo com o parâmetro
        switch ( ratio ) {
            case Ratio3_2:
                ratioFactor = 3.0/2.0;
                break;
            case Ratio4_3:
                ratioFactor = 4.0/3.0;
                break;
            case Ratio5_3:
                ratioFactor = 5.0/3.0;
                break;
            case Ratio5_4:
                ratioFactor = 5.0/4.0;
                break;
            case Ratio8_5:
                ratioFactor = 8.0/5.0;
                break;
            case Ratio16_9:
                ratioFactor = 16.0/9.0;
                break;
            case Ratio17_9:
                ratioFactor = 17.0/9.0;
                break;
            case Ratio21_9:
                ratioFactor = 21.0/9.0;
                break;
        }
        
        double resolutionFactor;
        
        // Adiciona na lista as resoluções com a proporção
        for (int i=0; i < resolutions.length; i++) {
            resolutionFactor = (double)resolutions[i].getWidth() / (double)resolutions[i].getHeight();
            
            if ( resolutionFactor == ratioFactor ) {
                resolutionList.add(resolutions[i]);
            }
        }
        
        return resolutionList;
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
            System.out.println("Currente display resolution: " + mode.getWidth() + "x" + mode.getHeight());
        } else {
            currentDisplayMode = e.getDefaultScreenDevice().getDisplayMode();
            System.out.println(mode.getWidth() + "x" + mode.getHeight() + " not found!");
        }
    }
    
    public DisplayMode getCurrentDisplayMode() {
        return currentDisplayMode;
    }
}