/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.game.Game;
import java.util.Timer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author bruno.weig
 */
public class GameUI extends AbstractUI {
    Game game = null;
    Timer timer = null;
    
    int fps = 40;
    int miliPerFrame = 1000 / fps; 
    
    public GameUI() {
        setViewTitle("Player");
        
        // Objeto que controla o jogo
        timer = new Timer();
        
        game = Game.getInstance();
        game.init(timer);
        
        initGL(game);
        
        setSceneContent(new StackPane());
        
        // Loop principal - executa o método run do objeto game a cada 1000/fps ms
        timer.scheduleAtFixedRate(game, 0, miliPerFrame);
    }
}
