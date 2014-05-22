package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import client.map.Map;
import client.util.Camera;
import client.util.Display;
import java.awt.DisplayMode;
import java.util.Vector;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author bruno.weig
 */
public class Pokemon extends Application {
    TextArea textArea;
    
    ImageView screenView = new ImageView();
//    Canvas screenView = new Canvas(640 + 32, 480 + 32);
    
    Map map = new Map();
    
    Rectangle hero = new Rectangle();
    
    WritableImage screen = null;
    
//    Timer timer = new Timer();
    
    @Override
    public void start(Stage stage) {
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
        
        loop();
        
//        screenView.setOnKeyPressed(new EventHandler<KeyEvent>() {
        screenView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int speed = 4;
                
                switch ( event.getCode() ) {
                    case RIGHT:
                        if ( hero.getX() + speed <= map.getMapWidth() )
                            hero.setX(hero.getX() + speed);
                        
                        break;
                    case LEFT:
                        if ( hero.getX() >= speed )
                            hero.setX(hero.getX() - speed);
                        
                        break;
                    case UP:
                        if ( hero.getY() >= speed )
                            hero.setY(hero.getY() - speed);

                        break;
                    case DOWN:
                        if ( hero.getY() + speed <= map.getMapHeight() )
                            hero.setY(hero.getY() + speed);
                        
                        break;
                    default:
                        break;
                }
                
                // Atualiza a câmera com a posição do personagem
                Camera.getInstance().update(hero);
                
                map.draw(screen, screenView);
            }
        });
        
        DisplayMode d = Display.getInstance().getCurrentDisplayMode();
        
        // Se tirar o javafx não reconhece o evento de tecla pressionada
        screenView.setFocusTraversable(true);
        
        screenView. setImage(screen);
//        screenView.setClip(new Rectangle(0, 0, d.getWidth(), d.getHeight()));
        screenView.setViewport(new Rectangle2D(0, 0, d.getWidth(), d.getHeight()));
        
        StackPane pane = new StackPane();
        
        pane.getChildren().add(screenView);
//        pane.getChildren().addAll(textArea, buttonSendMessage);
        
        Scene scene = new Scene(pane, d.getWidth(), d.getHeight());
        
        stage.setTitle("Pokemon!");
        stage.setScene(scene);
        stage.show();
        
//        timer.schedule(new Game(screenView), 500);        
//        timer.scheduleAtFixedRate(new Game(screenView), 0, 500);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loop() {
        map.loadMap();
        
        Display d = Display.getInstance();
        DisplayMode[] lol = d.getResolutions();
        Vector<DisplayMode> displayVector = new Vector<DisplayMode>();
        
        // Deixa somente as resoluções divisíveis exatamente pela dimensão do tile
        for (int i=0; i < lol.length; i++) {
            if ( lol[i].getWidth() % map.getTileWidth() == 0 && 
                 lol[i].getHeight() % map.getTileHeight() == 0 ) {
                displayVector.add(lol[i]);
            }
        }
        
        DisplayMode mode = displayVector.firstElement();
        
//        screenView.setWidth(mode.getWidth());
//        screenView.setHeight(mode.getHeight());
        
//        DisplayMode mode = d.getResolutions()[1];
        d.setCurrentDisplay(mode);
        
        Camera c = Camera.getInstance();
        
        // Seta limites do ambiente
        c.setEnvironmentBounds(map.getMapWidth(), map.getMapHeight());
        
        System.out.println("Resolution: " + c.getWidth() + "x" + c.getHeight());
        System.out.println("Map: " + map.getMapWidth() + "x" + map.getMapHeight());
        
        // Cria screen com folga para poder desenhar tiles a mais
        screen = new WritableImage(c.getWidth() + 32, c.getHeight() + 32);
        
        hero.setX(30 *  map.getTileWidth());
        hero.setY(10 * map.getTileHeight());
        hero.setWidth(32);
        hero.setHeight(32);
        
        c.update(hero);
        c.update(hero);
        
        map.draw(screen, screenView);
    }
}
