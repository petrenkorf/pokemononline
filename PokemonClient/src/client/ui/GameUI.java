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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

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
        
        // Fechar o jogo
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ( event.getCode() == KeyCode.ESCAPE ) {
                    event.consume();
                    
                    Action response = Dialogs.create().
                                title("Exit Game").
                                masthead(null).
                                message("Do you really want do close the game ?").
                                showConfirm();

                    System.out.println(response.toString());
                       
                    // Sair do jogo
                    if ( response == Dialog.Actions.YES ) {
                        timer.cancel();
                        game.save();
                        game.cancel();

                        timer = null;
                        game = null;

                        previousUI();
                    }
                }
            }
        });
        
        // Caso tentar fechar a janela antes de sair do jogo
        stageRef.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if ( timer != null ) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }
}
