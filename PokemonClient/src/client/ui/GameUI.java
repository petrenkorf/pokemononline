/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.game.Game;
import java.util.Timer;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author bruno.weig
 */
public class GameUI extends AbstractUI {
    Game game = null;
    Timer timer = null;
    
    int fps = 40;
    int miliPerFrame = 1000 / fps; 
    
    ImageView screenView = new ImageView();
    
    public GameUI(Stage stage) {
        super(stage);
        
        // Objeto que controla o jogo
        game = Game.getInstance();
        game.init(screenView);
        
        timer = new Timer();
        
        // Loop principal - executa o método run do objeto game a cada 1000/fps ms
        timer.scheduleAtFixedRate(game, 0, miliPerFrame);   
        
        StackPane pane = new StackPane();
        pane.getChildren().add(screenView);
        
        setSceneContent(pane);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.cancel();
            }
        });
    }
}
