/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.game.Game;
import client.util.Display;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author bruno.weig
 * 
 * Encapsula a scene de cada janela dentro de um JPanel do Swing
 * 
 */
public abstract class AbstractUI {
    static Stack<AbstractUI> stackUI = new Stack<AbstractUI>();
    
    String viewTitle = new String();
    protected Scene scene = null;

    static boolean fullscreen = false;
    
    static JFrame frame = null;
    static JFXPanel fxPanel = null;
    static GLCanvas canvas = null;

    public AbstractUI() {
        
    }
    
    protected void loadFXML() {
        Class<?> obj = this.getClass();
        
        String name = "/resource/view/" + obj.getSuperclass().getSimpleName()+ ".fxml";
        
        System.out.println(name);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
            loader.setController(this);
            
            Parent p = loader.load();

            setSceneContent(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void init() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
                System.out.println("InitShowGUI initiated!");
            }
        });
    }
    
    private static void initAndShowGUI() {
        // This method is invoked on Swing thread
        frame = new JFrame();
        
        fxPanel = new JFXPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fxPanel);
        frame.setVisible(true);
        
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        frame.setSize(new Dimension(d.getWidth(), d.getHeight()));
    }
    
    static public void changeCurrentUI(AbstractUI ui) {
        stackUI.add(ui);
        
        if ( stackUI.peek().getScene() != null )
            fxPanel.setScene(stackUI.peek().getScene());
        
        stackUI.peek().show();
    }
    
    /**
     * 
     * @param listener 
     */
    public void initGL(Game listener) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GLProfile gp = GLProfile.getDefault();

                GLCapabilities caps = new GLCapabilities(gp);
                caps.setDoubleBuffered(true);
                caps.setHardwareAccelerated(true);

                fxPanel.setVisible(false);

                if ( canvas == null ) {
                    canvas = new GLCanvas(caps);
                    frame.add(canvas);
                }
                
                canvas.setVisible(true);
                    
                canvas.addGLEventListener(listener);
                canvas.addKeyListener(listener);
            }
        });
    }
    
    static public void destroyGL() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fxPanel.setVisible(true);
                
                canvas.setVisible(fullscreen);
            }
        });
    }
    
    static public void previousUI() {
        try {
            stackUI.pop();
            fxPanel.setScene(stackUI.peek().getScene());
            stackUI.peek().show();
        } catch (EmptyStackException e) {
            System.out.println("UI Stack is Empty!");
        }
    }
    
    public String getViewTitle() {
        return viewTitle;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void show() {
        frame.setTitle(viewTitle);
    }
    
    public void setViewTitle(String title) {
        this.viewTitle = title;
    }
    
    public void setSceneContent(Parent node) {
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        
        scene = new Scene(node, d.getWidth(), d.getHeight());
        
        fxPanel.setScene(scene);
    }
    
    public void setFullscreen(boolean fullscreen) {
//        frame.setFullScreen(fullscreen);
        
    }
    
//    public void setFullScreen(DisplayMode displayMode) {  
//        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();  
//        device = environment.getDefaultScreenDevice();  
//
//        final JFrame frame = new JFrame();  
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//        frame.setUndecorated(true);  
//        frame.setIgnoreRepaint(true);  
//        frame.setResizable(false);  
//
//        device.setFullScreenWindow(frame);  
//
//        if (displayMode != null && device.isDisplayChangeSupported()) {  
//           setDisplayMode(displayMode);  
//           // fix for mac os x  
//           frame.setSize(displayMode.getWidth(), displayMode.getHeight());  
//        }  
//
//        frame.createBufferStrategy(2);  
//        sleep(500);  
//     }
//    
//    public void restoreScreen() {
//        if (getFullScreenWindow() != null) {
//            GraphicsEnvironment 
//        }
//        
//        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();  
//        device = environment.getDefaultScreenDevice();
//        getFullScreenWindow().dispose();
//        device.setFullScreenWindow(null);
//    }
    
    public boolean isFullscreen() {
        return fullscreen;
    }
}
