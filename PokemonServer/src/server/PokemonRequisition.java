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
import db.SQLQuery;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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
        
        System.out.println("PokemonRequisition");
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

                    // Parâmetros
                    if ( requestPart.length > 3 ) {
                        parameters = requestPart[3].split(" ");
                        command = requestPart[3];
                    }

                    // Verifica qual o comando na requisição
                    switch ( Request.valueOf(command) ) {
                        case LOGIN:
                            output = login(parameters[0], parameters[1]);
                            break;
                        case PRESS:
                            output = press(parameters[0]);
                            break;
                        case QUIT:
                            output = quit();
                            break;
                    }

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
        
        try {
            connection.connect();

            SQLQuery query = connection.newQuery();

            query.select("*").
                    from("users").
                    where().equal("name", username)._and()
                           .equal("password", password);
            
            ResultSet rs = connection.execute(query);
            
            Player player = null;
            
            if ( rs.next() ) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    // Utiliza nome e data corrente para gerar o hash md5 do validationCode
                    String nameAndDate = rs.getString("name") + Calendar.getInstance().getTimeInMillis();

                    md.update(nameAndDate.getBytes());
                    String validationCode = new String(md.digest());

                    player = new Player(rs.getInt("id"), validationCode);
                    // TODO Recuperar todos os dados do usuário (pokemons)
                    
                    // Serializa player
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("player.ser"));
                    out.writeObject(player);
                    out.close();
                    
                    message = new String(Files.readAllBytes(Paths.get("player.ser")));
                    
                    System.out.println("PlayerString Size: " + message.length() + " bytes!");
                } catch (NoSuchAlgorithmException e) {
                    message = Reply.FAIL.toString();
                    System.err.println(this.getClass().getName() + ": " + e.getMessage());
                } catch (IOException e) {
                    message = Reply.FAIL.toString();
                    System.err.println(this.getClass().getName() + ": " + e.getMessage());
                }
            }
            
            rs.close();
            
            connection.disconnect();
        } catch (SQLException e ) {
            
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