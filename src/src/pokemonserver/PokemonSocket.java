/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.pokemonserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.control.Label;

/**
 *
 * @author bruno.weig
 */
public class PokemonSocket implements Runnable {
    ServerSocket server = null;
    
    Label text = null;
    
    BufferedReader input = null;
    
    public PokemonSocket(Object[] args) {
        text = (Label)args[0];
    }
    
    @Override
    public void run() {
        Socket requestSocket = null;
        String message = new String();
        
        try {
            server = new ServerSocket(8888);
        } catch (IOException e) {
            System.err.println("Error creating ServerSoket: " + e.getMessage());
        }
        
        while ( true ) {
            try {
                requestSocket = server.accept();

                System.out.println("Received Socked!");

                input = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));

                message = input.readLine();
                
                System.out.println("Message from " + requestSocket.getInetAddress().toString() + " = " +
                                    message);

//                if ( message != null )
//                    text.setText(message);
            } catch (IOException e) {
                System.err.println("Problem listening socket: " + e.getMessage());
            } finally {
                try {
                    if ( input != null )
                        input.close();

                    if ( requestSocket != null )
                        requestSocket.close();
                    
                    if ( message.equals("close") ) {
                        if ( server != null )
                            server.close();

                        System.out.println("Socket server closed!");

                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Problem closing socket server: " + e.getMessage());
                }
            }
        }
    }
}
