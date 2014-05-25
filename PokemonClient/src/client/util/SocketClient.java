/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.util;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author bruno.weig
 */
public class SocketClient {
    static String SERVER_IP;
    static int server_port = 8686;
    
    public boolean sendMessage(String message) {
        Socket s = null;
        
        PrintStream stream = null;
                
        try {
            s = new Socket(SERVER_IP, server_port);

            stream = new PrintStream(s.getOutputStream());

            System.out.println(message);

            stream.println(message);
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
        
        return true;   
    }
}
