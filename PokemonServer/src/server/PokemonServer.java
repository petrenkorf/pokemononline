package server;

import db.SQLConnection;
import db.SQLConnection.SGBD;
import db.SQLQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author bruno.weig
 */
public class PokemonServer extends Application {
    SQLConnection db = null;
    Thread t = null;
    
    PokemonSocket s = null;
    
    @Override
    public void start(Stage primaryStage) {
        Label messagesHeader = new Label("Messages:");
        
        initDB();
//        test();
        
        StackPane root = new StackPane();
        root.getChildren().addAll(messagesHeader);
        
        Scene scene = new Scene(root, 400, 500);
        
        primaryStage.setTitle("Message Server!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        s = new PokemonSocket();
        t = new Thread(s);
        t.start();

        // Exclui thread
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                s.stop();
            }
        });
    }

    public void initDB() {
        db = SQLConnection.getInstance();
        
        db.setServer("localhost");
        db.setDbType(SGBD.Postgre);
        db.setUsername("postgres");
        db.setPassword("5624123");
        db.setDatabase("pokemon");
    }
    
    public void test() {
        SQLQuery query;
        
        try {
            db.connect();
            
            query = db.newQuery();
            
            query.select("*").from("users");
            ResultSet rs = db.execute(query);
            
            while ( rs.next() ) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("username"));
            }
            
            System.out.println("Database connected: " + rs.getRow() + 1 + " results!");
            
            rs.close();
            
            db.disconnect();
        } catch (SQLException e) {
            System.err.println("SQL Problem: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
