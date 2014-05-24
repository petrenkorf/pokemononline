package client;

import client.ui.LoginUI;
import client.util.Display;
import java.awt.DisplayMode;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.Vector;

/**
 *
 * @author bruno.weig, petrisrf
 */
public class Pokemon extends Application {
    Game game = null;
    
    int fps = 40;
    int miliPerFrame = 1000 / fps; 
    
    ImageView screenView = new ImageView();
    
//    Timer timer = new Timer();
    TextArea textArea;
    
    LoginUI loginUI = null;
    
    @Override
    public void start(Stage stage) {
        communication();
        
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
        
        d.setCurrentDisplay(displayVector.firstElement());
        
        loginUI = new LoginUI(stage);
        loginUI.show();
        
//        // Objeto que controla o jogo
//        game = Game.getInstance();
//        game.init(screenView);
//
//        // Loop principal - executa o método run do objeto game a cada 1000/fps ms
//        timer.scheduleAtFixedRate(game, 0, miliPerFrame);   
//        
//        StackPane pane = new StackPane();
//        pane.getChildren().add(screenView);
//        
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                timer.cancel();
//            }
//        });
//        
//        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
//        Scene scene = new Scene(pane, d.getWidth(), d.getHeight());
//        
//        stage.setTitle("Pokemon Remake");
//        stage.setScene(scene);
//        stage.show();
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
