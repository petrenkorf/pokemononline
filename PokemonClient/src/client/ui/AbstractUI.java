/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.util.Display;
import java.awt.DisplayMode;
import java.util.EmptyStackException;
import java.util.Stack;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig
 */
public abstract class AbstractUI {
    static Stack<AbstractUI> stackUI = new Stack<AbstractUI>();
    static Stage stageRef = null;
    
    String stageTitle = new String();
    Scene scene = null;

    static boolean fullscreen = false;

    public AbstractUI(Stage stage) {
        stageRef = stage;
    }
    
    static public void changeCurrentUI(AbstractUI ui) {
        stackUI.add(ui);
        stageRef.setScene(stackUI.peek().getScene());
        stageRef.show();
    }
    
    static public void previousUI() {
        try {
            stackUI.pop();
            
            stageRef.setScene(stackUI.peek().getScene());
            stageRef.show();
        } catch (EmptyStackException e) {
            System.out.println("UI Stack is Empty!");
        }
    }
    
    public String getStageTitle() {
        return stageTitle;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void setStageTitle(String stageHeader) {
        this.stageTitle = stageHeader;
    }
    
    public void setSceneContent(Parent node) {
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        
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
