/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author bruno.weig
 */
public class SocketClient {
    static final int INPUT_LINE_SIZE = 10 * 1024;
    
    static String serverAddress;
    static int serverPort = 8686;
    byte[] response = new byte[INPUT_LINE_SIZE];
    
    public static void init() {
        BufferedReader buf;
        
        try {
            buf = new BufferedReader(new FileReader("src/ip.txt"));
            
            serverAddress = buf.readLine();
            
            System.out.println("Address: " + serverAddress);
            
            buf.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public String sendMessage(String message) {
        Socket s = null;
        
        PrintStream stream = null;
                
        try {
            s = new Socket(serverAddress, serverPort);

            stream = new PrintStream(s.getOutputStream());

            stream.println(message);
            
            s.getInputStream().read(response);
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
        
        return new String(response);
    }
}
