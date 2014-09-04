/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import client.communication.ClientRequest.Request;
import client.communication.ClientRequest.Reply;
import client.game.Player;
import client.map.Map;
import com.google.gson.Gson;
import db.SQLConnection;
import db.SQLQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * Classe responsável por tratar as requisições dos usuários
 * 
 * @author bruno.weig
 */
public class PokemonRequisition implements Runnable {
    BlockingQueue<Socket> requisitioQueue = null;
    
    BufferedReader inputReader = null;
    
    SQLConnection connection = SQLConnection.getInstance();
    
    Socket currentRequisition = null;
    
    ObservableList<PlayerServer> obsPlayerOnline;
    
    QuadtreePlayer quadtree = new QuadtreePlayer();
    
    Map map = new Map();
    
    final long TIME_FILL_QUADTREE = 20;
    long fillQuadtreeTimer;
    long lastTimer;
    
    boolean executing = true;
    
    public PokemonRequisition(BlockingQueue<Socket> requisitions, ObservableList<PlayerServer> obsPlayerOnline) {
        requisitioQueue = requisitions;
        this.obsPlayerOnline = obsPlayerOnline;
        fillQuadtreeTimer = Calendar.getInstance().getTimeInMillis();
        
        // Carrega mapa
        map.loadMap("main_2.tmx", null);
        
        quadtree.createTree(new Rectangle(map.getMapWidth(), map.getMapHeight()), Point2D.ZERO);
        
        map = new Map();
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

                lastTimer = Calendar.getInstance().getTimeInMillis();
                
                // Atualiza a quadtree a cada TIME_FILL_QUADTREE milisegundos
                if ( lastTimer - fillQuadtreeTimer == TIME_FILL_QUADTREE ) {
                    fillQuadtreeTimer = Calendar.getInstance().getTimeInMillis();
                    
                    // Atualiza referência dos jogadores na Quadtree
                    quadtree.update(obsPlayerOnline);
                    
                }
                
                try {
                    currentRequisition = requisitioQueue.take();

                    inputReader = new BufferedReader(new InputStreamReader(currentRequisition.getInputStream()));

                    message = inputReader.readLine();

                    String[] parameters = null;
                    
                    // Divide a mensagem em 4 segmentos
                    String[] requestPart = message.split(":");
                    
                    Integer id = new Integer(requestPart[0]);
                    String verificationCode = requestPart[1];
                    command = requestPart[2];
                    parameters = requestPart[3].split(" ");

                    // Imprime a mensagem com um cabeçalho
                    System.out.println("Received message from user_id=" + id + "(ip=" + 
                            currentRequisition.getInetAddress().toString() + ") with verCode=" + verificationCode);
                    System.out.println("  message=" + message + " (" + message.getBytes().length + " bytes)");
                    
                    // Verifica qual o comando na requisição
                    if ( command.equals(Request.LOGIN.toString()) ) {
                        output = login(parameters[0], parameters[1]);
                    } else if ( command.equals(Request.PRESS.toString()) ) {
                        output = press(parameters[0]);
                    } else {
                        output = logout(id, verificationCode);
                    }
                    
                    outputStream = currentRequisition.getOutputStream();
                    
                    outputStream.write(Base64.encodeBase64(output.getBytes()));
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

            MessageDigest md = null;
            
            SQLQuery query = connection.newQuery();
            
            // Busca um usuário com as devidas credenciais
            query.select("*").
                    from("users").
                    where().equal("username", username)._and()
                           .equal("password", password);
            
            ResultSet rs = connection.execute(query);
            
            Player player = null;
            
            if ( rs.next() ) {
                try {
                    md = MessageDigest.getInstance("MD5");
                    
                    boolean playerOnline = rs.getBoolean("online");
                    
                    // Se player estiver online
                    if ( playerOnline ) {
                        System.out.println("User " + username + " already logged!");
                        message = Reply.FAIL.toString();
                    } else {
                        // Utiliza nome e data corrente para gerar o hash md5 do validationCode
                        String nameAndDate = rs.getString("username") + ":" + 
                                             Calendar.getInstance().getTimeInMillis();

                        md.update(nameAndDate.getBytes());
                        String validationCode = new String(Hex.encodeHex(md.digest()));
                        
//                        System.out.println("Creating Player...");

                        // TODO Recuperar todos os dados do usuário (pokemons)
                        player = new Player(rs.getInt("id"), validationCode);
                        player.setName(rs.getString("username"));

                        PlayerServer p = new PlayerServer(player.getId(), 
                                player.getName(), player.getVerificationCode());
                        
                        // TODO Não utilizar diretamente os valores
                        p.setWidth(16);
                        p.setHeight(24);
                        
                        obsPlayerOnline.add(p);
                        
//                        System.out.println("Player created...");

                        // Serializa player
//                        System.out.println("Player serializing...");

                        Gson gson = new Gson();
                        message = gson.toJson(player);

//                        System.out.println("Player serialized...");
                    }
                    
//                    System.out.println("PlayerString Size: " + message.length() + " bytes!");
//                    System.out.println("Message Encrypted: " + message);
                } catch (NoSuchAlgorithmException e) {
                    message = Reply.FAIL.toString();
                    System.err.println(this.getClass().getName() + ": " + e.getMessage());
                } catch ( IOException e ) {
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
    
    /**
     * Procura jogador pelo id
     * 
     * @param id
     * @return 
     */
    private PlayerServer searchPlayer(long id) {
        Iterator<PlayerServer> it = obsPlayerOnline.iterator();
        PlayerServer player;
        
        while ( it.hasNext() ) {
            player = it.next();
            
            if ( player.getId() == id) {
                return player;
            }
        }
        
        return null;
    }
    
    /**
     * Usuário tenta realizar logout
     * 
     * @param id
     * @param verificationCode
     * @return 
     */
    public String logout(long id, String verificationCode) {
        PlayerServer player = searchPlayer(id);
        
        if ( player != null ) {
            System.out.println(player.getVerificationCode());
            System.out.println(verificationCode);
            
            if ( player.getVerificationCode().equals(verificationCode) ) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String name = player.getUsername();
                        
                        obsPlayerOnline.remove(player);
//                        System.out.println(name + " removed!");
                    }
                });
                
                return Reply.OK.toString();
            }
        }
        
        return Reply.FAIL.toString();
    }
}