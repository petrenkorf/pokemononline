/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokemon;

import java.util.Timer;
//import java.util.TimerTask;
import javafx.application.Application;
//import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
//import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
<<<<<<< HEAD
//import pokemon.map.Map;
=======
import client.map.Map;
import javafx.scene.image.WritableImage;
>>>>>>> c1117ccbcd129cd66b823668b55e955c90d1c074

/**
 *
 * @author bruno.weig
 */
public class Pokemon extends Application {
    TextArea textArea;
    
    ImageView screenView = new ImageView();
//    Map map = new Map();
    
    WritableImage screen = null;
    
    Timer timer = new Timer();
    
    @Override
    public void start(Stage primaryStage) {
<<<<<<< HEAD
        Button buttonSendMessage = new Button();
        buttonSendMessage.setText("Send Message");
//        
        textArea = new TextArea();
//        
        buttonSendMessage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Socket s = null;
                
                PrintStream stream = null;
                
                try {
//                s = new Socket("127.0.0.1", 8686);
                    s = new Socket("177.97.167.22", 8686);
                    
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
=======
//        Button buttonSendMessage = new Button();
//        buttonSendMessage.setText("Send Message");
//        
//        textArea = new TextArea();
//        
//        buttonSendMessage.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Socket s = null;
//                
//                PrintStream stream = null;
//                
//                try {
////                s = new Socket("127.0.0.1", 8686);
//                    s = new Socket("201.67.150.184", 8888);
////                    s = new Socket("177.156.167.60", 8686);
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
        
        screen = new WritableImage(800, 600);
>>>>>>> c1117ccbcd129cd66b823668b55e955c90d1c074
        
        map.loadMap();
        map.draw(screen.getPixelWriter());
        
//        Rectangle2D monitor;
//        
//        ObservableList<Screen> screens = Screen.getScreens();
//        
//        for (int i=0; i < screens.size(); i++) {
//            monitor = screens.get(i).getBounds();
//            
//            System.out.println("Resolution (" + monitor.getWidth() + ", " + monitor.getHeight() + ")");
//        }
        
        screenView.setImage(screen);
        
        StackPane pane = new StackPane();
        
<<<<<<< HEAD
        
        pane.getChildren().add(screenView);
        pane.getChildren().addAll(textArea, buttonSendMessage);
=======
        pane.getChildren().add(screenView);
//        pane.getChildren().addAll(textArea, buttonSendMessage);
>>>>>>> c1117ccbcd129cd66b823668b55e955c90d1c074
        
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
