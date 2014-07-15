package server;

import client.ui.AbstractUI;
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
        AbstractUI.init();
        AbstractUI.changeCurrentUI(new ServerUI());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
