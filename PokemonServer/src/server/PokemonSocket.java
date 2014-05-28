/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.control.Label;

/**
 *
 * @author bruno.weig
 */
public class PokemonSocket implements Runnable {
    ServerSocket server = null;
    boolean executing = true;
    
    Label text = null;
    int port = 8686;
    
    PokemonRequisition p = null;
    
    BlockingQueue<Socket> requisitionQueue = new LinkedBlockingQueue<Socket>();
    
    public PokemonSocket() {
        p = new PokemonRequisition(requisitionQueue);
        
        Thread t = new Thread(p);
        t.start();
  
//        Platform.runLater(p);
        
        System.out.println("PokemonSocket");
    }
    
    public void stop() {
        p.stop();
        
        executing = false;
    }
    
    @Override
    public void run() {
        Socket requestSocket = null;
        
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error creating ServerSoket: " + e.getMessage());
        }
        
        while ( executing ) {
            try {
                requestSocket = server.accept();
                
                System.out.println("Received Socked!");

                requisitionQueue.put(requestSocket);
            } catch (IOException e) {
                System.err.println("Problem listening socket: " + e.getMessage());
            } catch (InterruptedException e ) {
                System.err.println("Problem listening socket: " + e.getMessage());
            }
        }
    }
}
