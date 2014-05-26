/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communication;

import client.game.Player;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *  Realiza o envio e tratamento das mensagens entre cliente/servidor
 * 
 * @author bruno.weig
 */
public class ClientRequest {
    public enum Command {
        UP("up"),
        RIGHT("right"),
        DOWN("down"),
        LEFT("left"),
        ACTION("action");
        
        String value;
        
        private Command(String value) {this.value = value;}
        
        @Override
        public String toString() {return value;}
    }
    
    public enum Request {
        LOGIN("login"),
        PRESS("press"),
        QUIT("quit");
        
        String value;
        
        private Request(String value) {this.value = value;}
        
        @Override
        public String toString() {return value;}
    }
    
    public enum Reply {
        OK("ok"),
        COLLIDING("colliding"),
        FAIL("fail");
        
        String value;
        
        private Reply(String value) {this.value = value;}
        
        @Override
        public String toString() {return value;}
    }
    
    Player player = null;
    
    static ClientRequest clientRequest = null;
    
    SocketClient socket = null;
    String message = "";
    String reply = "";
    
    private ClientRequest() {
        
    }
    
    static public ClientRequest getInstance() {
        if ( clientRequest == null )
            clientRequest = new ClientRequest();
        
        return clientRequest;
    }
    
    public Player login(String username, String password) {
        socket = new SocketClient();
        
        message = "0:0:" + Request.LOGIN + ":" + username + " " + password;
        
        System.out.println("Message: " + message);
        
        reply = socket.sendMessage(username);
        
        if ( reply.equals(Reply.FAIL.toString()) ) {
            return null;
        } else {
            // Deserializa objeto
            try {
                ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(reply.getBytes()));
                player = (Player)input.readObject();
            } catch (IOException e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
            }
        }
        
        return player;
    }
    
    public String press(Command command) {
        socket = new SocketClient();
        
        message = "press ";
        
        switch ( command ) {
            case UP:
                message += "up";
                break;
            case RIGHT:
                message += "right";
                break;
            case DOWN:
                message += "down";
                break;
            case LEFT:
                message += "left";
                break;
            case ACTION:
                message += "action";
                break;
        }
        
        return socket.sendMessage(message);
    }
    
    public String quit() {
        socket = new SocketClient();
        
        message = "quit";
        
        return socket.sendMessage(message);
    }
}
