/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.util.Display;
import java.awt.DisplayMode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public abstract class AbstractUI {
    String stageTitle = new String();
    
    Stage stageRef = null;
    Scene scene = null;

    static boolean fullscreen = false;

    public AbstractUI(Stage stage) {
        stageRef = stage;
        System.out.println("Constructor");
    }
    
    public String getStageTitle() {
        return stageTitle;
    }

    public void setStageTitle(String stageHeader) {
        this.stageTitle = stageHeader;
    }
    
    public void setSceneContent(Parent node) {
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        
        System.out.println("Resulution: " + d.getWidth() + 
                           "x" + d.getHeight());
        
        scene = new Scene(node, d.getWidth(), d.getHeight());
        stageRef.setScene(scene);
    }
    
    public void show() {
        stageRef.setTitle(stageTitle);
        stageRef.show();
    }
    
    public void setFullscreen(boolean fullscreen) {
        stageRef.setFullScreen(fullscreen);
    }
    
    public boolean isFullscreen() {
        return fullscreen;
    }
}
