/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.ui;

import client.game.Game;
import static client.ui.AbstractUI.frame;
import java.awt.Insets;
import javafx.scene.layout.StackPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author bruno.weig
 */
public class GameUI extends AbstractUI {
    Game game = null;
    
    int windowWidth = 960;
    int windowHeigth = 720;
    
    public GameUI() {
        setViewTitle("Game");
        
        game = new Game();
        
        initGL(game);
        setSceneContent(new StackPane());
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setResizable(false);
                
                Insets insets = frame.getInsets();
                
                // TODO Automatizar o dimensionamento não da janela mas de seu CONTEÚDO
                // Precisar somar o valor das bordas
                frame.setSize(windowWidth + insets.left - 2, windowHeigth + insets.top - 2);
                
//                frame.setSize(800 + insets.left + insets.right, 600 + insets.top + insets.bottom);
            }
        });
    }
}
