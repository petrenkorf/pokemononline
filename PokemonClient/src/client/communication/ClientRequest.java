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
        LOGOUT("logout");
        
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
    String request = "";
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
        
        request = makeRequest(Request.LOGIN, username + " " + passwordHash);
        
//        System.out.println("Serialized Object Size: " + q.length());
//        System.out.println("Deserialize: " + q);
        
        reply = socket.sendMessage(request);
        
        if ( reply.equals(Reply.FAIL.toString()) ) {
            return null;
        } else {
            // Deserializa objeto
            Gson gson = new Gson();
            player = gson.fromJson(reply, Player.class);
        }
        
        socket = null;
        
        return player;
    }
    
    /**
     * Trata as entradas do usuário
     * 
     * @param command
     * @return 
     */
    public String press(Command command) {
        socket = new SocketClient();
        
        String direction  = "";
        
        switch ( command ) {
            case UP:
                direction = "up";
                break;
            case RIGHT:
                direction = "right";
                break;
            case DOWN:
                direction = "down";
                break;
            case LEFT:
                direction = "left";
                break;
            case ACTION:
                direction = "action";
                break;
        }
        
        request = makeRequest(Request.PRESS, direction);
        
        reply = socket.sendMessage(request);
        
        socket = null;
        
        return reply;
    }
    
    private String makeRequest(Request command) {
        return makeRequest(command, null);
    }
    
    /**
     * Monta uma requisição com id e código de verificação do usuário no formato: 
     * <id>:<verificationCode>:<command>:<parameters*>
     * 
     * @param command
     * @param parameters
     * @return 
     */
    private String makeRequest(Request command, String parameters) {
        String _message = "";
        
        // Requisição deve sempre ter 4 parâmetros (não vazios) separados por :
        if ( parameters == null || parameters.isEmpty() ) {
            parameters = "0";
        }
        
        // Caso usuário esteja logado
        if ( player != null ) {
            _message = player.getId() + ":" + player.getVerificationCode() + ":" + 
                    command.toString() + ":" + parameters;
        } else {
            // Login
            _message = "0:0:" + command.toString() + ":" + parameters;
        }
        
        return _message;
    }
    
    /**
     * Realia logout do jogo
     * 
     * @return 
     */
    public boolean logout() {
        socket = new SocketClient();
        
        reply = socket.sendMessage(makeRequest(Request.LOGOUT));
        
        // Desaloca player caso logout ocorrer com sucesso
        if ( !reply.equals(Reply.OK.toString()) ) {
            System.out.println("Problem to logout!");
        }
        
        player = null;
        socket = null;
        
        return true;
    }
}
