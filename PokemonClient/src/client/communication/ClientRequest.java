/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communication;

import client.game.Player;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

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
        
        MessageDigest md = null;
            
        String passwordHash = "";

        // Gera o hash md5 da senha digitada pelo usuário
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            passwordHash = Hex.encodeHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
        }
        
        message = "0:0:" + Request.LOGIN + ":" + username + " " + passwordHash;
        
        String q = socket.sendMessage(message).trim();
        
        System.out.println("Serialized Object Size: " + q.length());
        System.out.println("Deserialize: " + q);
        
        reply = new String(Base64.decodeBase64(q));
        
        if ( reply.equals(Reply.FAIL.toString()) ) {
            return null;
        } else {
            // Deserializa objeto
            Gson gson = new Gson();
            player = gson.fromJson(reply, Player.class);
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
