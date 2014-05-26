package client;

import client.ui.AbstractUI;
import client.ui.LoginUI;
import client.util.Display;
import client.communication.SocketClient;
import java.awt.DisplayMode;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.util.Vector;

/**
 *
 * @author bruno.weig, petrisrf
 */
public class Pokemon extends Application {
    TextArea textArea;
    
    @Override
    public void start(Stage stage) {
        Display d = Display.getInstance();
        
        DisplayMode[] resolutions = d.getResolutions();
        Vector<DisplayMode> displayVector = new Vector<DisplayMode>();
        
        // Deixa somente as resoluções divisíveis exatamente pela dimensão do tile
        for (int i=0; i < resolutions.length; i++) {
            if ( resolutions[i].getWidth() % 32 == 0 && 
                 resolutions[i].getHeight() % 32 == 0 ) {
                displayVector.add(resolutions[i]);
            }
        }
        
        // Carrega o IP do servidor de um arquivo
        SocketClient.init();
        
        // Define a resolução
        d.setCurrentDisplay(displayVector.firstElement());
        
        // Layout corrente
        AbstractUI.changeCurrentUI(new LoginUI(stage));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
