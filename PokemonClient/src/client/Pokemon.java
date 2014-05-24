package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import client.util.Display;
import java.awt.DisplayMode;
import java.util.Timer;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author bruno.weig, petrisrf
 */
public class Pokemon extends Application {
    Game game = null;
    
    int fps = 50;
    int miliPerFrame = 1000 / 40; 
    
    ImageView screenView = new ImageView();
    
    Timer timer = new Timer();
    TextArea textArea;
    
    @Override
    public void start(Stage stage) {
        communication();

        // Objeto que controla o jogo
        game = Game.getInstance();
        game.init(screenView);

        // Loop principal - executa o método run do objeto game a cada 1000/fps ms
        timer.scheduleAtFixedRate(game, 0, miliPerFrame);   
        
        StackPane pane = new StackPane();
        pane.getChildren().add(screenView);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.cancel();
            }
        });
        
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        Scene scene = new Scene(pane, d.getWidth(), d.getHeight());
        
        stage.setTitle("Pokemon Remake");
        stage.setScene(scene);
        stage.show();
    }
    
    public void communication() {
//        Button buttonSendMessage = new Button();
//        buttonSendMessage.setText("Send Message");
//        
//        buttonSendMessage.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                Socket s = null;
//                
//                PrintStream stream = null;
//                
//                try {
////                s = new Socket("127.0.0.1", 8686);
//                    s = new Socket("177.97.167.22", 8686);
//                    
//                    stream = new PrintStream(s.getOutputStream());
//                    
//                    System.out.println(textArea.getText());
//                    
//                    stream.println(textArea.getText());
//                    
//                    textArea.clear();
//                } catch (IOException e) {
//                    System.out.println("Problem connecting server!");
//                } finally {
//                    try {
//                        if ( stream != null )
//                            stream.close();
//                        
//                        if ( s != null )
//                            s.close();
//                    } catch (IOException e) {
//                        System.err.println("Problem closing socket: " + e.getMessage());
//                    }
//                }
//            }
//        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
