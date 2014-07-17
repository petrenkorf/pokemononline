package server;

import client.ui.AbstractUI;
import client.util.Display;
import java.awt.DisplayMode;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import server.ui.ServerUI;

/**
 *
 * @author bruno.weig
 */
public class PokemonServer extends Application {
    @Override
    public void start(Stage primaryStage) {
        Display d = Display.getInstance();
        
        List<DisplayMode> res = d.getResolutionsPerRatio(Display.DisplayRatio.Ratio4_3);
        
        if ( !res.isEmpty() )
            d.setCurrentDisplay(res.get(0));
        
        AbstractUI.init();
        AbstractUI.changeCurrentUI(new ServerUI());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
