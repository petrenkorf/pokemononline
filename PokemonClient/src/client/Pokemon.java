package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Timer;
//import java.util.TimerTask;
import javafx.application.Application;
//import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
//import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;
import client.map.Map;

/**
 *
 * @author bruno.weig
 */
public class Pokemon extends Application {
    TextArea textArea;
    
    ImageView screenView = new ImageView();
    Map map = new Map();
    
    Timer timer = new Timer();
    
    @Override
    public void start(Stage primaryStage) {
        Button buttonSendMessage = new Button();
        buttonSendMessage.setText("Send Message");
        
        textArea = new TextArea();
        
        buttonSendMessage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Socket s = null;
                
                PrintStream stream = null;
                
                try {
//                s = new Socket("127.0.0.1", 8686);
                    s = new Socket("201.67.150.184", 8888);
//                    s = new Socket("177.156.167.60", 8686);
                    
                    stream = new PrintStream(s.getOutputStream());
                    
                    System.out.println(textArea.getText());
                    
                    stream.println(textArea.getText());
                    
                    textArea.clear();
                } catch (IOException e) {
                    System.out.println("Problem connecting server!");
                } finally {
                    try {
                        if ( stream != null )
                            stream.close();
                        
                        if ( s != null )
                            s.close();
                    } catch (IOException e) {
                        System.err.println("Problem closing socket: " + e.getMessage());
                    }
                }
            }
        });
        
//        map.loadMap();
        
//        Image image = new Image("/resource/tileset_day.png");
//        
//        WritableImage writeImage = new WritableImage(800, 600);
//        
//        for (int i=0; i < tilesVertical; i++) {
//            for (int j=0; j < tilesHorizontal; j++) {
//                writeImage.getPixelWriter().setPixels(j*tileWidth, i * tileHeight, tileWidth, tileHeight, 
//                                                        image.getPixelReader(), j*tileWidth, i * tileHeight);
//            }
//        }
//        
//        screen.setImage(writeImage);
        
        StackPane pane = new StackPane();
        
//        pane.getChildren().add(screenView);
        pane.getChildren().addAll(textArea, buttonSendMessage);
        
        Scene scene = new Scene(pane, 800, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        timer.schedule(new Game(screenView), 500);        
//        timer.scheduleAtFixedRate(new Game(screenView), 0, 500);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
