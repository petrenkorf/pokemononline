/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**/
/**/
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*working on server*/

/**
 *
 * @author bruno.weig
 */
public class PokemonServer extends Application {
    Thread server = null;
    
    @Override
    public void start(Stage primaryStage) {
        Label messagesHeader = new Label("Messages:");
        Label messages = new Label();
        messages.setPrefHeight(400);
        
        Object[] args = {messages};
        
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pokemon", 
                                                "postgres", "5624123");
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select * from users");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            
            rs.close();
            
            conn.close();
            
            System.out.println("Database connected: " + rs.getRow() + " results!");
        } catch (Exception e) {
            System.err.println("SQL Problem: " + e.getMessage());
        }
        
        StackPane root = new StackPane();
        root.getChildren().addAll(messagesHeader, messages);
        
        Scene scene = new Scene(root, 400, 500);
        
        primaryStage.setTitle("Message Server!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        server = new Thread(new PokemonSocket(args));
//        server.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
