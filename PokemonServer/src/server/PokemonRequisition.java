/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client.communication.ClientRequest.Request;
import client.communication.ClientRequest.Reply;
import db.SQLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import client.game.Player;
import com.google.gson.Gson;
import db.SQLQuery;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author bruno.weig
 */
public class PokemonRequisition implements Runnable {
    BlockingQueue<Socket> requisitioQueue = null;
    
    BufferedReader inputReader = null;
    
    SQLConnection connection = SQLConnection.getInstance();
    
    Socket currentRequisition = null;
    
    boolean executing = true;
    
    public PokemonRequisition(BlockingQueue<Socket> requisitions) {
        requisitioQueue = requisitions;
    }
    
    public void stop() {
        executing = false;
    }
    
    @Override
    public void run() {
        while ( executing ) {
            if ( !requisitioQueue.isEmpty() ) {
                String message = "";
                OutputStream outputStream = null;
                String output = "";
                String command = "";

                try {
                    currentRequisition = requisitioQueue.take();

                    inputReader = new BufferedReader(new InputStreamReader(currentRequisition.getInputStream()));

                    message = inputReader.readLine();

                    System.out.println("Message: " + message);
                    
                    String[] requestPart = message.split(":");
                    String[] parameters = null;
                    
                    System.out.println("Segments: " + requestPart.length);
                    
                    command = requestPart[2];
                    
                    // Parâmetros
                    parameters = requestPart[3].split(" ");

                    // Verifica qual o comando na requisição
                    if ( command.equals(Request.LOGIN.toString()) ) {
                        output = login(parameters[0], parameters[1]);
                    } else if ( command.equals(Request.PRESS.toString()) ) {
                        output = press(parameters[0]);
                    } else {
                        output = quit();
                    }
                    
                    System.out.println("SIZE: " + output.getBytes().length);
                    
                    outputStream = currentRequisition.getOutputStream();
                    outputStream.write(output.getBytes());

                    System.out.println("Message from " + currentRequisition.getInetAddress().toString() + " = " +
                                        message);
                } catch (IOException e) {
                    System.err.println("Socket Error: " + e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println("Interrupted: " + e.getMessage());
                } finally {
                    // Fecha socket
                    try {
                        if ( inputReader != null )
                            inputReader.close();

                        if ( currentRequisition != null )
                            currentRequisition.close();
                    } catch (IOException e) {
                        System.err.println("Problem closing socket server: " + e.getMessage());
                    }
                }
            }
        }
    }
    
    public String login(String username, String password) {
        String message = "";
        
        System.out.println("Login player");
        
        try {
            connection.connect();

            MessageDigest md = null;
            
            SQLQuery query = connection.newQuery();
            
            query.select("*").
                    from("users").
                    where().equal("username", username)._and()
                           .equal("password", password);
            
            ResultSet rs = connection.execute(query);
            
            Player player = null;
            
            if ( rs.next() ) {
                try {
                    md = MessageDigest.getInstance("MD5");
                    
                    // Utiliza nome e data corrente para gerar o hash md5 do validationCode
                    String nameAndDate = rs.getString("username") + ":" + 
                                         Calendar.getInstance().getTimeInMillis();

                    md.update(nameAndDate.getBytes());
                    String validationCode = new String(md.digest());

                    System.out.println("Creating Player...");
                    
                    // TODO Recuperar todos os dados do usuário (pokemons)
                    player = new Player(rs.getInt("id"), validationCode);
                    
                    System.out.println("Player created...");
                    
                    // Serializa player
                    System.out.println("Player serializing...");
                    
                    Gson gson = new Gson();
                    message = gson.toJson(player);
                    
                    System.out.println("Player serialized...");
                    
                    message = Base64.encodeBase64String(message.getBytes());
                    
                    System.out.println("PlayerString Size: " + message.length() + " bytes!");
                    
                    System.out.println("Message: " + message);
                } catch (NoSuchAlgorithmException e) {
                    message = Reply.FAIL.toString();
                    System.err.println(this.getClass().getName() + ": " + e.getMessage());
                }
            } else {
                System.out.println("ResultSet is empty!");
            }
            
            rs.close();
            
            connection.disconnect();
        } catch (SQLException e ) {
            message = Reply.FAIL.toString();
        }
        
        return message;
    }
    
    public String press(String command) {
        return Reply.OK.toString();
    }
    
    public String quit() {
        return Reply.OK.toString();
    }
}