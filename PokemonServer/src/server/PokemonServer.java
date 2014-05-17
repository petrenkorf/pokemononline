/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        
        StackPane root = new StackPane();
        root.getChildren().addAll(messagesHeader, messages);
        
        Scene scene = new Scene(root, 400, 500);
        
        primaryStage.setTitle("Message Server!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        server = new Thread(new PokemonSocket(args));
        server.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
