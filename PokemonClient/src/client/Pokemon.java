package client;

import client.ui.AbstractUI;
import client.util.Display;
import client.communication.SocketClient;
import client.ui.LoginUI;
import client.ui.GameUI;
import java.awt.DisplayMode;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author bruno.weig, petrisrf
 */
public class Pokemon extends Application {
    @Override
    public void start(Stage stage) {
        Display d = Display.getInstance();
        
        List<DisplayMode> res = d.getResolutionsPerRatio(Display.DisplayRatio.Ratio4_3);
        
        // Carrega o IP do servidor de um arquivo
        SocketClient.init();
        
        // Define a resolução
        if ( !res.isEmpty() )
            d.setCurrentDisplay(res.get(0));
        
        // Inicializa o frame do swing
        AbstractUI.init();
        
        // Layout corrente
        AbstractUI.changeCurrentUI(new GameUI());
//        AbstractUI.changeCurrentUI(new LoginUI());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
